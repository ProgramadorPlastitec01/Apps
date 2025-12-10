package Tags;

import Controladores.AreaJpaController;
import Controladores.PendienteJpaController;
import Controladores.ReunionJpaController;
import Controladores.UsuarioJpaController;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_reunion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            String[] cod_area = pageContext.getSession().getAttribute("Id/Area").toString().split("/");
            int cod = Integer.parseInt(cod_area[0].toString());
            int id_usuario = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
            String area = cod_area[1];
            //FIN PERMISOS
            AreaJpaController jpacara = new AreaJpaController();
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            ReunionJpaController jpacrun = new ReunionJpaController();
            PendienteJpaController jpacpde = new PendienteJpaController();
            //VARIABLE GLOBALES
            int id_reunion = 0;
            int id_pendiente = 0;
            int visor = 0;
            List lst_usuario = null;
            List lst_areas = null;
            List lst_usuarios = null;
            List lst_reuniones = null;
            List lst_pendientes = null;
            List lst_pendientesol = null;
            List lst_pendientessnsol = null;
            List lst_pendiente = null;
            List lst_reunion = null;
            String filtro = "";
            Date fecha_jv = new Date();
            String fecha_inicio = (fecha_jv.getYear() + 1900) + "" + (fecha_jv.getMonth() < 9 ? "-0" : "-") + "" + (fecha_jv.getMonth() + 1) + "" + (fecha_jv.getDate() <= 9 ? "-0" : "-") + "01";
            String fecha_fin = (fecha_jv.getYear() + 1900) + "" + (fecha_jv.getMonth() < 9 ? "-0" : "-") + "" + (fecha_jv.getMonth() + 1) + "" + (fecha_jv.getDate() <= 9 ? "-0" : "-");
            List contador_pen = jpacpde.Consulta_pendiente_usuario(id_usuario);
            List contador_sol = jpacpde.Pendientes_solucionados(id_usuario);
            int cont_pen = 0;
            int cont_sol = 0;
            if (contador_pen != null) {
                cont_pen = contador_pen.size();
            }
            if (contador_sol != null) {
                cont_sol = contador_sol.size();
            }
            if (pageContext.getRequest().getAttribute("Reunion") != null) {
                //<editor-fold defaultstate="collapsed" desc="MODULO REUNIÓN">
                if (pageContext.getRequest().getAttribute("Reunion").toString().equals("Modulo_reunion")) {
                    id_reunion = Integer.parseInt(pageContext.getRequest().getAttribute("Id_reunion").toString());
                    id_pendiente = Integer.parseInt(pageContext.getRequest().getAttribute("Id_pendiente").toString());
                    visor = Integer.parseInt(pageContext.getRequest().getAttribute("Tipo_visor").toString());
                    fecha_inicio = pageContext.getRequest().getAttribute("Fecha_inicio").toString();
                    fecha_fin = pageContext.getRequest().getAttribute("Fecha_fin").toString();
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    out.print("<div id='content_sin'>");
                    if (!rol.equals("Consulta")) {
                        out.print("<h3><a href='Reunion?opc=1&iru=-1&fin=&ffn=&fto='><img src='Interfaz/Contenido/Iconos/Plus.png' width='22px' height='22px' alt='edit' title='Desplegar Menu' /></a>"
                                + "Reuniones "
                                + "<div style='float:right;width:50%;'>"
                                + "<div style='width:300px;float:left'><i><a style='text-decoration: none;color:#CC0000;' href='Sesion?opc=2'>Pendientes (" + cont_pen + ")</a> | <a style='text-decoration: none;color:green;' href='Pendiente?opc=4'>Solucionados (" + cont_sol + ")</a></i></div>"
                                + "<div style='float:right'><img id='Filtro_avanzado' src='Interfaz/Contenido/Iconos/Search.png' width='22px' height='22px' />"
                                + "  <input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>"
                                + "</div></h3>");
                    } else {
                        out.print("<h3>Reuniones<div style='float:right'>"
                                + "<img id='Filtro_avanzado' src='Interfaz/Contenido/Iconos/Search.png' width='22px' height='22px' />"
                                + "  <input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' />"
                                + "</div></h3>");
                    }
                    //<editor-fold defaultstate="collapsed" desc="FILTRO AVANZADO">
                    out.print("<script>");
                    out.print("$(Filtro_avanzado).click(function() {");
                    out.print("$(\"#toggleF\").toggle(\"slide\");");
                    out.print("});");
                    out.print("</script>");
                    out.print("<div style='width:400px;padding-right:20px;padding-right:20px;margin-left:50%;margin-top:0%;border-radius:11px;display:none;border: 1px solid #C2185B;background-color:#fff;position:absolute;z-index:200;' id=\"toggleF\">");
                    out.print("<dir /><h3>Filtro Avanzado</h3>");
                    out.print("<form action='Reunion?opc=1&iru=0' method='post' id='Form_filtro_avanzado'>");
//                    out.print("<a style='float:right' href='#' onclick=\"javascript:document.getElementById('Form_filtro_avanzado').reset();\">Limpiar</a>");
                    out.print("<table class='table'>");
                    out.print("<tr>");
                    out.print("<th>Tipo</th>");
                    out.print("<th>Valor</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'>Fecha inicio</td>");
                    out.print("<td><input type='text' name='fin' id='start' autocomplete='off' required value='" + fecha_inicio + "' placeholder='Fecha inicio'/></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'>Fecha fin</td>");
                    out.print("<td><input type='text' name='ffn' id='end' autocomplete='off' required value='" + fecha_fin + "' placeholder='Fecha fin'/></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'>Buscar</td>");
                    out.print("<td><input type='text' name='Txt_filtro_avanzado' id='Txt_filtro_avanzado' autocomplete='off' onkeypress='FiltroAvanzado(event);' placeholder='Buscar' />"
                            + "<br /><b>Valores a filtrar</b><div id='Buscar_valores'></div>"
                            + "<input type='hidden' name='fto' id='Txt_valores_filtro' oninput=\"javascript:this.value+=document.getElementById('Buscar_valores').innerHTML\"/></td>");
                    if (filtro.length() > 0) {
                        out.print("<br /><b>Anteriores Filtrado</b><br />" + filtro.toUpperCase().replace("+", "<br />") + "");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2' align='center'><input type='submit' value='Filtrar' /></td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</form>");
                    out.print("</div>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR INFORME">
                    if (id_reunion == -1) {
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:85%;position: absolute;top: 20px;left:2%;height:600px;overflow:scroll;'>");
                        out.print("<div style='float:right;'><a href='Reunion?opc=1&iru=0&fin=&ffn=&fto='><img src='Interfaz/Contenido/Iconos/Delete.png' width='22px' height='22px' title='Cancelar'></a></div>");
                        out.print("<h3>Nueva Reunión</h3>");
                        out.print("<div style='width:50%;float:left'>");
                        out.print("<form action='Reunion?opc=2&iru=0' onsubmit='Informe();' method='post' id='Form_informe'>");
                        out.print("<table align='left'><tr>");
                        out.print("<td><b>Fecha:</b></td>");
                        out.print("<td><input type='text' style='width:80px' name='Txt_fecha' id=\"datepicker\"  placeholder='Fecha'>");
                        out.print("<script type='text/javascript'>var validation = new LiveValidation('datepicker');validation.add( Validate.Presence );</script></td>");
                        out.print("<td><b>Hora inicio:</b></td>");
                        out.print("<td><input type='time' style='width:120px' name='Txt_hora_inicio' id='Txt_hora_inicio' placeholder='Fecha'>");
                        out.print("<script type='text/javascript'>var validation = new LiveValidation('Txt_hora_inicio');validation.add( Validate.Presence );</script></td>");
                        out.print("<td><b>Hora fin:</b></td>");
                        out.print("<td><input type='time' style='width:120px' name='Txt_hora_fin' id='Txt_hora_fin' placeholder='Fecha'>");
                        out.print("<script type='text/javascript'>var validation = new LiveValidation('Txt_hora_fin');validation.add( Validate.Presence );</script></td></tr>");
                        out.print("<tr><td colspan='6'><b>Asunto:</b></td></tr>");
                        out.print("<tr><td colspan='6'><textarea name='Txt_asunto' id='Txt_asunto' style='width: 100%;' placeholder='Asunto'></textarea>");
                        out.print("<script type='text/javascript'>var validation = new LiveValidation('Txt_asunto');validation.add( Validate.Presence );</script></td></tr>");
                        out.print("</table>");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 550px; height: 400px' placeholder='descripcion'>");
                        out.print("<b>Contenido : </b><br/>");
                        out.print("<div contenteditable='true'><br /><br /><br /><br /></div>");
                        out.print("</textarea>");
                        out.print("<input type='submit' id='Btn_guardar_informe'  value='Guardar' />");
                        out.print("<input type='hidden' name='Txt_seleccion_participes' id='Txt_seleccion_participes' value='' />");
                        out.print("<input type='hidden' name='Txt_seleccion_areas' id='Txt_seleccion_areas' value='' />");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("<div style='width:50%;float:left'>");
                        lst_usuario = jpacusa.Usuarios();
                        out.print("<table style='text-align:left;width:100%' class='table'>");
                        out.print("<tr>");
                        out.print("<th colspan='2'>Asistentes</th>");
                        out.print("</tr>");
                        out.print("<tr><td style='width:50%' valing='top'>");
                        for (int i = 0; i < lst_usuario.size(); i += 2) {
                            Object[] obj_usuario = (Object[]) lst_usuario.get(i);
                            out.print("<input type='checkbox' id='Cbx_participe' name='Cbx_participe' value='[" + obj_usuario[0] + "/" + obj_usuario[10].toString().split("/")[0] + "]' onclick=\"SeleccionParticipes(this);\" /> " + obj_usuario[1] + "<br />");
                        }
                        out.print("</td>");
                        out.print("<td style='width:50%' valing='top'>");
                        for (int i = 1; i < lst_usuario.size(); i += 2) {
                            Object[] obj_usuario = (Object[]) lst_usuario.get(i);
                            out.print("<input type='checkbox' id='Cbx_participe' name='Cbx_participe' value='[" + obj_usuario[0] + "/" + obj_usuario[10].toString().split("/")[0] + "]' onclick=\"SeleccionParticipes(this);\" /> " + obj_usuario[1] + "<br />");
                        }
                        out.print("</td></tr>");
                        out.print("</table>");
                        lst_areas = jpacara.Areas();
                        out.print("<table style='text-align:left;width:100%' class='table'>");
                        out.print("<tr>");
                        out.print("<th colspan='2'>Consulta</th>");
                        out.print("</tr>");
                        out.print("<tr><td style='width:50%' valing='top'>");
                        for (int i = 0; i < lst_areas.size(); i += 2) {
                            Object[] obj_area = (Object[]) lst_areas.get(i);
                            out.print("<input type='checkbox' id='Cbx_area_consulta' name='Cbx_area_consulta' value='[" + obj_area[0] + "]' onclick=\"SeleccionAreas(this);\"/> " + obj_area[1] + "<br />");
                        }
                        out.print("</td>");
                        out.print("<td style='width:50%' valing='top'>");
                        for (int i = 1; i < lst_areas.size(); i += 2) {
                            Object[] obj_area = (Object[]) lst_areas.get(i);
                            out.print("<input type='checkbox' id='Cbx_area_consulta' name='Cbx_area_consulta' value='[" + obj_area[0] + "]' onclick=\"SeleccionAreas(this);\"/> " + obj_area[1] + "<br />");
                        }
                        out.print("</td></tr>");
                        out.print("</table>");
                        out.print("</div>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    } //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="VISOR Y MODIFICAR INFORME">
                    else if (id_reunion > 0) {
                        lst_reunion = jpacrun.Reunion_id(id_reunion);
                        Object[] obj_reunion = (Object[]) lst_reunion.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:85%;position: absolute;top: 20px;left:2%;height:600px;overflow:scroll;'>");
                        out.print("<div style='float:right;'><a href='Reunion?opc=1&iru=0&fin=&ffn=&fto='><img src='Interfaz/Contenido/Iconos/Delete.png' width='22px' height='22px' title='Cancelar'></a></div>");
                        if (visor > 0) {
                            //<editor-fold defaultstate="collapsed" desc="VISOR REUNIÓN">
                            out.print("<div style='float:left;'><a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" alt=\"\" width='22px' height='22px' title='Imprimir' /></a> Imprimir o PDF </div>");
                            out.print("<div id='Imprimir'>");
                            out.print("<table class='table2' style='width:100%' >");
                            out.print("<tr>");
                            out.print("<td colspan='10' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center' colspan='3' rowspan='2'>"
                                    + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                    + "</td>");
                            out.print("<td colspan='5' align='center'><b class='negro'>REGISTRO</b></td>");
                            out.print("<td colspan='2' rowspan='2' align='center'><b class='negro'>NO CODIFICADO</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='5' align='center'><b class='negro'>ACTA DE REUNIÓN</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='10' align='left' ><b>ASUNTO</b><br />" + obj_reunion[4] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='2' align='left'><b>AREA : </b>" + obj_reunion[8] + "</td>");
                            out.print("<td colspan='2' align='left'><b>RESPONSABLE : </b>" + obj_reunion[10] + "</td>");
                            out.print("<td colspan='2' align='left'><b>FECHA : </b>" + obj_reunion[1] + "</td>");
                            out.print("<td colspan='2' align='left'><b>HORA INICIO : </b>" + obj_reunion[2] + "</td>");
                            out.print("<td colspan='2' align='left'><b>HORA FIN : </b>" + obj_reunion[3] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th colspan='10'>ASISTENTES</th>");
                            out.print("</tr>");
                            out.print("<td colspan='10' align='left'>");
                            lst_usuarios = jpacusa.Usuarios();
                            String participes = "";
                            for (int j = 0; j < lst_usuarios.size(); j++) {
                                Object[] obj_usuario = (Object[]) lst_usuarios.get(j);
                                if (obj_reunion[6].toString().contains("[" + obj_usuario[0] + "/" + obj_usuario[10].toString().split("/")[0] + "]")) {
                                    participes = participes + "-" + obj_usuario[1];
                                }
                            }
                            out.print("" + participes.replace("-", "<br />* ") + "<br /><br />");
                            out.print("</td>");
                            out.print("<tr>");
                            out.print("<th colspan='10'>REUNIÓN</th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='10' align='left' >");
                            out.print("" + obj_reunion[5]);
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th colspan='10'>PENDIENTES</th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='10' align='left' >");
                            lst_pendientes = jpacpde.Pendientes_id_reunion((Integer) obj_reunion[0]);
                            if (lst_pendientes == null) {
                            } else {
                                out.print("<table style='text-align:left;width:100%' class='table2'>");
                                out.print("<tr>");
                                out.print("<td><b>Pendiente</b></td>");
                                out.print("<td><b>Responsables</b></td>");
                                out.print("</tr>");
                                for (int j = 0; j < lst_pendientes.size(); j++) {
                                    Object[] obj_pendientes = (Object[]) lst_pendientes.get(j);
                                    out.print("<tr>");
                                    out.print("<td>" + obj_pendientes[2] + "</td>");
                                    out.print("<td>");
                                    lst_usuarios = jpacusa.Usuarios();
                                    String responsables = "";
                                    for (int k = 0; k < lst_usuarios.size(); k++) {
                                        Object[] obj_responsables = (Object[]) lst_usuarios.get(k);
                                        if (obj_pendientes[3].toString().contains("[" + obj_responsables[0] + "]")) {
                                            responsables = responsables + "-" + obj_responsables[1];
                                        }
                                    }
                                    out.print("" + responsables + "");
                                    out.print("</td>");
                                    out.print("</tr>");
                                }
                                out.print("</table>");
                            }
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("</div>");
//</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                            out.print("<h3>Modificar Reunión</h3>");
                            //<editor-fold defaultstate="collapsed" desc="BLOQUE 1">
                            out.print("<div style='width:50%;float:left'>");
                            out.print("<form action='Reunion?opc=2&iru=" + obj_reunion[0] + "' onsubmit='Informe();' method='post' id='Form_informe'>");
                            out.print("<table align='left'><tr>");
                            out.print("<td><b>Fecha:</b></td>");
                            out.print("<td><input type='text' style='width:80px' name='Txt_fecha' id=\"datepicker\" value='" + obj_reunion[1] + "' placeholder='Fecha'>");
                            out.print("<script type='text/javascript'>var validation = new LiveValidation('datepicker');validation.add( Validate.Presence );</script></td>");
                            out.print("<td><b>Hora inicio:</b></td>");
                            out.print("<td><input type='time' style='width:120px' name='Txt_hora_inicio' id='Txt_hora_inicio' value='" + obj_reunion[2] + "' placeholder='Fecha'>");
                            out.print("<script type='text/javascript'>var validation = new LiveValidation('Txt_hora_inicio');validation.add( Validate.Presence );</script></td>");
                            out.print("<td><b>Hora fin:</b></td>");
                            out.print("<td><input type='time' style='width:120px' name='Txt_hora_fin' id='Txt_hora_fin' value='" + obj_reunion[3] + "' placeholder='Fecha'>");
                            out.print("<script type='text/javascript'>var validation = new LiveValidation('Txt_hora_fin');validation.add( Validate.Presence );</script></td></tr>");
                            out.print("<tr><td colspan='6'><b>Asunto:</b></td></tr>");
                            out.print("<tr><td colspan='6'><textarea name='Txt_asunto' id='Txt_asunto' style='width: 100%;' placeholder='Asunto'>" + obj_reunion[4] + "</textarea>");
                            out.print("<script type='text/javascript'>var validation = new LiveValidation('Txt_asunto');validation.add( Validate.Presence );</script></td></tr>");
                            out.print("</table>");
                            out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 550px; height: 400px' placeholder='descripcion'>");
                            out.print("" + obj_reunion[5].toString().replace("<div>", "<div contenteditable='true'>"));
                            out.print("</textarea>");
                            out.print("<input type='submit' id='Btn_guardar_informe'  value='Guardar' />");
                            out.print("<input type='hidden' name='Txt_seleccion_participes' id='Txt_seleccion_participes' value='" + obj_reunion[6] + "' />");
                            out.print("<input type='hidden' name='Txt_seleccion_areas' id='Txt_seleccion_areas' value='" + obj_reunion[11] + "' />");
                            out.print("</form>");
                            out.print("</div>");
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="BLOQUE 2">
                            out.print("<div style='width:50%;float:left'>");
                            out.print("<button class='accordion'>Asistentes</button>");
                            out.print("<div class='panel'>");
                            lst_usuario = jpacusa.Usuarios();
                            out.print("<table style='text-align:left;width:100%' class='table'>");
                            out.print("<tr>");
                            out.print("<td colspan='2'><b>Asistentes</b></td>");
                            out.print("</tr>");
                            out.print("<tr><td style='width:50%' valing='top'>");
                            for (int i = 0; i < lst_usuario.size(); i += 2) {
                                Object[] obj_usuario = (Object[]) lst_usuario.get(i);
                                if (obj_reunion[6].toString().contains("[" + obj_usuario[0] + "/" + obj_usuario[10].toString().split("/")[0] + "]")) {
                                    out.print("<input type='checkbox' id='Cbx_participe' checked name='Cbx_participe' value='[" + obj_usuario[0] + "/" + obj_usuario[10].toString().split("/")[0] + "]' onclick=\"SeleccionParticipes(this);\" /> " + obj_usuario[1] + "<br />");
                                } else {
                                    out.print("<input type='checkbox' id='Cbx_participe' name='Cbx_participe' value='[" + obj_usuario[0] + "/" + obj_usuario[10].toString().split("/")[0] + "]' onclick=\"SeleccionParticipes(this);\" /> " + obj_usuario[1] + "<br />");
                                }
                            }
                            out.print("</td>");
                            out.print("<td style='width:50%' valing='top'>");
                            for (int i = 1; i < lst_usuario.size(); i += 2) {
                                Object[] obj_usuario = (Object[]) lst_usuario.get(i);
                                if (obj_reunion[6].toString().contains("[" + obj_usuario[0] + "/" + obj_usuario[10].toString().split("/")[0] + "]")) {
                                    out.print("<input type='checkbox' id='Cbx_participe' checked name='Cbx_participe' value='[" + obj_usuario[0] + "/" + obj_usuario[10].toString().split("/")[0] + "]' onclick=\"SeleccionParticipes(this);\" /> " + obj_usuario[1] + "<br />");
                                } else {
                                    out.print("<input type='checkbox' id='Cbx_participe' name='Cbx_participe' value='[" + obj_usuario[0] + "/" + obj_usuario[10].toString().split("/")[0] + "]' onclick=\"SeleccionParticipes(this);\" /> " + obj_usuario[1] + "<br />");
                                }
                            }
                            out.print("</td></tr>");
                            out.print("</table>");
                            out.print("</div>");
                            out.print("<button class='accordion'>Opciones de compartir</button>");
                            out.print("<div class='panel'>");
                            lst_areas = jpacara.Areas();
                            out.print("<table style='text-align:left;width:100%' class='table'>");
                            out.print("<tr>");
                            out.print("<td colspan='2'><b>Consulta</b></td>");
                            out.print("</tr>");
                            out.print("<tr><td style='width:50%' valing='top'>");
                            for (int i = 0; i < lst_areas.size(); i += 2) {
                                Object[] obj_area = (Object[]) lst_areas.get(i);
                                if (obj_reunion[11].toString().contains("[" + obj_area[0] + "]")) {
                                    out.print("<input type='checkbox' id='Cbx_area_consulta' checked name='Cbx_area_consulta' value='[" + obj_area[0] + "]' onclick=\"SeleccionAreas(this);\"/> " + obj_area[1] + "<br />");
                                } else {
                                    out.print("<input type='checkbox' id='Cbx_area_consulta' name='Cbx_area_consulta' value='[" + obj_area[0] + "]' onclick=\"SeleccionAreas(this);\"/> " + obj_area[1] + "<br />");
                                }
                            }
                            out.print("</td>");
                            out.print("<td style='width:50%' valing='top'>");
                            for (int i = 1; i < lst_areas.size(); i += 2) {
                                Object[] obj_area = (Object[]) lst_areas.get(i);
                                if (obj_reunion[11].toString().contains("[" + obj_area[0] + "]")) {
                                    out.print("<input type='checkbox' checked id='Cbx_area_consulta' checked name='Cbx_area_consulta' value='[" + obj_area[0] + "]' onclick=\"SeleccionAreas(this);\"/> " + obj_area[1] + "<br />");
                                } else {
                                    out.print("<input type='checkbox' id='Cbx_area_consulta' name='Cbx_area_consulta' value='[" + obj_area[0] + "]' onclick=\"SeleccionAreas(this);\"/> " + obj_area[1] + "<br />");
                                }
                            }
                            out.print("</td></tr>");
                            out.print("</table>");
                            out.print("</div>");
                            if (id_pendiente == 0) {
                                //<editor-fold defaultstate="collapsed" desc="NUEVO PENDIENTE">
                                out.print("<button class='accordion'>Nuevo pendiente</button>");
                                out.print("<div class='panel'>");
                                out.print("<form action='Reunion?opc=3&iru=" + id_reunion + "' method='post' id='Form_pendiente'>");
                                lst_usuario = jpacusa.Usuarios();
                                out.print("<table style='text-align:left;width:100%' class='table'>");
                                out.print("<tr>");
                                out.print("<td colspan='2'><b>Responsables</b></td>");
                                out.print("</tr>");
                                out.print("<tr><td style='width:50%' valing='top'>");
                                for (int i = 0; i < lst_usuario.size(); i += 2) {
                                    Object[] obj_usuario = (Object[]) lst_usuario.get(i);
                                    out.print("<input type='checkbox' id='Cbx_responsable' name='Cbx_responsable' value='[" + obj_usuario[0] + "]' onclick=\"SeleccionResponsables(this);\" /> " + obj_usuario[1] + "<br />");
                                }
                                out.print("</td>");
                                out.print("<td style='width:50%' valing='top'>");
                                for (int i = 1; i < lst_usuario.size(); i += 2) {
                                    Object[] obj_usuario = (Object[]) lst_usuario.get(i);
                                    out.print("<input type='checkbox' id='Cbx_responsable' name='Cbx_responsable' value='[" + obj_usuario[0] + "]' onclick=\"SeleccionResponsables(this);\" /> " + obj_usuario[1] + "<br />");
                                }
                                out.print("</td></tr>");
                                out.print("</table>");
                                out.print("<b>PENDIENTE</b><br /><textarea style='width:100%' name='Txt_pendiente' id='Txt_pendiente' placeholder='Descripcion de pendiente'></textarea>");
                                out.print("<script type='text/javascript'>var validation = new LiveValidation('Txt_pendiente');validation.add( Validate.Presence );</script>");
                                out.print("<input type='hidden' name='Txt_seleccion_responsables' id='Txt_seleccion_responsables' value='' />");
                                out.print("<input type='submit' id='Btn_guardar_pendiente' style='display:none' value='Registrar pendiente' />");
                                out.print("</form>");
                                out.print("</div>");
                                out.print("</br></br>");
                                lst_pendientes = jpacpde.Pendientes_id_reunion(id_reunion);
                                if (lst_pendientes == null) {
                                } else {
                                    out.print("<div align='left' id='NavPosicion1'></div>");
                                    out.print("<table style='text-align:left;width:100%' id='resultados_1' class='table'>");
                                    out.print("<tr>");
                                    out.print("<th colspan='3'>Pendiente</th>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>#</b></td>");
                                    out.print("<td align='center' style='width:70%'><b>Pendiente</b></td>");
                                    out.print("<td align='center'><b>Responsables</b></td>");
                                    out.print("</tr>");
                                    for (int i = 0; i < lst_pendientes.size(); i++) {
                                        Object[] obj_pendientes = (Object[]) lst_pendientes.get(i);
                                        out.print("<tr>");
                                        out.print("<td align='center'><b><a href='Reunion?opc=1&iru=" + id_reunion + "&vsr=0&ipd=" + obj_pendientes[0] + "&fin=&ffn=&fto='><b>" + (i + 1) + "</b></a></b></td>");
                                        out.print("<td valign='top' style='width:80px'>" + obj_pendientes[2] + "</td>");
                                        out.print("<td valign='top'>");
                                        lst_usuarios = jpacusa.Usuarios();
                                        String responsables = "";
                                        for (int j = 0; j < lst_usuarios.size(); j++) {
                                            Object[] obj_responsables = (Object[]) lst_usuarios.get(j);
                                            if (obj_pendientes[3].toString().contains("[" + obj_responsables[0] + "]")) {
                                                responsables = responsables + "-" + obj_responsables[1];
                                            }
                                        }
                                        out.print("" + responsables.replace("-", "<br/>") + "");
                                        out.print("</td>");
                                        out.print("</tr>");
                                    }
                                    out.print("</table>");
                                    out.print("<script type='text/javascript'>");
                                    out.print("var pager1 = new Pager1('resultados_1', 5);");
                                    out.print("pager1.init_1();");
                                    out.print("pager1.showPageNav_1('pager1','NavPosicion1');");
                                    out.print("pager1.showPage_1(1);");
                                    out.print("</script>");
                                }
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="MODIFICAR PENDIENTE">
                                lst_pendiente = jpacpde.Pendientes_id(id_pendiente);
                                Object[] obj_pendiente = (Object[]) lst_pendiente.get(0);
                                out.print("<h3>Modificar pendiente</h3>");
                                out.print("<form action='Reunion?opc=3&iru=" + id_reunion + "&ipd=" + id_pendiente + "' method='post' id='Form_pendiente'>");
                                lst_usuario = jpacusa.Usuarios();
                                out.print("<table style='text-align:left;width:100%' class='table'>");
                                out.print("<tr>");
                                out.print("<td colspan='2'><b>Responsables</b></td>");
                                out.print("</tr>");
                                out.print("<tr><td style='width:50%' valing='top'>");
                                for (int i = 0; i < lst_usuario.size(); i += 2) {
                                    Object[] obj_usuario = (Object[]) lst_usuario.get(i);
                                    if (obj_pendiente[3].toString().contains("[" + obj_usuario[0] + "]")) {
                                        out.print("<input type='checkbox' id='Cbx_responsable' checked name='Cbx_responsable' value='[" + obj_usuario[0] + "]' onclick=\"SeleccionResponsables(this);\" /> " + obj_usuario[1] + "<br />");
                                    } else {
                                        out.print("<input type='checkbox' id='Cbx_responsable' name='Cbx_responsable' value='[" + obj_usuario[0] + "]' onclick=\"SeleccionResponsables(this);\" /> " + obj_usuario[1] + "<br />");
                                    }
                                }
                                out.print("</td>");
                                out.print("<td style='width:50%' valing='top'>");
                                for (int i = 1; i < lst_usuario.size(); i += 2) {
                                    Object[] obj_usuario = (Object[]) lst_usuario.get(i);
                                    if (obj_pendiente[3].toString().contains("[" + obj_usuario[0] + "]")) {
                                        out.print("<input type='checkbox' id='Cbx_responsable' checked name='Cbx_responsable' value='[" + obj_usuario[0] + "]' onclick=\"SeleccionResponsables(this);\" /> " + obj_usuario[1] + "<br />");
                                    } else {
                                        out.print("<input type='checkbox' id='Cbx_responsable' name='Cbx_responsable' value='[" + obj_usuario[0] + "]' onclick=\"SeleccionResponsables(this);\" /> " + obj_usuario[1] + "<br />");
                                    }
                                }
                                out.print("</td></tr>");
                                out.print("</table>");
                                out.print("<b>PENDIENTE</b><br /><textarea style='width:100%' name='Txt_pendiente' id='Txt_pendiente' placeholder='Descripcion de pendiente'>" + obj_pendiente[2] + "</textarea>");
                                out.print("<script type='text/javascript'>var validation = new LiveValidation('Txt_pendiente');validation.add( Validate.Presence );</script>");
                                out.print("<input type='hidden' name='Txt_seleccion_responsables' id='Txt_seleccion_responsables' value='" + obj_pendiente[3] + "' />");
                                out.print("<input type='submit' id='Btn_guardar_pendiente' style='display:" + (("".equals(obj_pendiente[3].toString())) ? "none" : "block") + "' value='Modificar pendiente' />");
                                out.print("</form>");
//</editor-fold>
                            }
                            out.print("</div>");
                            //</editor-fold>
                            //</editor-fold>
                        }
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    if (id_reunion == 0 && visor == 0) {
                        if (filtro.length() > 0) {
                            lst_reuniones = jpacrun.Reuniones_area_filtro(cod, fecha_inicio, fecha_fin, filtro);
                        } else if (fecha_inicio.length() > 0) {
                            lst_reuniones = jpacrun.Reuniones_area_filtro_fecha(cod, fecha_inicio, fecha_fin);
                        } else {
                            lst_reuniones = jpacrun.Reuniones_area_id(cod);
                        }
                        if (lst_reuniones == null) {
                            out.print("<center>");
                            out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                            out.print("<b>No hay datos de reuniones registrados</b>");
                            out.print("</center>");
                        } else {
                            out.print("<div id='NavPosicion'></div>");
                            out.print("<table class='table3' id='resultados' style='width:100%'>");
                            out.print("<tr>");
                            //out.print("<th colspan='6'>Informes</th>");
                            out.print("<td colspan='6'></td>");
                            out.print("</tr>");
                            String[] arg_filtro = filtro.toLowerCase().replace("+", "---").split("---");
                            for (int i = 0; i < lst_reuniones.size(); i++) {
                                Object[] obj_reuniones = (Object[]) lst_reuniones.get(i);
                                out.print("<tr>");
//                                out.print("<th style='width:10%'>" + obj_reuniones[1] + "</th>");
                                out.print("<td valign='top' style='width:15%'>"
                                        + "<div class='negro' style='font-size:14px;font-weight:bold;background-color:#AD1457;color:#fff;border-radius:11px;text-align:center'>" + obj_reuniones[1] + "</div>"
                                        + "<b>Hora Inicio : </b>" + obj_reuniones[2] + "<br />"
                                        + "<b>Hora Final : </b>" + obj_reuniones[3] + "<br />"
                                        + "<b>Area : </b>" + obj_reuniones[8] + "<br />"
                                        + "<b>Autor : </b>" + obj_reuniones[10] + "<br />");
                                if (!rol.equals("Consulta")) {
                                    out.print("<a href='Reunion?opc=1&iru=" + obj_reuniones[0] + "&vsr=0&fin=&ffn=&fto='><img src='Interfaz/Contenido/Iconos/Edit.png' width='20px' height='20px' title='Modificar reunión'></a> ");
                                }
                                out.print("<a href='Reunion?opc=1&iru=" + obj_reuniones[0] + "&vsr=1&fin=&ffn=&fto='><img src='Interfaz/Contenido/Iconos/Ver.png' width='22px' height='22px' title='Ver reunión'></a><br />");
                                if (Integer.parseInt(obj_reuniones[12].toString()) > 0) {
                                    if (!rol.equals("Consulta")) {
                                        out.print("<a href='Reunion?opc=4&iru=" + obj_reuniones[0] + "'><img src='Interfaz/Contenido/Iconos/Mail.png' style='height:15px' title='Ver reunión'>Enviar (" + obj_reuniones[12] + ") Pendiente(s)</a>");
                                    } else {
                                        out.print("<img src='Interfaz/Contenido/Iconos/Mail.png' style='height:15px' title='Ver reunión'>Enviar (" + obj_reuniones[12] + ") Pendiente(s)");
                                    }
                                }
                                lst_pendientesol = jpacpde.Consulta_pendiente_estado(Integer.parseInt(obj_reuniones[0].toString()), 1);
                                out.print("</br><b class='verde'>Pendientes Solucionados: ");
                                if (!lst_pendientesol.isEmpty()) {
                                    out.print("" + lst_pendientesol.size() + "");
                                } else {
                                    out.print("0");
                                }
                                lst_pendientessnsol = jpacpde.Consulta_pendiente_estado(Integer.parseInt(obj_reuniones[0].toString()), 0);
                                out.print("</br><b class='rojo'>Pendientes Sin Solucionar: ");
                                if (!lst_pendientessnsol.isEmpty()) {
                                    out.print("" + lst_pendientessnsol.size() + "");
                                } else {
                                    out.print("0");
                                }
                                out.print("</b>");
                                out.print("</td>");
                                out.print("<td valign='top' style='width:70%'>");
                                out.print("<b>Asunto :</b> " + obj_reuniones[4] + "<br /><br />");
                                out.print("<button class='accordion'>Asistentes</button>");
                                out.print("<div class='panel'>");
                                lst_usuarios = jpacusa.Usuarios();
                                String participes = "";
                                for (int j = 0; j < lst_usuarios.size(); j++) {
                                    Object[] obj_usuario = (Object[]) lst_usuarios.get(j);
                                    if (obj_reuniones[6].toString().contains("[" + obj_usuario[0] + "/" + obj_usuario[10].toString().split("/")[0] + "]")) {
                                        participes = participes + "-" + obj_usuario[1];
                                    }
                                }
                                out.print("" + participes.replace("-", "<br />* ") + "<br /><br />");
                                out.print("</div>");
                                String contenido = obj_reuniones[5].toString();
                                String filtro_encontrado = "";
                                if (filtro.length() > 0) {
                                    for (int j = 0; j < arg_filtro.length; j++) {
                                        if (contenido.contains(arg_filtro[j]) || contenido.contains(arg_filtro[j].toUpperCase()) || contenido.contains(arg_filtro[j].toLowerCase()) || contenido.contains(arg_filtro[j].substring(0, 1).toUpperCase() + arg_filtro[j].substring(1))) {
                                            contenido = contenido.replace(arg_filtro[j], "<i style='background-color:#00FA9A'>" + arg_filtro[j].toUpperCase() + "</i>");
                                            contenido = contenido.replace(arg_filtro[j].toUpperCase(), "<i style='background-color:#00FA9A'>" + arg_filtro[j].toUpperCase() + "</i>");
                                            contenido = contenido.replace(arg_filtro[j].toLowerCase(), "<i style='background-color:#00FA9A'>" + arg_filtro[j].toUpperCase() + "</i>");
                                            contenido = contenido.replace(arg_filtro[j].substring(0, 1).toUpperCase() + arg_filtro[j].substring(1), "<i style='background-color:#00FA9A'>" + arg_filtro[j].toUpperCase() + "</i>");
                                            filtro_encontrado = filtro_encontrado + "(" + arg_filtro[j] + ")";
                                        }
                                    }
                                    //out.print(obj_reuniones[5].toString() + "");
                                }
                                out.print("<button class='accordion'><div>Contenido<marquee style='width:300px;float:right;background-color:#fff;color:#ccc'>" + filtro_encontrado.toUpperCase() + "</marquee></div></button>");
                                out.print("<div class='panel'>");
                                out.print(contenido + "");
                                out.print("</div>");
                                out.print("<button class='accordion'>Pendientes</button>");
                                out.print("<div class='panel'>");
                                lst_pendientes = jpacpde.Pendientes_id_reunion((Integer) obj_reuniones[0]);
                                if (lst_pendientes != null) {
                                    out.print("<table style='text-align:left;width:100%' class='table'>");
                                    for (int j = 0; j < lst_pendientes.size(); j++) {
                                        Object[] obj_pendientes = (Object[]) lst_pendientes.get(j);
                                        out.print("<tr>");
                                        out.print("<td valign='top' style='width:50%;'>");
                                        out.print("<b>Fecha y Hora: </b>" + obj_pendientes[7] + "</br>");
                                        out.print("<b>Responsable: </b>");
                                        lst_usuarios = jpacusa.Usuarios();
                                        String responsables = "";
                                        for (int k = 0; k < lst_usuarios.size(); k++) {
                                            Object[] obj_responsables = (Object[]) lst_usuarios.get(k);
                                            if (obj_pendientes[3].toString().contains("[" + obj_responsables[0] + "]")) {
                                                responsables = responsables + "-" + obj_responsables[1];
                                            }
                                        }
                                        out.print("" + responsables + "</br></br>");
                                        out.print("<b>Pendiente: </b>" + obj_pendientes[2] + "</br>");
                                        out.print("</td>");
                                        out.print("<td valign='top' style='width:50%;'>");
                                        if (obj_pendientes[4].equals(1)) {
                                            String solucion = "<b>Solucion:</b>" + obj_pendientes[8].toString().replace("Contenido :", "");
//                                            solucion = solucion.replace("<br/>", "");
                                            out.print("" + solucion + "");
                                        } else {
                                            out.print("<b class='naranja'>Solucion Pendiente</b>");
                                        }
                                        out.print("</td>");
                                        out.print("</tr>");
                                    }
                                    out.print("</table>");
                                }
                                out.print("</div>");
                                out.print("</td>");
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
                    }
                    //</editor-fold>
                    out.print("<script src='Interfaz/Acordeon/Js_accordeon.js'></script>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }
//</editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_reunion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
