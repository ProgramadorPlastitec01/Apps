package Tags;

import Controladoras.AreaJpaController;
import Controladoras.UbicacionJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_maquinas extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol = sesion.getAttribute("Rol").toString();
        String nombre = sesion.getAttribute("Nombre").toString();
        AreaJpaController jpa_area = new AreaJpaController();
        UbicacionJpaController jpa_ubicacion = new UbicacionJpaController();
        List lst_ubicacion = jpa_ubicacion.ConsultaUbicacion();
        List lst_area = jpa_area.ConsultaAreas();
        try {
            out.print("<div id='sidebar'>");
            if (pageContext.getRequest().getAttribute("consultaMaquinasM") != null) {
                // <editor-fold defaultstate="collapsed"  desc="Modificar Maquinas">
                List conMaquinas = (List) pageContext.getRequest().getAttribute("consultaMaquinasM");
                String filtro = (String) pageContext.getRequest().getAttribute("filtro");
                Object[] obj_MaquinasM = (Object[]) conMaquinas.get(0);
                out.print("<h3>Modificar maquina");
                out.print("<div style='float: right;'>");
                out.print("<a href='Maquinas?op=1&idM=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' title='Volver' /></a>");
                out.print("</div>");
                out.print("</h3>");
                out.print("<form method='post' name='formarea' action='Maquinas?op=3&idM=" + obj_MaquinasM[0] + "&txt_bus=" + filtro + "' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='txt_registroM' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<b>Area:</b>");
                out.print("<select name='idA'>");
                out.print("<option style='display:none;' value='" + obj_MaquinasM[1] + "'>" + obj_MaquinasM[6] + "</option>");
                for (int i = 0; i < lst_area.size(); i++) {
                    Object[] obj_area = (Object[]) lst_area.get(i);
                    if (obj_area[5].equals(1)) {
                        out.print("<option value='" + obj_area[0] + "'>" + obj_area[3] + "</option>");
                    } else {
                    }
                }
                out.print("</select><br /><br />");
                out.print("<b>Ubicación:</b>");
                out.print("<select name='idU'>");
                out.print("<option style='display:none;' value='" + obj_MaquinasM[2] + "'>" + obj_MaquinasM[7] + "</option>");
                for (int i = 0; i < lst_ubicacion.size(); i++) {
                    Object[] obj_ubicacion = (Object[]) lst_ubicacion.get(i);
                    if (obj_ubicacion[3].equals(1)) {
                        out.print("<option value='" + obj_ubicacion[0] + "'>" + obj_ubicacion[2] + "</option>");
                    } else {
                    }
                }
                out.print("</select><br /><br />");
                out.print("<b>Maquina: </b><br />");
                out.print("<input type='text' name='txt_maquinaM' id='maquinaM-id' placeholder='Maquina' value='" + obj_MaquinasM[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('maquinaM-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' value='Modificar'><br />");
                out.print("</form>");
                // </editor-fold>
            } else {
                // <editor-fold defaultstate="collapsed"  desc="Registrar Maquinas">
                out.print("<h3>Nueva Maquina</h3>");
                out.print("<form method='post' name='formarea' action='Maquinas?op=2' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='txt_registro' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<b>Area:</b>");
                out.print("<select name='idA'>");
                out.print("<option style='display:none;'>SELECCIONE EL AREA</option>");
                for (int i = 0; i < lst_area.size(); i++) {
                    Object[] obj_area = (Object[]) lst_area.get(i);
                    if (obj_area[5].equals(1)) {
                        out.print("<option value='" + obj_area[0] + "'>" + obj_area[3] + "</option>");
                    } else {
                    }
                }
                out.print("</select><br /><br />");
                out.print("<b>Ubicación:</b>");
                out.print("<select name='idU'>");
                out.print("<option style='display:none;'>SELECCIONE LA UBICACION</option>");
                for (int i = 0; i < lst_ubicacion.size(); i++) {
                    Object[] obj_ubicacion = (Object[]) lst_ubicacion.get(i);
                    if (obj_ubicacion[3].equals(1)) {
                        out.print("<option value='" + obj_ubicacion[0] + "'>" + obj_ubicacion[2] + "</option>");
                    } else {
                    }
                }
                out.print("</select><br /><br />");
                out.print("<b>Maquina: </b><br />");
                out.print("<input type='text' name='txt_maquina' id='maquina-id' placeholder='Maquina' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('maquina-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' value='Registrar'><br />");
                out.print("</form>");
                // </editor-fold>
            }

            out.print("<div class='cleaner'></div></div>");

            out.print("<div id='content'>");
            if (pageContext.getRequest().getAttribute("consultaMaquinas") != null) {
                // <editor-fold defaultstate="collapsed"  desc="Consulta Maquinas">
                List conMaquinas = (List) pageContext.getRequest().getAttribute("consultaMaquinas");
                String filtro = (String) pageContext.getRequest().getAttribute("filtro");
                out.print("<form action='Maquinas?op=1&idM=" + 0 + "' method='post' >");
                out.print("<div style='float: right;'>");
                out.print("<input type='text' name='txt_bus' aling='right' placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("</div>");
                out.print("</form>");
                out.print("<h3>Maquinas registradas</h3>");
                if (conMaquinas == null || conMaquinas.isEmpty()) {
                    out.print("<h3>No se encuentran Maquinas registradas<h3>");
                } else {
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width: 100%;'>");
                    out.print("<tr>");
                    out.print("<th>Nombre</th>");
                    out.print("<th>Ubicacion/Area</th>");
                    out.print("<th>Modificar</th>");
                    out.print("<th>Estado</th>");
                    out.print("</tr>");
                    for (int i = 0; i < conMaquinas.size(); i++) {
                        Object[] obj_maquinas = (Object[]) conMaquinas.get(i);
                        if (obj_maquinas[5].equals(1)) {
                            out.print("<tr>");
                            out.print("<td align='center'>" + obj_maquinas[4] + "</td>");
                            out.print("<td align='center'>" + obj_maquinas[7] + "/" + obj_maquinas[6] + "</td>");
                            out.print("<td align='center' ><a href='Maquinas?op=1&idM=" + obj_maquinas[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("<td align='center' ><a href='Maquinas?op=4&idM=" + obj_maquinas[0] + "&est=" + 0 + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='30' height='30.5' title='Desactivar maquina'/></a></td>");
                            out.print("</tr>");
                        } else {
                            out.print("<tr>");
                            out.print("<td align='center'><b style='color:red;'>" + obj_maquinas[4] + "</b></td>");
                            out.print("<td align='center'><b style='color:red;'>" + obj_maquinas[7] + "/" + obj_maquinas[6] + "</b></td>");
                            out.print("<td align='center' ><a href='Maquinas?op=1&idM=" + obj_maquinas[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("<td align='center' ><a href='Maquinas?op=4&idM=" + obj_maquinas[0] + "&est=" + 1 + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='Logo' width='25' height='25.5' title='Activar maquina'/></a></td>");
                            out.print("</tr>");
                        }
                    }
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 15);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                // </editor-fold>
            }

            out.print("<div class='cleaner'></div></div>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_resultados.class.getName()).log(Level.SEVERE, null, ex);
        }


        return super.doStartTag();

    }
}
