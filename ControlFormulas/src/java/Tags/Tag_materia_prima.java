package Tags;

import Controladores.MateriaPrimaJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_materia_prima extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //FIN PERMISOS
            //JPA´S
            MateriaPrimaJpaController jpacmpm = new MateriaPrimaJpaController();
            //VARIABLE GLOBALES
            String filtro = "";
            int id_materia = 0;
            List lst_materias_primas = null;
            List lst_materia_prima = null;
            if (pageContext.getRequest().getAttribute("Materia_prima") != null) {
                if (pageContext.getRequest().getAttribute("Materia_prima").toString().equals("Registro")) {
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    id_materia = Integer.parseInt(pageContext.getRequest().getAttribute("Id_materia_prima").toString());
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Materia Prima</h3>");
                    if (rol.equals("Coordinador_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Materia_prima?opc=2' method='post'>");
                        out.print("<b>Nombre de materia :</b>");
                        out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre de la MP' title='Nombre de MP' onkeypress='if (event.keyCode==13){ func_name(this.value);}' onkeyup='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);val1.add(Validate.MP);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<br /><br /><br />");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content'>");
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_materias_primas = jpacmpm.Materias_primas();
                    } else {
                        lst_materias_primas = jpacmpm.Materias_primas_filtro(filtro);
                        if (lst_materias_primas == null) {
                            lst_materias_primas = jpacmpm.Materias_primas();
                        }
                    }
                    if (lst_materias_primas == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de materias primas registrados</b>");
                        out.print("</center>");
                    } else {
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            out.print("<form action='Materia_prima?opc=1' method='post'><div style='float: right;; margin: 20px;'><input type='hidden' name='imp' value='0' /><input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/></div></form>");
                        } else {
                            out.print("<form action='Materia_prima?opc=1' method='post'><div style='float: right;; margin: 20px;'><input type='hidden' name='imp' value='0' /><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></div></form>");
                        }
                        out.print("<h3>Materias primas</h3>");
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table style='width:100%' class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>MP Principal</th>");
                        out.print("<th colspan='2'>MP Equivalentes</th>");
                        out.print("<th>Estado</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_materias_primas.size(); i++) {
                            Object[] obj_materias_primas = (Object[]) lst_materias_primas.get(i);
                            if (Integer.parseInt(obj_materias_primas[3].toString()) == 1) {
                                out.print("<tr>");
                                out.print("<td align='center'><b>" + obj_materias_primas[1] + "</b></td>");
                                if (obj_materias_primas[2].toString() == null ? "" == null : obj_materias_primas[2].toString().equals("")) {
                                    out.print("<td align='center'>N/A</td>");
                                } else {
                                    out.print("<td align='center'>" + obj_materias_primas[2] + "</td>");
                                }
                                if (!(rol.equals("Coordinador_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta"))) {
                                    if (id_materia != 0) {
                                        if ((Integer) obj_materias_primas[0] == id_materia) {
                                            out.print("<td align='center'><a href='Materia_prima?opc=1&fto=&imp=0'><img src='Interfaz/Contenido/Iconos/Min.png' width='25px' height='25px' alt='edit' title='Asigan MP Equivalente' /></a></td>");
                                        } else {
                                            out.print("<td align='center'><a href='Materia_prima?opc=1&fto=&imp=" + obj_materias_primas[0] + "'><img src='Interfaz/Contenido/Iconos/Plus.png' width='25px' height='25px' alt='edit' title='Asigan MP Equivalente' /></a></td>");
                                        }
                                    } else {
                                        out.print("<td align='center'><a href='Materia_prima?opc=1&fto=&imp=" + obj_materias_primas[0] + "'><img src='Interfaz/Contenido/Iconos/Plus.png' width='25px' height='25px' alt='edit' title='Asigan MP Equivalente' /></a></td>");
                                    }
                                } else {
                                    out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Warning.png' width='26px' height='26px' alt='edit' title='Sin permisos MP Equivalentes' /></a></td>");
                                }
                                if (!(rol.equals("Coordinador_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta"))) {
                                    out.print("<td align='center'><a href='#'  onclick='DesactivarMateria(" + obj_materias_primas[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='30px' height='30px' alt='edit' title='Desactivar Materia' /></a></td>");
                                } else {
                                    out.print("<td align='center'><img src='Interfaz/Contenido/Iconos/Check.png' width='30px' height='30px' alt='edit' title='Sin permisos Desactivar Materia' /></td>");
                                }
                                out.print("</tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                                out.print("<td align='center'>" + obj_materias_primas[1] + "</td>");
                                if (obj_materias_primas[2].toString() == null ? "" == null : obj_materias_primas[2].toString().equals("")) {
                                    out.print("<td align='center'>N/A</td>");
                                } else {
                                    out.print("<td align='center'>" + obj_materias_primas[2] + "</td>");
                                }
                                out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Warning.png' width='26px' height='26px' alt='edit' title='Sin permisos MP Equivalentes' /></a></td>");
                                out.print("<td align='center'><a href='#' onclick='ActivarMateria(" + obj_materias_primas[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Activar Materia' /></a></td>");
                                out.print("</tr>");
                            }
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                        if (id_materia != 0) {
                            lst_materia_prima = jpacmpm.Traer_materia_prima_id(id_materia);
                            Object[] obj_materia_prima = (Object[]) lst_materia_prima.get(0);
                            String vector_equivalentes[] = obj_materia_prima[2].toString().split("-");
                            out.print("<div style='float:left'>");
                            out.print("<fieldset class='resalta_field' id='Registro_toma' style='width:400px;visibility: visible;position: absolute;top: 260px;left: 65%;'>");
                            out.print("<legend>MP Principal " + obj_materia_prima[1] + " </legend>");
                            out.print("<div style='float:right'><a href='Materia_prima?opc=1&fto=&imp=0' ><img src='Interfaz/Contenido/Iconos/Delete.png' width='30px' height='30px' alt='edit' title='Cancelar registro MP Equivalentes' /></a></div>");
                            out.print("<form action='Materia_prima?opc=4' method='post'>");
                            out.print("<br />Digite la MP Principal para la equivalencia de <b>" + obj_materia_prima[1] + "</b><br /><br />");
                            lst_materias_primas = jpacmpm.Materias_primas();
                            if (lst_materias_primas == null) {
                                out.print("<center>");
                                out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                                out.print("<b>No hay datos de materias primas registrados</b>");
                                out.print("</center>");
                            } else {
                                out.print("<center>");
                                out.print("<select name='Cbx_mp_equivalente' id='Cbx_mp_equivalente' title='MP Equivalente'>");
                                out.print("<option value='0' >Seleccionar MP Equivalente</option>");
                                for (int i = 0; i < lst_materias_primas.size(); i++) {
                                    Object[] obj_mp_equivalentes = (Object[]) lst_materias_primas.get(i);
                                    if (id_materia != (Integer) obj_mp_equivalentes[0]) {
                                        int contador = 0;
                                        for (int j = 0; j < vector_equivalentes.length; j++) {
                                            if (obj_mp_equivalentes[1].toString().equals(vector_equivalentes[j].toString())) {
                                                contador++;
                                            }
                                            if (j == (vector_equivalentes.length - 1)) {
                                                if (contador > 0) {
                                                } else {
                                                    out.print("<option value='" + obj_mp_equivalentes[1] + "'>" + obj_mp_equivalentes[1] + "</option>");
                                                }
                                            }
                                        }
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_mp_equivalente');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("&nbsp&nbsp&nbsp&nbsp");
                            }
                            out.print("<input type='hidden' name='Id_materia' id='Id_materia' value='" + id_materia + "' />");
                            out.print("<input  style='width:80px;' type='submit' value='Registrar'  />");
                            out.print("</center>");
                            out.print("</form>");
                            out.print("<br />");
                            if (obj_materia_prima[2].toString() == null ? "" == null : obj_materia_prima[2].toString().equals("")) {
                                out.print("<b>No hay datos de materias primas equivalentes en la maestra.</b>");
                            } else {
                                out.print("<table class='table' style='width: 100%;'>");
                                out.print("<tr>");
                                out.print("<th>MP Equivalente</th>");
                                out.print("<th>Quitar</th>");
                                out.print("</tr>");
                                for (int i = 0; i < vector_equivalentes.length; i++) {
                                    out.print("<tr>");
                                    out.print("<td>" + vector_equivalentes[i] + "</td>");
                                    out.print("<td align='center'>"
                                            + "<form action='Materia_prima?opc=5' method='post' name='FormQuitarMP" + i + "' id='FormQuitarMP'>"
                                            + "<input type='hidden' name='Txt_mp_equivalente' value='" + vector_equivalentes[i] + "' />"
                                            + "<input type='hidden' name='Id_materia' value='" + id_materia + "' />"
                                            + "<a href='JAVASCRIPT:FormQuitarMP" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Quitar MP Equivalente' /></a>"
                                            + "</form>"
                                            + "</td>");
                                    out.print("</tr>");
                                }
                                out.print("</table>");
                            }
                            out.print("</fieldset>");
                            out.print("</div>");
                        }
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_materia_prima.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
