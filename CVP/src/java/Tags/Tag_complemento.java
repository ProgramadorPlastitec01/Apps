package Tags;

import Controladores.AreaJpaController;
import Controladores.GrupoJpaController;
import Controladores.TipoCalificacionJpaController;
import Controladores.TipoInformeJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_complemento extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //FIN PERMISOS
            AreaJpaController jpacara = new AreaJpaController();
            TipoCalificacionJpaController jpactcl = new TipoCalificacionJpaController();
            GrupoJpaController jpacgpo = new GrupoJpaController();
            TipoInformeJpaController jpactif = new TipoInformeJpaController();
            //VARIABLE GLOBALES
            List lst_area = null;
            List lst_grupos = null;
            List lst_tipo_calificacion = null;
            List lst_tipo_informe = null;
            String filtro = "";
            if (pageContext.getRequest().getAttribute("Complemento") != null) {
                // <editor-fold defaultstate="collapsed" desc="AREAS">
                if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Modulo_area")) {
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Area</h3>");
                    if (rol.equals("Consulta") || rol.equals("Gestor")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Complemento?opc=3' method='post' onsubmit='checkSubmit();'>");
                        out.print("<b>Área :</b>");
                        out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre del area' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                        out.print("<b>Sigla :</b>");
                        out.print("<input type='text' name='Txt_sigla' id='Txt_sigla' placeholder='Sigla' title='Sigla' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sigla');val1.add(Validate.Presence);</script>");
                        out.print("<b>Correo :</b>");
                        out.print("<textarea name='Txt_correo' id='Txt_correo' placeholder='Correos del area' title='Correos del area' onchange='javascript:this.value=this.value.toUpperCase();'/></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_correo');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    lst_area = jpacara.Areas();
                    out.print("<div id='content'>");
                    out.print("<h3>Areas<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    if (lst_area == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de líneas registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>Area</th>");
                        out.print("<th>Sigla</th>");
                        out.print("<th>Correo</th>");
                        if (!(rol.equals("Consulta") || rol.equals("Gestor"))) {
                            out.print("<th>Estado</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_area.size(); i++) {
                            Object[] obj_areas = (Object[]) lst_area.get(i);
                            if (Integer.parseInt(obj_areas[4].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                            }
                            out.print("<td>" + (i + 1) + "</td>");
                            out.print("<td>" + obj_areas[1] + "</td>");
                            out.print("<td align='center'>" + obj_areas[2] + "</td>");
                            out.print("<td >" + obj_areas[3] + "</td>");
                            if (!(rol.equals("Consulta") || rol.equals("Gestor"))) {
                                if (Integer.parseInt(obj_areas[4].toString()) == 1) {
                                    out.print("<td align='center'><a href='#'  onclick='DesactivarArea(" + obj_areas[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar' /></a></td>");
                                } else {
                                    out.print("<td align='center'><a href='#'  onclick='ActivarArea(" + obj_areas[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Activar' /></a></td>");
                                }
                            }
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
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="TIPO DE CALIFICACION">
                else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Modulo_tipo_calificacion")) {
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Tipo</h3>");
                    if (rol.equals("Consulta") || rol.equals("Gestor")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Complemento?opc=6' method='post' onsubmit='checkSubmit();'>");
                        out.print("<b>Tipo clasificación :</b>");
                        out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre del area' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                        out.print("<b>Descripción :</b>");
                        out.print("<textarea name='Txt_descripcion' id='Txt_descripcion' placeholder='Descripción de tipo de calificacion' title='Descripción de tipo de calificacion' onchange='javascript:this.value=this.value.toUpperCase();'/></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_correo');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    lst_tipo_calificacion = jpactcl.Tipos_calificacion();
                    out.print("<div id='content'>");
                    out.print("<h3>Tipos de calificación<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    if (lst_tipo_calificacion == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de líneas registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>Tipo</th>");
                        out.print("<th>Descripción</th>");
                        if (!(rol.equals("Consulta") || rol.equals("Gestor"))) {
                            out.print("<th>Estado</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_tipo_calificacion.size(); i++) {
                            Object[] obj_tipos = (Object[]) lst_tipo_calificacion.get(i);
                            if (Integer.parseInt(obj_tipos[3].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                            }
                            out.print("<td>" + (i + 1) + "</td>");
                            out.print("<td>" + obj_tipos[1] + "</td>");
                            out.print("<td>" + obj_tipos[2] + "</td>");
                            if (!(rol.equals("Consulta") || rol.equals("Gestor"))) {
                                if (Integer.parseInt(obj_tipos[3].toString()) == 1) {
                                    out.print("<td align='center'><a href='#'  onclick='DesactivarTipoCalificacion(" + obj_tipos[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar' /></a></td>");
                                } else {
                                    out.print("<td align='center'><a href='#'  onclick='ActivarTipoCalificacion(" + obj_tipos[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Activar' /></a></td>");
                                }
                            }
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
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="GRUPOS">
                else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Modulo_grupos")) {
                    lst_grupos = jpacgpo.Grupos();
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Grupo</h3>");
                    if (rol.equals("Consulta") || rol.equals("Gestor")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Complemento?opc=9' method='post' onsubmit='checkSubmit();'>");
                        out.print("<b>Grupo:</b>");
                        out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre del GRUPO' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                        out.print("<b>Asociar a grupo :</b>");
                        out.print("<select name='Cbx_grupo' id='Cbx_grupo' title='Grupos' >");
                        out.print("<option value='0' >Seleccionar grupo</option>");
                        out.print("<option value='N/A' >N/A</option>");
                        for (int i = 0; i < lst_grupos.size(); i++) {
                            Object[] obj_grupos = (Object[]) lst_grupos.get(i);
                            if (obj_grupos[2].toString().equals("N/A")) {
                                out.print("<option value='" + obj_grupos[1] + "' >" + obj_grupos[1] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_grupo');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><b>Color :</b><br />");
                        String color = "";
                        try {
                            String[] color_1 = {"A", "B", "C", "D", "E", "F", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
                            int numRandon1 = (int) Math.round(Math.random() * 16);
                            int numRandon2 = (int) Math.round(Math.random() * 16);
                            int numRandon3 = (int) Math.round(Math.random() * 16);
                            int numRandon4 = (int) Math.round(Math.random() * 16);
                            int numRandon5 = (int) Math.round(Math.random() * 16);
                            int numRandon6 = (int) Math.round(Math.random() * 16);
                            color = "" + color_1[numRandon1] + color_1[numRandon2] + color_1[numRandon3] + color_1[numRandon4] + color_1[numRandon5] + color_1[numRandon6];
                        } catch (Exception e) {
                            color = "898989";
                        }
                        out.print("<input type='color' name='Txt_color' id='Txt_color' value='#" + color + "' placeholder='Color' title='Color'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_color');val1.add(Validate.Presence);</script>");
                        out.print("<br /><br /><input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content'>");
                    out.print("<h3>Grupos<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    if (lst_grupos == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de líneas registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>Tipo</th>");
                        out.print("<th>Descripción</th>");
                        out.print("<th>Color</th>");
                        if (!(rol.equals("Consulta") || rol.equals("Gestor"))) {
                            out.print("<th>Estado</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_grupos.size(); i++) {
                            Object[] obj_grupos = (Object[]) lst_grupos.get(i);
                            if (Integer.parseInt(obj_grupos[3].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                            }
                            out.print("<td>" + (i + 1) + "</td>");
                            if (obj_grupos[2].toString().equals("N/A")) {
                                out.print("<td colspan='2'><b>" + obj_grupos[1] + "</b></td>");
                                out.print("<td align='center'><b style='color:" + obj_grupos[6] + "'>" + obj_grupos[6] + "</td>");
                            } else {
                                out.print("<td>" + obj_grupos[1] + "</td>");
                                out.print("<td>" + obj_grupos[2] + "</td>");
                                out.print("<td align='center'><b style='color:" + obj_grupos[6] + "'>" + obj_grupos[6] + "</td>");
                            }
                            if (!(rol.equals("Consulta") || rol.equals("Gestor"))) {
                                if (Integer.parseInt(obj_grupos[3].toString()) == 1) {
                                    out.print("<td align='center'><a href='#'  onclick='DesactivarGrupo(" + obj_grupos[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar' /></a></td>");
                                } else {
                                    out.print("<td align='center'><a href='#'  onclick='ActivarGrupo(" + obj_grupos[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Activar' /></a></td>");
                                }
                            }
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
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="TIPO DE INFORME">
                if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Modulo_tipo_informe")) {
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Tipo de informe</h3>");
                    if (rol.equals("Consulta") || rol.equals("Gestor")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Complemento?opc=12' method='post' onsubmit='checkSubmit();'>");
                        out.print("<b>Tipo :</b>");
                        out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre del area' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script><br />");
                        out.print("<b>Vigencia :</b><br />");
                        out.print("<input type='radio' name='Rdb_vigencia' value='1' id='Rdb_vigencia' title='Vigencia' /> SI"
                                + "<input type='radio' name='Rdb_vigencia' value='0' id='Rdb_vigencia' title='Vigencia' checked/> NO");
                        out.print("<br /><b>Color :</b><br />");
                        String color = "";
                        try {
                            String[] color_1 = {"A", "B", "C", "D", "E", "F", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
                            int numRandon1 = (int) Math.round(Math.random() * 16);
                            int numRandon2 = (int) Math.round(Math.random() * 16);
                            int numRandon3 = (int) Math.round(Math.random() * 16);
                            int numRandon4 = (int) Math.round(Math.random() * 16);
                            int numRandon5 = (int) Math.round(Math.random() * 16);
                            int numRandon6 = (int) Math.round(Math.random() * 16);
                            color = "" + color_1[numRandon1] + color_1[numRandon2] + color_1[numRandon3] + color_1[numRandon4] + color_1[numRandon5] + color_1[numRandon6];
                        } catch (Exception e) {
                            color = "898989";
                        }
                        out.print("<input type='color' name='Txt_color' id='Txt_color' value='#" + color + "' placeholder='Color' title='Color'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_color');val1.add(Validate.Presence);</script>");
                        out.print("<br /><br /><input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    lst_tipo_informe = jpactif.Tipos_informe();
                    out.print("<div id='content'>");
                    out.print("<h3>Tipos de informe<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    if (lst_tipo_informe == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de tipo de informe</b>");
                        out.print("</center>");
                    } else {
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>Tipo</th>");
                        out.print("<th>Color</th>");
                        out.print("<th>Vigencia</th>");
                        if (!(rol.equals("Consulta") || rol.equals("Gestor"))) {
                            out.print("<th>Estado</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_tipo_informe.size(); i++) {
                            Object[] obj_tipo_informe = (Object[]) lst_tipo_informe.get(i);
                            if (Integer.parseInt(obj_tipo_informe[4].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                            }
                            out.print("<td>" + (i + 1) + "</td>");
                            out.print("<td>" + obj_tipo_informe[1] + "</td>");
                            out.print("<td align='center'><b style='color:" + obj_tipo_informe[3] + "'>" + obj_tipo_informe[3] + "</td>");
                            out.print("<td >" + ((Integer.parseInt(obj_tipo_informe[2].toString()) > 0) ? "SI" : "NO") + "</td>");
                            if (!(rol.equals("Consulta") || rol.equals("Gestor"))) {
                                if (Integer.parseInt(obj_tipo_informe[4].toString()) == 1) {
                                    out.print("<td align='center'><a href='#'  onclick='DesactivarTipoInforme(" + obj_tipo_informe[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar' /></a></td>");
                                } else {
                                    out.print("<td align='center'><a href='#'  onclick='ActivarTipoInforme(" + obj_tipo_informe[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Activar' /></a></td>");
                                }
                            }
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
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_complemento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
