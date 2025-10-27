package Tags;

import Controladoras.UsuarioJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_usuario extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        int id_rol = Integer.parseInt(pageContext.getSession().getAttribute("Id_rol").toString());
        int id_usuarioS = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
        UsuarioJpaController jpa_usuario = new UsuarioJpaController();
        List lst_usuario = null;
        String modulo = pageContext.getRequest().getAttribute("Modulo").toString();
        try {
            if (modulo.equals("Usa")) {
                //<editor-fold defaultstate="collapsed" desc="modulo usuarios">
                int id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("id_usuario").toString());
                List lst_usuarios = jpa_usuario.consultarUsuarios();
                List lst_roles = jpa_usuario.consultarRoles();
                out.print("<div style='float:right'><a href='#' data-toggle=\"modal\" data-target=\"#Registrar\"><i class='fa fa-plus fa-lg' style='color:#292929'></i></a></div>");
                out.print("<h3>Usuario</h3>");
                if (id_usuario == 0) {
                    //<editor-fold defaultstate="collapsed" desc="registrar">
                    out.print("<div class='modal fade' id='Registrar' role='dialog' data-backdrop='static' data-keyboard='false'>");
                    out.print("<div class='modal-dialog modal-lg'>");
                    out.print("<div class='modal-content'>");
                    out.print("<form action='Usuario?opc=2' name='formA' method='post'>");
                    out.print("<div class='modal-header'>");
                    out.print("<a href='Usuario?opc=1&mod=Usa' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Registrar</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center'>");
                    out.print("<table style='width:90%;font-size:12px'>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b class='title'>Nombre(s): </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_nombre' id='nombre-id' autocomplete='off' placeholder='Nombres' onchange='Javascript:this.value = this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b class='title'>Apellido(s): </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_apellido' id='apellido-id' autocomplete='off' placeholder='Apellido' onchange='Javascript:this.value = this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b class='title'>Documento: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_documento' id='documento-id' autocomplete='off' placeholder='Documento' onchange='Javascript:this.value = this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b class='title'>Codigo: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_codigo' id='codigo-id' autocomplete='off' placeholder='Documento' onchange='Javascript:this.value = this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b class='title'>Usuario: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_usuario' id='usuario-id' autocomplete='off' placeholder='Usuario' onchange='Javascript:this.value = this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b class='title'>Firma: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_firma' id='firma-id' autocomplete='off' placeholder='Firma' onchange='Javascript:this.value = this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b class='title'>Rol: </b><br>");
                    out.print("<select name='slc_rol' id='rol-id' required>");
                    out.print("<option value='' style='display:none;'>Seleccione rol</option>");
                    for (int i = 0; i < lst_roles.size(); i++) {
                        Object[] obj_rol = (Object[]) lst_roles.get(i);
                        out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[1] + "</option>");
                    }
                    out.print("</select>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b class='title'>Correo: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_correo' id='correo-id' autocomplete='off' placeholder='Correo' onchange='Javascript:this.value = this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<div class='modal-footer'>");
                    out.print("<input type='submit' value='Registrar'>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="modificar usuario">
                    lst_usuario = jpa_usuario.consultaUsuarioId(id_usuario);
                    Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                    out.print("<div class='modal fade' id='Modificar' role='dialog' data-backdrop='static' data-keyboard='false'>");
                    out.print("<div class='modal-dialog modal-lg'>");
                    out.print("<div class='modal-content'>");
                    out.print("<form action='Usuario?opc=3' name='formA' method='post'>");
                    out.print("<input type='hidden' name='idU' value='" + obj_usuario[0] + "'>");
                    out.print("<div class='modal-header'>");
                    out.print("<a href='Usuario?opc=1&mod=Usa' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Modificar</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center'>");
                    out.print("<table style='width:90%;font-size:12px'>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b class='title'>Nombre(s): </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_nombre' id='nombre-id' autocomplete='off' value='" + obj_usuario[1] + "' placeholder='Nombres' onchange='Javascript:this.value = this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b class='title'>Apellido(s): </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_apellido' id='apellido-id' autocomplete='off' value='" + obj_usuario[2] + "' placeholder='Apellido' onchange='Javascript:this.value = this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b class='title'>Documento: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_documento' id='documento-id' autocomplete='off' value='" + obj_usuario[3] + "' placeholder='Documento' onchange='Javascript:this.value = this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b class='title'>Codigo: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_codigo' id='codigo-id' autocomplete='off' value='" + obj_usuario[4] + "' placeholder='Documento' onchange='Javascript:this.value = this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b class='title'>Usuario: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_usuario' id='usuario-id' autocomplete='off' value='" + obj_usuario[5] + "' placeholder='Usuario' onchange='Javascript:this.value = this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b class='title'>Firma: </b><br>");
                    out.print("<input type='password' class='form-control' name='txt_firma' id='firma-id' autocomplete='off' value='" + obj_usuario[11] + "' placeholder='Firma' onchange='Javascript:this.value = this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b class='title'>Rol: </b><br>");
                    out.print("<select name='slc_rol' id='rol-id' required>");
                    out.print("<option value='" + obj_usuario[6] + "' style='display:none;'>" + obj_usuario[7] + "</option>");
                    for (int i = 0; i < lst_roles.size(); i++) {
                        Object[] obj_rol = (Object[]) lst_roles.get(i);
                        out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[1] + "</option>");
                    }
                    out.print("</select>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b class='title'>Correo: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_correo' autocomplete='off' id='correo-id' value='" + obj_usuario[8] + "' placeholder='Correo' onchange='Javascript:this.value = this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<div class='modal-footer'>");
                    out.print("<div style='margin-right:79%'>");
                    out.print("<a href='Usuario?opc=5&idU=" + id_usuario + "'><b class='naranja'>Reestaurar contraseña</b></a>");
                    out.print("</div>");
                    out.print("<input type='submit' value='Modificar'>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<script>");
                    out.print("$(\"#Modificar\").modal(\"show\");");
                    out.print("</script>");
                    //</editor-fold>
                }
                if (lst_usuarios != null) {
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th>Nombre(s)</th>");
                    out.print("<th>Apellido(s)</th>");
                    out.print("<th>Documento</th>");
                    out.print("<th>Codigo</th>");
                    out.print("<th>Usuario</th>");
                    out.print("<th>Rol</th>");
                    out.print("<th>Correo</th>");
                    out.print("<th>Modificar</th>");
                    out.print("<th>Estado</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_usuarios.size(); i++) {
                        Object[] obj_usuario = (Object[]) lst_usuarios.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_usuario[1] + "</td>");
                        out.print("<td>" + obj_usuario[2] + "</td>");
                        out.print("<td>" + obj_usuario[3] + "</td>");
                        out.print("<td>" + obj_usuario[4] + "</td>");
                        out.print("<td>" + obj_usuario[5] + "</td>");
                        out.print("<td>" + obj_usuario[7] + "</td>");
                        out.print("<td>" + obj_usuario[8] + "</td>");
                        out.print("<td align='center'><a href='Usuario?opc=1&mod=Usa&idU=" + obj_usuario[0] + "'><i class='fa fa-pencil-alt fa-lg' style='color:#292929'></i></a></td>");
                        if ((Integer) obj_usuario[9] == 1) {
                            out.print("<td align='center'><a href='Usuario?opc=4&idU=" + obj_usuario[0] + "&est=0'><i class='fa fa-check fa-lg' style='color:#008000'></i></a></td>");
                        } else {
                            out.print("<td align='center'><a href='Usuario?opc=4&idU=" + obj_usuario[0] + "&est=1'><i class='fa fa-times fa-lg' style='color:#ED4C67'></i></a></td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',14);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                } else {
                    out.print("<br><b>No se encontraron resultados</b>");
                }
                //</editor-fold>
            }
            if (modulo.equals("Flt")) {
                String fechaI = pageContext.getRequest().getAttribute("fechaInicio").toString();
                String fechaF = pageContext.getRequest().getAttribute("fechaFin").toString();
                String filtro = pageContext.getRequest().getAttribute("filtro").toString();
                String rol = pageContext.getRequest().getAttribute("cargo").toString();
                String usuarios = pageContext.getRequest().getAttribute("usuarios").toString();
                int pendientes = Integer.parseInt(pageContext.getRequest().getAttribute("pendientes").toString());
                int casos = Integer.parseInt(pageContext.getRequest().getAttribute("casos").toString());
                int actividades = Integer.parseInt(pageContext.getRequest().getAttribute("actividades").toString());
                out.print("<h3>Filtro General</h3>");
                out.print("<div class='panel-group' id='accordion' style='overflow-y: scroll;max-height: 94%;'>");
                if (pendientes == 1) {
                    //<editor-fold defaultstate="collapsed" desc="Pendientes">
                    List lst_pendientes = jpa_usuario.consultaPendientesFiltro(filtro, fechaI, fechaF, usuarios, rol);
                    out.print("<div class='panel panel-default'>");
                    out.print("<div class='panel-heading'>");
                    out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#Pendientes'>Pendientes</a></h4><span class='label pull-right label-warning'>" + (((lst_pendientes != null) ? lst_pendientes.size() : 0)) + "</span>");
                    out.print("</div>");
                    out.print("<div id='Pendientes' class='panel-collapse collapse'>");
                    out.print("<div class='panel-body'>");
                    if (lst_pendientes != null) {
                        out.print("<table class='table'>");
                        for (int i = 0; i < lst_pendientes.size(); i++) {
                            Object[] obj_pendientes = (Object[]) lst_pendientes.get(i);
                            out.print("<tr>");
                            out.print("<td colspan='4' style='background-color: #ddd;'></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>Fecha: </b>" + obj_pendientes[4] + "<hr/><b class='title'>Asunto: </b>" + obj_pendientes[12] + "</td>");
                            out.print("<td style='width:70%' valign='top'><b class='title'>Pendiente: </b>" + obj_pendientes[1] + "</td>");
                            out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>De: </b>" + obj_pendientes[9] + "<hr /><b class='title'>Para: </b>" + obj_pendientes[10] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='width:70%' valign='top'>");
                            if (!(obj_pendientes[2] == null && obj_pendientes[7] == null && obj_pendientes[8] == null && obj_pendientes[11] == null)) {
                                out.print("<b>" + obj_pendientes[8] + "&nbsp;" + obj_pendientes[11] + "</b><br><b class='title'>Solución: </b>" + obj_pendientes[2] + "");
                            } else {
                                out.print("<b class='naranja'>No se ha solucionado el pendiente</b>");
                            }
                            out.print("</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    } else {
                        out.print("<b>No se encontraron resultados</b>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                }
                if (casos == 1) {
                    //<editor-fold defaultstate="collapsed" desc="Casos">
                    List lst_casos = jpa_usuario.consultaCasosFiltro(filtro, fechaI, fechaF, usuarios, rol);
                    out.print("<div class='panel panel-default'>");
                    out.print("<div class='panel-heading'>");
                    out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#Casos'>Casos</a></h4><span class='label pull-right label-warning'>" + (((lst_casos != null) ? lst_casos.size() : 0)) + "</span>");
                    out.print("</div>");
                    out.print("<div id='Casos' class='panel-collapse collapse'>");
                    out.print("<div class='panel-body'>");
                    if (lst_casos != null) {
                        out.print("<table class='table'>");
                        for (int i = 0; i < lst_casos.size(); i++) {
                            Object[] obj_casos = (Object[]) lst_casos.get(i);
                            out.print("<tr>");
                            out.print("<td colspan='4' style='background-color: #ddd;'></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>Fecha: </b>" + obj_casos[1] + "<hr/><b class='title'>Prioridad: </b>" + obj_casos[6] + "</td>");
                            out.print("<td style='width:70%' valign='top'><b class='title'>Caso: </b>" + obj_casos[5] + "</td>");
                            out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>De: </b>" + obj_casos[4] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='width:70%' valign='top'><b class='title'>Solución: </b>" + obj_casos[9] + "<br><div style='float:right'><b>Responsable: </b>" + obj_casos[10] + "&nbsp;|&nbsp;" + obj_casos[8] + "</div></td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    } else {
                        out.print("<b>No se encuentran resultados</b>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                }
                if (actividades == 1) {
                    List lst_actividades = jpa_usuario.consultaActividadesFiltro(filtro, fechaI, fechaF, usuarios, rol);
                    List lst_actividadesR = jpa_usuario.consultaActividadesReportanteFiltro(filtro, fechaI, fechaF, usuarios, rol);
                    out.print("<div class='panel panel-default'>");
                    out.print("<div class='panel-heading'>");
                    out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#Actividades'>Actividades</a></h4><span class='label pull-right label-warning'>" + (((lst_actividades != null && lst_actividadesR != null) ? (lst_actividades.size() + lst_actividadesR.size()) : ((lst_actividades != null) ? lst_actividades.size() : ((lst_actividadesR != null) ? lst_actividadesR.size() : 0)))) + "</span>");
                    out.print("</div>");
                    out.print("<div id='Actividades' class='panel-collapse collapse'>");
                    out.print("<div class='panel-body'>");
                    out.print("<div class='panel-group' id='accordion2'>");
                    //<editor-fold defaultstate="collapsed" desc="Actividades">
                    out.print("<div class='panel panel-default'>");
                    out.print("<div class='panel-heading'>");
                    out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion2' href='#ActividadesG'>Actividades</a></h4><span class='label pull-right label-warning'>" + (((lst_actividades != null) ? lst_actividades.size() : 0)) + "</span>");
                    out.print("</div>");
                    out.print("<div id='ActividadesG' class='panel-collapse collapse'>");
                    out.print("<div class='panel-body' style='overflow-x: scroll;'>");
                    if (lst_actividades != null) {
                        out.print("<div id='NavPosicion'></div>");
                        out.println("<table class='table' id='resultados'>");
                        out.println("<tr>");
                        out.println("<th align='center' style='width:15%'>Fecha Registro</th>");
                        out.println("<th align='center' style='width:15%'>Asunto</th>");
                        out.println("<th align='center' style='width:70%'>Actividades</th>");
                        out.println("</tr>");
                        for (int i = 0; i < lst_actividades.size(); i++) {
                            Object[] obj_actividades = (Object[]) lst_actividades.get(i);
                            out.println("<tr>");
                            out.println("<td align='center'>" + obj_actividades[1] + "</td>");
                            out.println("<td>" + obj_actividades[3] + "<hr><b>Actividad de: <br>" + obj_actividades[8] + "</b></td>");
                            out.println("<td valign='top'>" + obj_actividades[4] + "</td>");
                            out.println("</tr>");
                        }
                        out.println("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados',5);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    } else {
                        out.print("<b>No se encuentran resultados</b>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="Actividades Reportadas">
                    out.print("<div class='panel panel-default'>");
                    out.print("<div class='panel-heading'>");
                    out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion2' href='#ActividadesR'>Actividades Reportante</a></h4><span class='label pull-right label-warning'>" + (((lst_actividadesR != null) ? lst_actividadesR.size() : 0)) + "</span>");
                    out.print("</div>");
                    out.print("<div id='ActividadesR' class='panel-collapse collapse'>");
                    out.print("<div class='panel-body'>");
                    if (lst_actividadesR != null) {
                        out.print("<table class='table' id='resultados'>");
                        for (int i = 0; i < lst_actividadesR.size(); i++) {
                            Object[] obj_actividadesR = (Object[]) lst_actividadesR.get(i);
                            out.print("<tr>");
                            out.print("<td colspan='5' style='background-color: #ddd;'></d>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='width:18%'><b class='title'>Fecha: </b>" + obj_actividadesR[9] + "</td>");
                            out.print("<td style='width:23%'><b class='title'>Reportante: </b>" + obj_actividadesR[1] + "</td>");
                            if (id_rol == 5) {
                                out.print("<td style='width:18%'><b class='title'>Aplicativo: </b>" + obj_actividadesR[8] + "</td>");
                            } else {
                                out.print("<td style='width:18%'><b class='title'>Equipo: </b>" + obj_actividadesR[3] + "</td>");
                            }
                            out.print("<td style='width:23%'><b class='title'>Tipo Soporte: </b>" + obj_actividadesR[6] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='2' valign='top'>" + obj_actividadesR[12] + "</td>");
                            out.print("<td colspan='2' valign='top'>" + obj_actividadesR[13] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b class='title'>Fecha Reportante: </b>" + obj_actividadesR[9] + "</td>");
                            out.print("<td><b class='title'>Fecha Ejecucion: </b>" + obj_actividadesR[10] + "</td>");
                            out.print("<td><b class='title'>Fecha Fin: </b>" + obj_actividadesR[11] + "</td>");
                            out.print("<td align='center'><b>Parada Equipo: " + obj_actividadesR[16] + "&nbsp;|&nbsp;Produccion: " + obj_actividadesR[17] + "</b></td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    } else {
                        out.print("<b>No se encontraron resultados</b>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                out.print("</div>");
                out.print("<script>");
                out.print("window.onload=buscar('" + filtro + "')");
                out.print("</script>");
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
