package Tags;

import Controladoras.PendienteJpaController;
import Controladoras.UsuarioJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_pendiente extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        UsuarioJpaController jpa_usuario = new UsuarioJpaController();
        PendienteJpaController jpa_pendiente = new PendienteJpaController();
        int id_usuario = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
        int id_rol = Integer.parseInt(pageContext.getSession().getAttribute("Id_rol").toString());
        String fecha_inicial = pageContext.getSession().getAttribute("Fch_inicial").toString();
        String fecha_final = pageContext.getSession().getAttribute("Fch_final").toString();
        List lst_roles = jpa_usuario.consultarRoles();
        String modulo = pageContext.getRequest().getAttribute("Pendiente").toString();
        List lst_pendientes = null;
        List lst_pendiente = null;
        int id_pendiente = 0;
        try {
            if (modulo.equals("R")) {
                //<editor-fold defaultstate="collapsed" desc="registrar_pendiente">
                out.print("<h3>Nuevo Pendiente</h3>");
                out.print("<form action='Pendiente?opc=2' name='formP' method='post'>");
                out.print("<div style='display:flex; margin:1%;'>");
                out.print("<div>");
                out.print("<input type='text' class='form-control' name='txt_asunto' id='asunto-id' placeholder='Asunto'  onchange='javascript:this.value=this.value.toUpperCase();' required/>");
                out.print("</div>");
                out.print("<div>");
                out.print("<b>Para: </b>&nbsp;&nbsp;");
                out.print("<select class='selectpicker show-menu-arrow' name='slc_cargo' id='cargo-id' required>");
                out.print("<option value='' style='display:none'>Seleccione Cargo</option>");
                for (int i = 0; i < lst_roles.size(); i++) {
                    Object[] obj_roles = (Object[]) lst_roles.get(i);
                    if (Integer.parseInt(obj_roles[0].toString()) > 1) {
                        out.println("<option value=" + obj_roles[0] + ">" + obj_roles[1] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                out.print("<textarea id='editor' name='txt_descripcion' width='100%' height='100%'></textarea>");
                out.print("<br>");
                out.print("<input type='submit' value='Enviar'>");
                out.print("</form>");
//</editor-fold>
            }
            if (modulo.equals("M")) {
                //<editor-fold defaultstate="collapsed" desc="modificar pendiente">
                id_pendiente = Integer.parseInt(pageContext.getRequest().getAttribute("id_pendiente").toString());
                lst_pendiente = jpa_pendiente.consultaPendienteId(id_pendiente);
                Object[] obj_pendiente = (Object[]) lst_pendiente.get(0);
                out.print("<h3>Modificar Pendiente</h3>");
                out.print("<form action='Pendiente?opc=5' name='formP' method='post'>");
                out.print("<input type='hidden' name='idP' value='" + id_pendiente + "'>");
                out.print("<div style='display:flex; margin:1%;'>");
                out.print("<div style='width:367px;'>");
                out.print("<input type='text' class='form-control' name='txt_asunto' value='" + obj_pendiente[12] + "' id='asunto-id' style='padding: 2px 5px;' onchange='javascript:this.value=this.value.toUpperCase();' required/>");
                out.print("</div>");
                out.print("<div>");
                out.print("<b>Para: </b>&nbsp;&nbsp;");
                out.print("<select class='selectpicker show-menu-arrow' name='slc_cargo' id='cargo-id' required>");
                out.print("<option value='" + obj_pendiente[6] + "' style='display:none'>" + obj_pendiente[10] + "</option>");
                for (int i = 0; i < lst_roles.size(); i++) {
                    Object[] obj_roles = (Object[]) lst_roles.get(i);
                    if (Integer.parseInt(obj_roles[0].toString()) > 1) {
                        out.println("<option value=" + obj_roles[0] + ">" + obj_roles[1] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                out.print("<textarea id='editor' name='txt_descripcion' width='100%' height='50%'>" + ((obj_pendiente[1] != null) ? obj_pendiente[1].toString().replace("<div>", "<div contenteditable='true'>") : "<div contenteditable='true'><p></p></div>") + "</textarea>");
                out.print("<div style='float:right; margin:1%;'>");
                out.print("<input type='submit' value='Modificar'>");
                out.print("</div>");
                out.print("</form>");
                //</editor-fold>
            }
            if (modulo.equals("S")) {
                //<editor-fold defaultstate="collapsed" desc="solucionar pendiente">
                id_pendiente = Integer.parseInt(pageContext.getRequest().getAttribute("id_pendiente").toString());
                lst_pendiente = jpa_pendiente.consultaPendienteId(id_pendiente);
                Object[] obj_pendiente = (Object[]) lst_pendiente.get(0);
                out.print("<h3>Solucionar Pendiente</h3>");
                out.print("<form action='Pendiente?opc=3' name='formP' method='post'>");
                out.print("<input type='hidden' name='idP' value='" + id_pendiente + "'>");
                if (obj_pendiente[2] != null) {
                    out.print("<input type='hidden' name='mod' value='1'>");
                }
                out.print("<table class='table'>");
                out.print("<tr>");
                out.print("<td><b class='title'>Fecha: </b>" + obj_pendiente[4] + "&nbsp;|&nbsp;<b class='title'>Asunto: </b>" + obj_pendiente[12] + "</td>");
                out.print("<td><b class='title'>De: </b>" + obj_pendiente[9] + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='2'><b class='title'>Pendiente: </b><br/>" + obj_pendiente[1] + "</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("<textarea id='editor' name='txt_descripcion' width='100%' height='50%'>" + ((obj_pendiente[2] != null) ? obj_pendiente[2].toString().replace("<div>", "<div contenteditable='true'>") : "<div contenteditable='true'><p></p></div>") + "</textarea>");
                out.print("<br>");
                out.print("<input type='submit' value='Solucionar'>");
                out.print("</form>");
//</editor-fold>
            }
            if (modulo.equals("C") || modulo.equals("C_All")) {
                //<editor-fold defaultstate="collapsed" desc="consulta pendientes">
                if (modulo.equals("C")) {
                    lst_pendientes = jpa_pendiente.consultarPendientesUsuario(id_rol, fecha_inicial, fecha_final);
                    out.print("<h3>Mis Pendientes</h3>");
                } else {
                    lst_pendientes = jpa_pendiente.ConsultarAllPendientes();
                    out.print("<h3>Todos los Pendientes</h3>");
                }
                out.print("<div style='display:flex;justify-content: space-between; align-items: center;'>");
                out.print("<div>");
                out.print("<div id='NavPosicion'></div>");
                out.print("</div>");
                out.print("<div>");
                out.print("<input type='text' style='margin:6px;' name='txt_bus' id='Txt_filtro' class='form-control'  onkeyup='Filtrar()'  placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div style='width: 100%; height:87%; max-width: 100%; max-height:88%; overflow:auto'>");
                if (lst_pendientes != null) {
                    out.print("<table class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th class='sticky4' style='width:10%'>FECHA</th>");
                    out.print("<th class='sticky4' style='width:10%'>DE</th>");
                    out.print("<th class='sticky4' style='width:10%'>PARA</th>");
                    out.print("<th class='sticky4' style='width:15%'>Asunto</th>");
                    out.print("<th class='sticky4' style='width:50%'>PENDIENTE</th>");
                    out.print("<th class='sticky4' style='width:5%'>MOD/SOl</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_pendientes.size(); i++) {
                        Object[] obj_pendiente = (Object[]) lst_pendientes.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_pendiente[4] + "</td>");
                        out.print("<td>" + obj_pendiente[9] + "</td>");
                        out.print("<td>" + obj_pendiente[10] + "</td>");
                        out.print("<td>" + obj_pendiente[12] + "</td>");
                        out.print("<td>" + obj_pendiente[1] + "</td>");
                        out.print("<td align='center'>");
                        if (id_rol == Integer.parseInt(obj_pendiente[6].toString())) {
                            out.print("<a href='Pendiente?opc=1&mod=S&idP=" + obj_pendiente[0] + "' class='icon' title='Solucionar'><i class='fa fa-file-signature fa-lg'></i></a>");
                        } else {
                            out.print("<a href='#' class='icon' title='Sin permisos'><i class='fas fa-exclamation'></i></a>");
                        }
                        if (id_usuario == Integer.parseInt(obj_pendiente[5].toString()) && obj_pendiente[2] == null) {
                            out.print("<hr><a href='Pendiente?opc=1&mod=M&idP=" + obj_pendiente[0] + "' class='icon' title='Modificar'><i class='fa fa-pencil-alt fa-lg'></i></a>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                } else {
                    out.print("<b class='title'>No se encontraron resultados</b>");
                }
                out.print("</div>");
//</editor-fold>
            }
            if (modulo.equals("CS")) {
                //<editor-fold defaultstate="collapsed" desc="consulta solucionados">
                lst_pendientes = jpa_pendiente.consultarPendientesSolucionados(fecha_inicial, fecha_final);
                out.print("<h3>Pendientes Solucionados</h3>");
                out.print("<div style='display:flex;justify-content: space-between; align-items: center;'>");
                out.print("<div>");
                out.print("<div id='NavPosicion'></div>");
                out.print("</div>");
                out.print("<div>");
                out.print("<input type='text' style='margin:6px;' name='txt_bus' id='Txt_filtro' class='form-control'  onkeyup='Filtrar()'  placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div style='width: 100%; height:87%; max-width: 100%; max-height:88%; overflow:auto'>");
                if (lst_pendientes != null) {
                    out.print("<table class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th style='widht:10%' class='sticky4' >FECHA</th>");
                    out.print("<th style='widht:14%' class='sticky4' >ASUNTO</th>");
                    out.print("<th style='widht:30%' class='sticky4' >PENDIENTE</th>");
                    out.print("<th style='widht:30%' class='sticky4' >SOLUCION</th>");
                    out.print("<th style='widht:13%' class='sticky4' >RESPONSABLES</th>");
                    out.print("<th style='widht:5%' class='sticky4' >OPCION</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_pendientes.size(); i++) {
                        Object[] obj_pendiente = (Object[]) lst_pendientes.get(i);
                        out.print("<tr>");
                        out.print("<td valign='top'>" + obj_pendiente[4] + "</td>");
                        out.print("<td valign='top'>" + obj_pendiente[13] + "</td>");
                        out.print("<td valign='top'>" + obj_pendiente[1] + "</td>");
                        out.print("<td valign='top'>" + obj_pendiente[2] + "<br><div style='float:right'><b>Responsable: </b>" + obj_pendiente[11] + "&nbsp;|&nbsp;" + obj_pendiente[4] + "</div></td>");
                        out.print("<td valign='top'><b class='title'>De: </b>" + obj_pendiente[9] + "<hr /><b class='title'>Para: </b>" + obj_pendiente[10] + "</td>");
                        out.print("<td align='center'>");
                        if (Integer.parseInt(obj_pendiente[3].toString()) == 1) {
                            out.print("<i class='fa fa-check-double fa-lg'></i>");
                        } else {
                            if (id_usuario == Integer.parseInt(obj_pendiente[7].toString())) {
                                out.print("<a href='Pendiente?opc=1&mod=S&idP=" + obj_pendiente[0] + "' class='icon' title='Modificar Solucion'><i class='fa fa-pencil-alt fa-lg'></i></a>");
                            } else {
                                out.print("<a href='#' class='icon' title='Sin permisos' style='color:#b1b1b1;'><i class='fa fa-times fa-lg'></i></a>");
                            }
                            if (id_rol == 4 || id_rol == 2) {
                                out.print("<hr /><a href='Pendiente?opc=4&idP=" + obj_pendiente[0] + "' class='icon' title='Revisar'><i class='fa fa-check fa-lg' style='color: #ea7200;'></i></a>");
                            }
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                } else {
                    out.print("<br><b class='title'>No se encontraron resultados</b>");
                }
//</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_pendiente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
