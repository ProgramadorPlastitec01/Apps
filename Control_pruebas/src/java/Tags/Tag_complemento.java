package Tags;

import Controladores.ProcesoJpaController;
import Controladores.PruebaJpaController;
import Controladores.TipoLineaJpaController;
import Controladores.VariableJpaController;
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
            TipoLineaJpaController jpactln = new TipoLineaJpaController();
            PruebaJpaController jpacpeb = new PruebaJpaController();
            ProcesoJpaController jpacpcs = new ProcesoJpaController();
            VariableJpaController jpacvra = new VariableJpaController();
            //VARIABLE GLOBALES
            List lst_tipo_lineas = null;
            List lst_procesos = null;
            List lst_variables = null;
            List lst_pruebas = null;
            String filtro = "";
            if (pageContext.getRequest().getAttribute("Complemento") != null) {
                // <editor-fold defaultstate="collapsed" desc="LINEAS">
                if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Registro_linea")) {
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Línea</h3>");
                    if (rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Coordinadora-Calidad")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Complemento?opc=2' method='post'>");
                        out.print("<b>Línea :</b>");
                        out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre de línea' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                        lst_tipo_lineas = jpactln.Tipo_lineas();
                        out.print("<b>Tipo de línea :</b>");
                        out.print("<select name='Cbx_tipo_linea' id='Cbx_tipo_linea' title='Tipo de línea'>");
                        out.print("<option value='0' >Seleccionar Tipo de línea</option>");
                        for (int i = 0; i < lst_tipo_lineas.size(); i++) {
                            Object[] obj_tipo_lineas = (Object[]) lst_tipo_lineas.get(i);
                            out.print("<option value='" + obj_tipo_lineas[0] + "'>" + obj_tipo_lineas[1] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_linea');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<b>Código de línea :</b>");
                        out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Código de línea' title='Código de línea' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    List lst_lineas = (List) pageContext.getRequest().getAttribute("Lista_lineas");
                    out.print("<div id='content'>");
                    if (lst_lineas == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'  alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de líneas registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Líneas<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar actividad' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>Línea</th>");
                        out.print("<th>Tipo de línea</th>");
                        out.print("<th>Código</th>");
                        if (!(rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Coordinadora-Calidad"))) {
                            out.print("<th>Opciones</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_lineas.size(); i++) {
                            Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                            if (Integer.parseInt(obj_lineas[4].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                            }
                            out.print("<td>" + obj_lineas[1] + "</td>");
                            out.print("<td align='center'>" + obj_lineas[3] + "</td>");
                            out.print("<td align='center'>" + obj_lineas[5] + "</td>");
                            if (!(rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Coordinadora-Calidad"))) {
                                out.print("<td align='center' style='width:10%'>");
                                if (Integer.parseInt(obj_lineas[4].toString()) == 1) {
                                    out.print("<span onclick='DesactivarLinea(" + obj_lineas[0] + ")' class='fa fa-check-circle fa-size_small'></span>");
                                } else {
                                    out.print("<span onclick='ActivarLinea(" + obj_lineas[0] + ")' class='fa fa-times-circle fa-size_small'></span>");
                                }
                                out.print("</td>");
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
                    // </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="PRUEBAS">
                } else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Registro_pruebas")) {
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Prueba</h3>");
                    if (rol.equals("Consulta")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'  alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Complemento?opc=5' method='post' id='Pruebas'>");
                        out.print("<b>Prueba :</b>");
                        out.print("<input type='text' name='Txt_prueba' id='Txt_prueba' placeholder='Nombre prueba' title='Nombre prueba' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_prueba');val1.add(Validate.Presence);</script>");
                        out.print("<b>Frecuencia :</b>");
                        out.print("<select name='Cbx_frecuencia' id='Cbx_frecuencia' title='Frecuencia de prueba'>");
                        out.print("<option value='0' >Seleccionar frecuencia de toma</option>");
                        out.print("<option value='2' >(2)Cada dos</option>");
                        out.print("<option value='3' >(3)Cada tres</option>");
                        out.print("<option value='4' >(4)Cada cuatro</option>");
                        out.print("<option value='8' >(8)Cada ocho</option>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_frecuencia');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        lst_procesos = jpacpcs.Tipo_procesos();
                        out.print("<b>Proceso :</b>");
                        out.print("<select name='Cbx_proceso' id='Cbx_proceso' title='Tipo proceso'>");
                        out.print("<option value='0' >Seleccionar Tipo de línea</option>");
                        for (int i = 0; i < lst_procesos.size(); i++) {
                            Object[] obj_tipo_procesos = (Object[]) lst_procesos.get(i);
                            out.print("<option value='" + obj_tipo_procesos[0] + "'>" + obj_tipo_procesos[1] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_proceso');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        lst_tipo_lineas = jpactln.Tipo_lineas();
                        out.print("<b>Tipo de línea :</b>");
                        out.print("<table class='table' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>Tipo de línea</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_tipo_lineas.size(); i++) {
                            Object[] obj_tipo_lineas = (Object[]) lst_tipo_lineas.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><input type='checkbox' name='Ckb_tipo_linea[" + i + "]' value='" + obj_tipo_lineas[1] + "' checked></td>");
                            out.print("<td>" + obj_tipo_lineas[1] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<input type='hidden' name='Cantidad_tipos_linea' value='" + lst_tipo_lineas.size() + "' />");
                        out.print("<br /><input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content'>");
                    lst_pruebas = jpacpeb.Pruebas();
                    if (lst_pruebas == null) {
                        out.print("<h3>Pruebas</h3>");
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'  alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay parámetros pruebas de calidad</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Pruebas<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar actividad' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%' align='left'>");
                        out.print("<tr>");
                        out.print("<th>Parámetro</th>");
                        out.print("<th>Frecuencia (h)</th>");
                        out.print("<th>Proceso</th>");
                        out.print("<th>Tipos de línea</th>");
                        if (!(rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Coordinadora-Calidad"))) {
                            out.print("<th>Opciones</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_pruebas.size(); i++) {
                            Object[] obj_prueba = (Object[]) lst_pruebas.get(i);
                            if (Integer.parseInt(obj_prueba[6].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                            }
                            out.print("<td>" + obj_prueba[1] + "</td>");
                            out.print("<td align='center'>" + obj_prueba[2] + "</td>");
                            out.print("<td align='center'>" + obj_prueba[4] + "</td>");
                            out.print("<td>" + obj_prueba[5] + "</td>");
                            if (!(rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Coordinadora-Calidad"))) {
                                out.print("<td align='center' style='width:10%'>");
                                if (Integer.parseInt(obj_prueba[6].toString()) == 1) {
                                    out.print("<span onclick='DesactivarPrueba(" + obj_prueba[0] + ")' class='fa fa-check-circle fa-size_small'></span>");
                                } else {
                                    out.print("<span onclick='ActivarPrueba(" + obj_prueba[0] + ")' class='fa fa-times-circle fa-size_small'></span>");
                                }
                                out.print("</td>");
                            }
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<div class='cleaner'></div>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 15);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                    // </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="PROCESOS">
                } else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Registro_proceso")) {
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Proceso</h3>");
                    if (rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Coordinadora-Calidad")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Complemento?opc=8' method='post'>");
                        out.print("<b>Proceso :</b>");
                        out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre del proceso' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                        lst_variables = jpacvra.Variables();
                        out.print("<b>Variables :</b>");
                        out.print("<table class='table' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>Variables</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_variables.size(); i++) {
                            Object[] obj_variables = (Object[]) lst_variables.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><input type='radio' name='Rdb_variable' id='Rdb_variable' value='" + obj_variables[1] + "' /></td>");
                            out.print("<td>" + obj_variables[1] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<td align='center'><span class='fa fa-plus fa-size_small' onclick='Nueva_variable()'></span></td>");
                        out.print("<td><input type='text' placeholder='Nueva variable' id='Txt_new_variable' style='width:80%' /></td>");
                        out.print("</tr>");
                        out.print("</table>");
//                        out.print("<input type='hidden' name='Cantidad_variables' value='" + lst_variables.size() + "' />");
                        out.print("<br /><input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content'>");
                    lst_procesos = jpacpcs.Tipo_procesos();
                    if (lst_procesos == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px'  alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de procesos registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Procesos<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>Proceso</th>");
                        out.print("<th>Variables</th>");
                        if (!(rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Coordinadora-Calidad"))) {
                            out.print("<th>Estado</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_procesos.size(); i++) {
                            Object[] obj_procesos = (Object[]) lst_procesos.get(i);
                            if (Integer.parseInt(obj_procesos[3].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                            }
                            out.print("<td>" + obj_procesos[1] + "</td>");
                            out.print("<td>" + obj_procesos[2] + "</td>");
                            if (!(rol.equals("Encargada-operaria") || rol.equals("Inspectora-Calidad") || rol.equals("Consulta") || rol.equals("Coordinadora-Calidad"))) {
                                out.print("<td align='center' style='width:10%'>");
                                if (Integer.parseInt(obj_procesos[3].toString()) == 1) {
                                    out.print("<span onclick='DesactivarProceso(" + obj_procesos[0] + ")' class='fa fa-check-circle fa-size_small'></span>");
                                } else {
                                    out.print("<span onclick='ActivarProceso(" + obj_procesos[0] + ")' class='fa fa-times-circle fa-size_small'></span>");
                                }
                                out.print("</td>");
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
                }
                // </editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_complemento.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
