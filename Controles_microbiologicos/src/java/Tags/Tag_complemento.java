package Tags;

import controladoras.AreaMuestradaJpaController;
import controladoras.DesinfectanteJpaController;
import controladoras.TipoAreaJpaController;
import controladoras.TipoNivelJpaController;
import controladoras.UnidadesJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_complemento extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            HttpSession sesion = pageContext.getSession();
            String rol = sesion.getAttribute("Rol").toString();
            String usuario = sesion.getAttribute("Nombre").toString();
            //FIN PERMISOS
            //JPAS
            DesinfectanteJpaController jpacdsf = new DesinfectanteJpaController();
            AreaMuestradaJpaController jpacame = new AreaMuestradaJpaController();
            TipoAreaJpaController jpactar = new TipoAreaJpaController();
            UnidadesJpaController jpacumd = new UnidadesJpaController();
            TipoNivelJpaController jpa_tipoN = new TipoNivelJpaController();
            //VARIABLE GLOBALES
            List lst_desinfectante = null;
            List lst_areas_muestradas = null;
            List lst_tipos_areas = null;
            List lst_unidad_medida = null;
            List lst_tipos_nivel = null;
            List lst_tipo_nivel = null;
          
            String filtro = "";
            if (pageContext.getRequest().getAttribute("Complemento") != null) {
                if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Desinfectantes")) {
                    //<editor-fold defaultstate="collapsed" desc="desinfectantes">
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Desinfectante</h3>");
                    out.print("<form action='Complemento?opc=2' method='post'>");
                    out.print("<b>Desinfectante :</b>");
                    out.print("<input type='text' name='Txt_desinfectante' id='Txt_desinfectante' placeholder='Nombre Desinfectante' title='Nombre del desinfectante' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_desinfectante');val1.add(Validate.Presence);</script>");
                    out.print("<input type='submit' value='Registrar' />");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    lst_desinfectante = jpacdsf.Consultar_desinfectantes();
                    out.print("<div id='content'>");
                    if (lst_desinfectante == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de desinfectantes registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Desinfectantes</h3>");
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th style='width:30px'>#</th>");
                        out.print("<th>Desinfectante</th>");
                        out.print("<th>Estado</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_desinfectante.size(); i++) {
                            Object[] obj_desinfectante = (Object[]) lst_desinfectante.get(i);
                            out.print("<tr>");
                            out.print("<td align=center><b>" + (i + 1) + "</b></td>");
                            if ((Integer) obj_desinfectante[2] == 0) {
                                out.print("<td>" + obj_desinfectante[1] + "</td>");
                                out.print("<td align='center'><a href='Complemento?opc=1&idD=" + obj_desinfectante[0] + "&est=1'><img src='Interfaz/Contenido/Iconos/Check.png' width='25' heigth='25' title='Activo'></a></td>");
                            } else {
                                out.print("<td><b class='morado'>" + obj_desinfectante[1] + "</b></td>");
                                out.print("<td align='center'><a href='Complemento?opc=1&idD=" + obj_desinfectante[0] + "&est=0'><img src='Interfaz/Contenido/Iconos/Delete.png' width='25' heigth='25' title='Inactivo'></a></td>");
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
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Areas_muestradas")) {
                    //<editor-fold defaultstate="collapsed" desc="Area muestrada">
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Area Muestrada</h3>");
                    out.print("<form action='Complemento?opc=4' method='post'>");
                    out.print("<b>Area Muestrada :</b>");
                    out.print("<input type='text' name='Txt_area_muestrada' id='Txt_area_muestrada' placeholder='Nombre Area Muestrada' title='Nombre del Area Muestrada' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_area_muestrada');val1.add(Validate.Presence);</script>");
                    out.print("<input type='submit' value='Registrar' />");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    lst_areas_muestradas = jpacame.Consultar_areas_muestradas();
                    out.print("<div id='content'>");
                    if (lst_areas_muestradas == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de areas muestradas</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Areas Muestradas</h3>");
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th style='width:30px'>#</th>");
                        out.print("<th>Area Muestrada</th>");
                        out.print("<th>Estado</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_areas_muestradas.size(); i++) {
                            Object[] obj_area_muestrada = (Object[]) lst_areas_muestradas.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + (i + 1) + "</b></td>");
                            if (Integer.parseInt(obj_area_muestrada[2].toString()) == 0) {
                                out.print("<td>" + obj_area_muestrada[1] + "</td>");
                                out.print("<td align='center'><a href='Complemento?opc=3&idA=" + obj_area_muestrada[0] + "&est=1'><img src='Interfaz/Contenido/Iconos/Check.png' width='25' heigth='25' title='Activo'></a></td>");
                            } else {
                                out.print("<td><b class='morado'>" + obj_area_muestrada[1] + "</b></td>");
                                out.print("<td align='center'><a href='Complemento?opc=3&idA=" + obj_area_muestrada[0] + "&est=0'><img src='Interfaz/Contenido/Iconos/Delete.png' width='25' heigth='25' title='Inactivo'></a></td>");
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
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Tipos_areas")) {
                    //<editor-fold defaultstate="collapsed" desc="Tipo area">
                    lst_tipos_nivel = jpa_tipoN.ConsultaTiposNivel();
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Tipo Area</h3>");
                    out.print("<form action='Complemento?opc=6' method='post'>");
                    out.print("<b>Tipo Area :</b>");
                    out.print("<input type='text' name='Txt_tipo_area' id='Txt_tipo_area' placeholder='Nombre tipo area' title='Nombre tipo area' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tipo_area');val1.add(Validate.Presence);</script>");
                    out.print("<b>Tipo de nivel:</b><br />");
                    out.print("<select name='slc_tipoN' id='slc_tipoN'>");
                    out.print("<option value='' style='display:none'>Seleccionar tipo nivel</option>");
                    for (int i = 0; i < lst_tipos_nivel.size(); i++) {
                        Object[] obj_tipoN = (Object[]) lst_tipos_nivel.get(i);
                        if (Integer.parseInt(obj_tipoN[8].toString()) == 0) {
                            out.print("<option value='" + obj_tipoN[0] + "'>" + obj_tipoN[2] + "</option>");
                        }
                    }
                    out.print("</select><br /><br />");
                    out.print("<script type='text/javascript'>var val1 = new LiveValidation('slc_tipoN');val1.add(Validate.Presence);</script>");
                    out.print("<input type='submit' value='Registrar' />");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    lst_tipos_areas = jpactar.Consultar_tipos_areas();
                    out.print("<div id='content'>");
                    if (lst_tipos_areas == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de tipos de areas</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Tipos De Areas</h3>");
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th style='width:30px'>#</th>");
                        out.print("<th>Tipo Area</th>");
                        out.print("<th>Tipo Nivel</th>");
                        out.print("<th>Estado</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_tipos_areas.size(); i++) {
                            Object[] obj_tipos_areas = (Object[]) lst_tipos_areas.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + (i + 1) + "</b></td>");
                            if (Integer.parseInt(obj_tipos_areas[2].toString()) == 0) {
                                out.print("<td>" + obj_tipos_areas[1] + "</td>");
                                out.print("<td align='center'>" + obj_tipos_areas[5] + "</td>");
                                out.print("<td align='center'><a href='Complemento?opc=5&idTA=" + obj_tipos_areas[0] + "&est=1'><img src='Interfaz/Contenido/Iconos/Check.png' width='25' heigth='25' title='Activo'></a></td>");
                            } else {
                                out.print("<td><b class='morado'>" + obj_tipos_areas[1] + "</b></td>");
                                out.print("<td align='center'><b class='morado'>" + obj_tipos_areas[5] + "</b></td>");
                                out.print("<td align='center'><a href='Complemento?opc=5&idTA=" + obj_tipos_areas[0] + "&est=0'><img src='Interfaz/Contenido/Iconos/Delete.png' width='25' heigth='25' title='Inactivo'></a></td>");
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
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Unidades_medida")) {
                    //<editor-fold defaultstate="collapsed" desc="unidad de medida">
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Unidades De Medidas</h3>");
                    out.print("<form action='Complemento?opc=8' method='post'>");
                    out.print("<b>Unidad de medida :</b>");
                    out.print("<input type='text' name='Txt_unidad_medida' id='Txt_unidad_medida' placeholder='Nombre unidad de medida' title='Nombre unidad de medida' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_unidad_medida');val1.add(Validate.Presence);</script>");
                    out.print("<input type='submit' value='Registrar' />");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    lst_unidad_medida = jpacumd.Consultar_unidades_media();
                    out.print("<div id='content'>");
                    if (lst_unidad_medida == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de unidades de medida</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Unidades De Medida</h3>");
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th style='width:30px'>#</th>");
                        out.print("<th>Unidad de medida</th>");
                        out.print("<th>Estado</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_unidad_medida.size(); i++) {
                            Object[] obj_unidades_medida = (Object[]) lst_unidad_medida.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + (i + 1) + "</b></td>");
                            if (Integer.parseInt(obj_unidades_medida[2].toString()) == 0) {
                                out.print("<td>" + obj_unidades_medida[1] + "</td>");
                                out.print("<td align='center'><a href='Complemento?opc=7&idU=" + obj_unidades_medida[0] + "&est=1'><img src='Interfaz/Contenido/Iconos/Check.png' width='25' heigth='25' title='Activo'></a></td>");
                            } else {
                                out.print("<td><b class='morado'>" + obj_unidades_medida[1] + "</b></td>");
                                out.print("<td align='center'><a href='Complemento?opc=7&idU=" + obj_unidades_medida[0] + "&est=0'><img src='Interfaz/Contenido/Iconos/Delete.png' width='25' heigth='25' title='Inactivo'></a></td>");
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
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Tipo_nivel")) {
                    //<editor-fold defaultstate="collapsed" desc="tipo nivel">
                    int id_tipoNivel = Integer.parseInt(pageContext.getRequest().getAttribute("id_tipoNivel").toString());
                    out.print("<div id='sidebar'>");
                    if (id_tipoNivel == 0) {
                        out.print("<h3>Registrar Tipo de nivel</h3>");
                        out.print("<form action='Complemento?opc=10' method='post'>");
                        out.print("<b>Tipo:</b><br />");
                        out.print("<input type='text' name='Txt_tipo' id='Txt_tipo' placeholder='Tipo nivel' title='Tipo nivel' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tipo');val1.add(Validate.Presence);</script>");
                        out.print("<b>Dato:</b><br />");
                        out.print("<input type='number' name='Txt_dato' id='Txt_dato'  placeholder='Dato' title='Dato' onkeyup='validarPCT(this.value)' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dato');val1.add(Validate.Presence);</script>");
                        out.print("<table style='width:100%'>");
                        out.print("<tr>");
                        out.print("<td style='width:50%'>");
                        out.print("<b>Cumple:</b><br />");
                        out.print("<input type='number' name='Txt_cumple' id='Txt_cumple' placeholder='Cumple' title='Cumple' style='width:65%' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cumple');val1.add(Validate.Presence);</script>");
                        out.print("<b>%</b>");
                        out.print("</td>");
                        out.print("<td style='width:50%'>");
                        out.print("<b>Alerta:</b><br />");
                        out.print("<input type='number' name='Txt_alerta' id='Txt_alerta'  placeholder='Alerta' title='Alerta' style='width:65%' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_alerta');val1.add(Validate.Presence);</script>");
                        out.print("<b>%</b>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td>");
                        out.print("<b>Acción:</b><br />");
                        out.print("<input type='number' name='Txt_accion' id='Txt_accion'  placeholder='Accion' title='Accion' style='width:65%' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_accion');val1.add(Validate.Presence);</script>");
                        out.print("<b>%</b>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<b>Incumplimiento:</b><br />");
                        out.print("<input type='number' name='Txt_incumplimiento' id='Txt_incumplimiento' placeholder='Incumplimiento' title='Incumplimiento' style='width:65%' onchange='javascript:this.value=this.value.toUpperCase();' readonly='true'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_incumplimiento');val1.add(Validate.Presence);</script>");
                        out.print("<b>%</b>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    } else {
                        lst_tipo_nivel = jpa_tipoN.ConsultaTipoNivelId(id_tipoNivel);
                        Object[] obj_tipoN = (Object[]) lst_tipo_nivel.get(0);
                        out.print("<h3>Actualizar Tipo de nivel</h3>");
                        out.print("<form action='Complemento?opc=10' method='post'>");
                        out.print("<input type='hidden' name='idTN' value='" + id_tipoNivel + "' />");
                        out.print("<input type='hidden' name='Txt_tipo' value='" + obj_tipoN[2] + "' />");
                        out.print("<b>Tipo:</b><br />");
                        out.print("<b class='negro'>" + obj_tipoN[2] + "</b><br />");
                        out.print("<b>Dato:</b><br />");
                        out.print("<input type='number' name='Txt_dato' id='Txt_dato' value='" + obj_tipoN[3] + "' min='0' max='100' placeholder='Dato' title='Dato' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_dato');val1.add(Validate.Presence);</script>");
                        out.print("<table style='width:100%'>");
                        out.print("<tr>");
                        out.print("<td style='width:50%'>");
                        out.print("<b>Cumple:</b><br />");
                        out.print("<input type='number' name='Txt_cumple' id='Txt_cumple' value='" + obj_tipoN[4] + "' min='0' max='100' placeholder='Cumple' title='Cumple' style='width:65%' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cumple');val1.add(Validate.Presence);</script>");
                        out.print("<b>%</b>");
                        out.print("</td>");
                        out.print("<td style='width:50%'>");
                        out.print("<b>Alerta:</b><br />");
                        out.print("<input type='number' name='Txt_alerta' id='Txt_alerta' value='" + obj_tipoN[5] + "' min='0' max='100' placeholder='Alerta' title='Alerta' style='width:65%' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_alerta');val1.add(Validate.Presence);</script>");
                        out.print("<b>%</b>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td>");
                        out.print("<b>Acción:</b><br />");
                        out.print("<input type='number' name='Txt_accion' id='Txt_accion' value='" + obj_tipoN[6] + "' min='0' max='100' placeholder='Accion' title='Accion' style='width:65%' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_accion');val1.add(Validate.Presence);</script>");
                        out.print("<b>%</b>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<b>Incumplimiento:</b><br />");
                        out.print("<input type='number' name='Txt_incumplimiento' id='Txt_incumplimiento' value='" + obj_tipoN[7] + "' min='0' max='100' placeholder='Incumplimiento' title='Incumplimiento' style='width:65%' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_incumplimiento');val1.add(Validate.Presence);</script>");
                        out.print("<b>%</b>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }

                    out.print("<div class='cleaner'></div></div>");
                    lst_tipos_nivel = jpa_tipoN.ConsultaTiposNivel();
                    out.print("<div id='content'>");
                    out.print("<h3>Tipo nivel</h3>");
                    if (lst_tipos_nivel != null) {
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th align='center'>#</th>");
                        out.print("<th align='center'>Tipo</th>");
                        out.print("<th align='center'>Dato</th>");
                        out.print("<th align='center'>Cumple</th>");
                        out.print("<th align='center'>Alerta</th>");
                        out.print("<th align='center'>Accion</th>");
                        out.print("<th align='center'>Incumplimiento</th>");
                        out.print("<th align='center'>Actualizar</th>");
                        out.print("</tr>");
                        for (int i = 0; i < 10; i++) {
                            Object[] obj_tipoN = (Object[]) lst_tipos_nivel.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + (i + 1) + "</b></td>");
                            out.print("<td>" + (((Integer) obj_tipoN[8] != 1) ? obj_tipoN[2] : "<b class='morado'>" + obj_tipoN[2] + "</b>") + "</td>");
                            out.print("<td align='center'>" + (((Integer) obj_tipoN[8] != 1) ? obj_tipoN[3] : "<b class='morado'>" + obj_tipoN[3] + "</b>") + "</td>");
                            out.print("<td align='center' style='background-color:#D7FFC6'><b> < </b>" + (((Integer) obj_tipoN[8] != 1) ? obj_tipoN[4] : "<b class='morado'>" + obj_tipoN[4] + "</b>") + " )</td>");
                            out.print("<td align='center' style='background-color:#FFFE9B'>[ " + (((Integer) obj_tipoN[8] != 1) ? obj_tipoN[4] : "<b class='morado'>" + obj_tipoN[4] + "</b>") + " - " + (((Integer) obj_tipoN[8] != 1) ? obj_tipoN[5] : "<b class='morado'>" + obj_tipoN[5] + "</b>") + " )</td>");
                            out.print("<td align='center' style='background-color:#FFCB9B'>[ " + (((Integer) obj_tipoN[8] != 1) ? obj_tipoN[5] : "<b class='morado'>" + obj_tipoN[5] + "</b>") + " - " + (((Integer) obj_tipoN[8] != 1) ? obj_tipoN[6] : "<b class='morado'>" + obj_tipoN[6] + "</b>") + " )</td>");
                            out.print("<td align='center' style='background-color:#FFC7C7'>[ " + (((Integer) obj_tipoN[8] != 1) ? obj_tipoN[7] : "<b class='morado'>" + obj_tipoN[7] + "</b>") + " <b> ></b></td>");
                            if ((Integer) obj_tipoN[8] != 1) {
                                out.print("<td align='center'><a href='Complemento?opc=9&idTN=" + obj_tipoN[0] + "'><img src='Interfaz/Contenido/Iconos/Update.png' width='20' heigth='20'></a></td>");
                            } else {
                                out.print("<td align='center'>--</td>");
                            }
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    } else {
                        out.print("<b>No se han encontrado resultados</b>");
                    }
                    out.print("<div class='cleaner'></div></div>");
                    //</editor-fold>
                } 
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_complemento.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    catch (Exception ex) {
            Logger.getLogger(Tag_complemento.class.getName()).log(Level.SEVERE, null, ex);
    }
    return super.doStartTag();
    }
}
