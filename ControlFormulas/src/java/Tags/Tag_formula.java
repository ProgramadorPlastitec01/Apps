package Tags;

import Controladores.FormulaJpaController;
import Controladores.FormulaMateriaPrimaJpaController;
import Controladores.MateriaPrimaJpaController;
import Controladores.RegistroDetalleJpaController;
import Controladores.RegistroJpaController;
import Metodos.Connection_metrologia;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_formula extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="0. VARIABLES">
            //Cumple/No cumple
            String clase = "";
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //FIN PERMISOS
            //JPA´S
            FormulaJpaController jpacfml = new FormulaJpaController();
            MateriaPrimaJpaController jpacmpm = new MateriaPrimaJpaController();
            FormulaMateriaPrimaJpaController jpacfmp = new FormulaMateriaPrimaJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            RegistroDetalleJpaController jpacrdt = new RegistroDetalleJpaController();
            Connection_metrologia mtdmtl = new Connection_metrologia();
            //FECHA
            Calendar cal = Calendar.getInstance();
            String ano = cal.get(Calendar.YEAR) + "";
            String mes = "";
            if ((cal.get(Calendar.MONTH) + 1) < 10) {
                mes = "0" + (cal.get(Calendar.MONTH) + 1);
            } else {
                mes = (cal.get(Calendar.MONTH) + 1) + "";
            }
            String dia = "";
            if ((cal.get(Calendar.DAY_OF_MONTH)) < 10) {
                dia = "0" + cal.get(Calendar.DAY_OF_MONTH);
            } else {
                dia = cal.get(Calendar.DAY_OF_MONTH) + "";
            }
            //double version = Double.parseDouble(ano + "." + mes + dia);
            String ano_lote = (cal.get(Calendar.YEAR) - 1990) + "";
            String mes_lote = "";
            String[] meses = {"X", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
            int mesz = Integer.parseInt(mes.toString());
            mes_lote = meses[mesz];
//            if (mes.equals("01")) {
//                mes_lote = "A";
//            } else if (mes.equals("02")) {
//                mes_lote = "B";
//            } else if (mes.equals("03")) {
//                mes_lote = "C";
//            } else if (mes.equals("04")) {
//                mes_lote = "D";
//            } else if (mes.equals("05")) {
//                mes_lote = "E";
//            } else if (mes.equals("06")) {
//                mes_lote = "F";
//            } else if (mes.equals("07")) {
//                mes_lote = "G";
//            } else if (mes.equals("08")) {
//                mes_lote = "H";
//            } else if (mes.equals("09")) {
//                mes_lote = "I";
//            } else if (mes.equals("10")) {
//                mes_lote = "J";
//            } else if (mes.equals("11")) {
//                mes_lote = "K";
//            } else if (mes.equals("12")) {
//                mes_lote = "L";
//            }
            //VARIABLE GLOBALES
            String filtro = "";
            String lote = "";
            String mp_maestra = "";
            int id_formula = 0;
            int id_registro = 0;
            int id_dureza = 0;
            int contador = 0;
            int contador_maestra = 0;
            int contador_equivalente = 0;
            List lst_formula = null;
            List lst_formulas = null;
            List lst_formula_id = null;
            List lst_lotes = null;
            List lst_registro = null;
            List lst_registro_detalle = null;
            List lst_materia_prima = null;
            List lst_formula_materia_prima = null;
            List lst_historial_formula = null;
            List lst_mp_maestras = null;
            List lst_mp_equivalentes = null;
            List lst_dureza_id = null;
            List lst_durezas = null;
            List lst_instrumentos = null;
//</editor-fold>
            if (pageContext.getRequest().getAttribute("Formula") != null) {
                //<editor-fold defaultstate="collapsed" desc="1. REGISTRAR FORMULAS">
                if (pageContext.getRequest().getAttribute("Formula").toString().equals("Registro")) {
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    id_formula = Integer.parseInt(pageContext.getRequest().getAttribute("Id_formula").toString());
                    if (id_formula > 0) {
                        lst_formula_id = jpacfml.Traer_formula_id(id_formula);
                        Object[] obj_formula_id = (Object[]) lst_formula_id.get(0);
                        out.print("<div id='sidebar'>");
                        out.print("<div align='right'><a href='Formula?opc=1&Id_formula=0&fto='><img src='Interfaz/Contenido/Iconos/Delete.png' width=\"26px\" height=\"26px\" alt=\"edit\" title='Cancelar Modificación'></a></div>");
                        out.print("<h3>Registrar Dureza</h3>");
                        if (rol.equals("Coordinador_PI") || rol.equals("Jefe_PI") || rol.equals("Consulta")) {
                            out.print("<center>");
                            out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='Sin permisos' /><br />");
                            out.print("<b>Sin permisos de registro</b>");
                            out.print("</center>");
                        } else {
                            out.print("<form action='Formula?opc=2' method='post'>");
                            out.print("<input type='hidden' name='Id_formula' id='Id_formula' value='" + id_formula + "'>");
                            out.print("<input type='hidden' name='radio' id='radio' value='1'>");
                            out.print("<b>Formula :</b>");
                            out.print("<input type='text' name='Txt_nombre' id='Txt_nombre'  placeholder='Formula' value='" + obj_formula_id[1] + "' onchange='javascriptpt:this.value=this.value.toUpperCase();' readonly />");
                            filtro = obj_formula_id[1].toString();
                            out.print("<b>Ficha Técnica :</b>");
                            out.print("<input type='text' name='c_ficha_tecnica' id='c_ficha_tecnica' onkeyup='datos()' placeholder='Ficha Técnica' title='Ficha Técnica'  value='" + ((!obj_formula_id[3].equals("N/A")) ? obj_formula_id[3].toString().split("\\]\\[")[0].replace("[", "") : "") + "' onchange='javascriptpt:this.value=this.value.toUpperCase();' required/>");
                            out.print("<b>Versión :</b>");
                            out.print("<input type='text' name='c_version' id='c_version' onkeyup='datos()' placeholder='Versión' title='Versión'  value='" + ((!obj_formula_id[3].equals("N/A")) ? obj_formula_id[3].toString().split("\\]\\[")[1] : "0") + "' onchange='javascriptpt:this.value=this.value.toUpperCase();' required/>");
                            out.print("<b>Observaciones :</b>");
                            out.print("<textarea rows='6' name='c_observaciones' id='c_observaciones' onkeyup='datos()' placeholder='Observaciones' onchange='javascriptpt:this.value=this.value.toUpperCase();' required>" + ((!obj_formula_id[3].equals("N/A")) ? obj_formula_id[3].toString().split("\\]\\[")[2].replace("]", "") : obj_formula_id[3]) + "</textarea>");
//                            out.print("<textarea rows='6' name='c_observaciones' id='c_observaciones' onkeyup='datos()' placeholder='Observaciones' onchange='javascriptpt:this.value=this.value.toUpperCase();' required>" +  obj_formula_id[3]+ "</textarea>");
                            out.print("<b>Dureza :</b>");
                            out.print("<input type='text' name='c_dureza' id='c_dureza'  placeholder='Dureza' title='Dureza Aceptable'  value='" + ((obj_formula_id[4] != null) ? obj_formula_id[4] : " ") + "' onchange='javascriptpt:this.value=this.value.toUpperCase();' required/>");
                            out.print("<b>Dureza Max :</b>");
                            out.print("<input type='text' name='c_dureza_max' id='c_dureza_max'  placeholder='Dureza Max' title='Dureza Max'  value='" + ((obj_formula_id[5] != null) ? obj_formula_id[5] : " ") + "' onchange='javascriptpt:this.value=this.value.toUpperCase();' required/>");
                            out.print("<b>Dureza Min :</b>");
                            out.print("<input type='text' name='c_dureza_min' id='c_dureza_min'  placeholder='Dureza Min' title='Dureza Min'  value='" + ((obj_formula_id[6] != null) ? obj_formula_id[6] : " ") + "' onchange='javascriptpt:this.value=this.value.toUpperCase();' required/>");
                            out.print("<input type='submit' value='Actualizar durezas' title='Actualizar durezas'>");
                            out.print("</form>");
                        }
                        out.print("</div> <!-- END of sidebar -->");
                    } else {
                        out.print("<div id='sidebar'>");
                        out.print("<h3>Registrar Formula</h3>");
                        if (rol.equals("Coordinador_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta")) {
                            out.print("<center>");
                            out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='Sin permisos' /><br />");
                            out.print("<b>Sin permisos de registro</b>");
                            out.print("</center>");
                        } else {
                            out.print("<form action='Formula?opc=2' onsubmit='registroA();' method='post'>");
                            out.print("<b>Formula :</b>");
                            out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre de línea' onchange='javascript:this.value=this.value.toUpperCase();' required/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                            out.print("<div id='form_registro' style='display:none'>");
                            out.print("<b>Aplica Dureza :</b><br>");
                            out.print("<input type='hidden' name='radio' id='radio' value='0' />");
                            out.print("</div>");
                            out.print("<input type='submit' id='btsubmit' value='Registrar' />");
                            out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                    + "          <div></div>\n"
                                    + "          <div></div>\n"
                                    + "          <div></div>\n"
                                    + "        </div>");
                            out.print("</form>");
                        }
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
                    }
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="2. CONSULTAR FORMULAS">
                    out.print("<div id='content'>");
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_formulas = jpacfml.Formulas();
                    } else {
                        lst_formulas = jpacfml.Formulas_filtro_incidente(filtro);
                        if (lst_formulas == null) {
                            lst_formulas = jpacfml.Formulas();
                        }
                    }
                    if (lst_formulas == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de formulas registrados</b>");
                        out.print("</center>");
                    } else {
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            out.print("<form action='Formula?opc=1' method='post'><div style='float: right; margin: 20px;'><input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/></div></form>");
                        } else {
                            out.print("<form action='Formula?opc=1' method='post'><div style='float: right; margin: 20px;'><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></div></form>");
                        }
                        out.print("<h3>Formulas</h3>");
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' style='width:100%' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Formula</th>");
                        out.print("<th>Observaciones</th>");
                        out.print("<th>Registro</th>");
                        out.print("<th>Historial</th>");
                        out.print("<th>Materias Primas</th>");
                        out.print("<th>Estado</th>");
                        out.print("<th>Durezas</th>");
                        out.print("<th>Modificar</th>");
                        // out.print("<th>Estado</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_formulas.size(); i++) {
                            Object[] obj_formulas = (Object[]) lst_formulas.get(i);
                            out.print("<tr " + ((Integer.parseInt(obj_formulas[2].toString()) == 1) ? "" : "class='rojo'") + ">");
                            out.print("<td " + ((id_formula == (Integer) obj_formulas[0]) ? "style='background-color:#F6921E;35px;'" : "style='width:35px'") + " ><b>" + obj_formulas[1] + "</b></td>");
                            out.print("<td width='30%'>" + ((!obj_formulas[4].equals("N/A")) ? obj_formulas[4].toString().split("\\]\\[")[2].replace("]", "") : obj_formulas[4]) + "</td>");
                            if (Integer.parseInt(obj_formulas[2].toString()) == 1) {
                                out.print("<td align='center'><a href='Formula?opc=8&Id_formula=" + obj_formulas[0] + "'><img src='Interfaz/Contenido/Iconos/Document.png' width='30px' height='34px' alt='edit' title='Generar R-PI-004' /></a></td>");
                                out.print("<td align='center'><a href='Formula?opc=6&fto=&Id_formula=" + obj_formulas[0] + "'><img src='Interfaz/Contenido/Iconos/History.png' width='30px' height='34px' alt='edit' title='Historial de Formula' /></a></td>");
                                out.print("<td align='center'>"
                                        + "<form action='Formula?opc=4' method='post' name='FormVer" + i + "' id='FormVer'>"
                                        + "<input type='hidden' name='Id_formula' value='" + obj_formulas[0] + "' />"
                                        + "<input type='hidden' name='fto' value='' />"
                                        + "<a href='JAVASCRIPT:FormVer" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Ver.png' width='30px' height='30px' alt='edit' title='Materia Prima' /></a>"
                                        + "</form>"
                                        + "</td>");
                                if (!(rol.equals("Coordinador_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta"))) {
                                    out.print("<td align='center'><a href='#' onclick='DesactivarFormula(" + obj_formulas[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='30px' height='30px' alt='edit' title='Desactivar Formula' /></a></td>");
                                } else {
                                    out.print("<td align='center'><img src='Interfaz/Contenido/Iconos/Check.png' width='30px' height='30px' alt='edit' title='Sin permisos Desactivar Formula' /></td>");
                                }
                            } else {
                                out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Warning.png' width='26px' height='26px' alt='edit' title='Sin permisos Generación R-PI-004' /></a></td>");
                                out.print("<td align='center'><a href='Formula?opc=6&fto=&Id_formula=" + obj_formulas[0] + "'><img src='Interfaz/Contenido/Iconos/History.png' width='30px' height='34px' alt='edit' title='Historial de Formula' /></a></td>");
                                out.print("<td align='center'>"
                                        + "<form action='Formula?opc=4' method='post' name='FormVer" + i + "' id='FormVer'>"
                                        + "<input type='hidden' name='Id_formula' value='" + obj_formulas[0] + "' />"
                                        + "<input type='hidden' name='fto' value='' />"
                                        + "<a href='JAVASCRIPT:FormVer" + i + ".submit()'><img src='Interfaz/Contenido/Iconos/Ver.png' width='30px' height='30px' alt='edit' title='Materia Prima' /></a>"
                                        + "</form>"
                                        + "</td>");
                                if (!(rol.equals("Coordinador_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta"))) {
                                    out.print("<td align='center'><a href='#' onclick='ActivarFormula(" + obj_formulas[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Activar Formula' /></a></td>");
                                } else {
                                    out.print("<td align='center'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Sin permisos Activar Formula' /></td>");
                                }
                            }
                            if (Integer.parseInt(obj_formulas[3].toString()) == 1) {
                                out.print("<td  style='width:145px'><div style='width:30%; float:left'><a href='Formula?opc=19&Id_formula=" + obj_formulas[0] + "'><img src='Interfaz/Contenido/Iconos/Hardness.png' width='75px' height='75px' alt='edit'  title='Durezas'/></a></div><div style='width:70%; float:left'><b>" + obj_formulas[4].toString().split("\\]\\[")[0].replace("[", "") + " </b><br/> <b>" + obj_formulas[5] + "</b><b style='color:red'> + </b><b style='color:#006666'>" + obj_formulas[6] + " </b> <b style='color:red'> - </b> <b style='color:#006666'>" + obj_formulas[7] + "</b></div></td>");
                                out.print("<td align='center' width='15px'><a href='Formula?opc=1&fto=&Id_formula=" + obj_formulas[0] + "' ><img src='Interfaz/Contenido/Iconos/Edit.png' width='30px' height='34px' alt='edit' title='Modificar'/></a></td>");
                            } else if (rol.equals("Inspectora_calidad") || rol.equals("Administrador")) {
                                out.print("<td colspan='2' align='center'>");
                                out.print("<div><b style='color:red'>Aplica</b></div> ");
                                out.print("<div>"
                                        + "<input type='radio' name='radio" + obj_formulas[0] + "' id='radio' value='1' required " + ((id_formula == (Integer) obj_formulas[0]) ? "checked" : "") + " onclick='JAVASCRIPT:FormEdit" + i + ".submit()'>Si"
                                        + "<input type='radio' name='radio" + obj_formulas[0] + "' id='radio' value='0'  " + ((id_formula != (Integer) obj_formulas[0]) ? "checked" : "") + ">No</div>");
                                out.print("<div><form action='Formula?opc=1' method='post' name='FormEdit" + i + "' id='FormEdit'>");
                                out.print("<input type='hidden' name='Id_formula' value='" + obj_formulas[0] + "' />");
                                if (filtro == null ? "" == null : filtro.equals("")) {
                                    out.print("<input type='hidden' name='fto' value='' />");
                                } else {
                                    out.print("<input type='hidden' name='fto' value='" + filtro + "' />");
                                }
                                out.print("</form></div> ");
                                out.print("</div> ");
                                out.print("</td>");
                            } else {
                                out.print("<td colspan='2'><center><b class='naranja'>No aplica registro de Durezas</b><center></td>");
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
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of content -->");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="3. ASIGNAR MATERIA PRIMA">
                } else if (pageContext.getRequest().getAttribute("Formula").toString().equals("Asignar_materia_prima")) {
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    id_formula = Integer.parseInt(pageContext.getRequest().getAttribute("Id_formula").toString());
                    lst_formulas = jpacfml.Traer_formula_id(id_formula);
                    Object[] obj_formula = (Object[]) lst_formulas.get(0);
                    out.print("<div id='sidebar' style='width:30%;'>");
                    out.print("<h3>Asignar Materia Prima</h3>");
                    if (!(rol.equals("Coordinador_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta")) && Integer.parseInt(obj_formula[7].toString()) == 1) {
                        out.print("<form action='Formula?opc=5' onsubmit='registroB();' method='post'>");
                        out.print("<center>");
                        out.print("<input type='submit' id='btsubmit' value='Registrar' />");
                        out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "        </div>");
                    }
                    lst_materia_prima = jpacmpm.Materias_primas();
                    if (lst_materia_prima == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No se generaron datos de seriales.</b>");
                        out.print("</center>");
                    } else {
                        out.print("<input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar materia prima'/>");
                        out.print("</center>");
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table id='resultados' class='table' style='width:100%;'>");
                        out.print("<tr>");
                        out.print("<th></th>");
                        out.print("<th>MP Principales</th>");
                        out.print("<th>MP Equivalentes</th>");
                        out.print("</tr>");
                        lst_formula_materia_prima = jpacfmp.Traer_formula_materia_prima_id(id_formula);
                        if (lst_formula_materia_prima == null) {
                            for (int i = 0; i < lst_materia_prima.size(); i++) {
                                Object[] obj_materias_primas = (Object[]) lst_materia_prima.get(i);
                                if ((Integer) obj_materias_primas[3] == 1) {
                                    out.print("<tr>");
                                    out.print("<td><input type='checkbox' name='Ckb_materia_prima[" + i + "]' value='" + obj_materias_primas[0] + "' /></td>");
                                    out.print("<td>" + obj_materias_primas[1] + "</td>");
                                    if (obj_materias_primas[2].toString() == null ? "" == null : obj_materias_primas[2].toString().equals("")) {
                                        out.print("<td>N/A</td>");
                                    } else {
                                        out.print("<td>" + obj_materias_primas[2] + "</td>");
                                    }
                                    out.print("</tr>");
                                }
                            }
                        } else {
                            for (int i = 0; i < lst_materia_prima.size(); i++) {
                                Object[] obj_materias_primas = (Object[]) lst_materia_prima.get(i);
                                contador = 0;
                                if ((Integer) obj_materias_primas[3] == 1) {
                                    out.print("<tr>");
                                    for (int j = 0; j < lst_formula_materia_prima.size(); j++) {
                                        Object[] obj_formulas_materia_prima = (Object[]) lst_formula_materia_prima.get(j);
                                        if (obj_materias_primas[0] == obj_formulas_materia_prima[3]) {
                                            contador++;
                                        }
                                    }
                                    if (contador > 0) {
                                        out.print("<td><input type='checkbox' disabled='true' checked name='Ckb_materia_prima[" + i + "]' value='" + obj_materias_primas[0] + "' /></td>");
                                        out.print("<td>" + obj_materias_primas[1] + "</td>");
                                        if (obj_materias_primas[2].toString() == null ? "" == null : obj_materias_primas[2].toString().equals("")) {
                                            out.print("<td>N/A</td>");
                                        } else {
                                            out.print("<td>" + obj_materias_primas[2] + "</td>");
                                        }
                                    } else {
                                        out.print("<td><input type='checkbox' name='Ckb_materia_prima[" + i + "]' value='" + obj_materias_primas[0] + "' /></td>");
                                        out.print("<td>" + obj_materias_primas[1] + "</td>");
                                        if (obj_materias_primas[2].toString() == null ? "" == null : obj_materias_primas[2].toString().equals("")) {
                                            out.print("<td>N/A</td>");
                                        } else {
                                            out.print("<td>" + obj_materias_primas[2] + "</td>");
                                        }
                                    }
                                    out.print("</tr>");
                                }
                            }
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager3 = new Pager3('resultados', 10);");
                        out.print("pager3.init();");
                        out.print("pager3.showPageNav3('pager3','NavPosicion');");
                        out.print("pager3.showPage(1);");
                        out.print("</script>");
                    }
                    if (!(rol.equals("Coordinador_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta")) && Integer.parseInt(obj_formula[7].toString()) == 1) {
                        out.print("<input type='hidden' name='Cantidad_materia_prima' id='Cantidad_materia_prima' value='" + lst_materia_prima.size() + "' />");
                        out.print("<input type='hidden' name='Id_formula' id='Id_formula' value='" + id_formula + "' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="4. CONSULTAR MATERIA PRIMA DE LA FORMULA">
                    out.print("<div id='content' style='width:63.5%; float: left; padding: 0px 20px 0px;'>");
                    lst_formula_materia_prima = null;
                    lst_formula_materia_prima = jpacfmp.Traer_formula_materia_prima_id(id_formula);
                    if (lst_formula_materia_prima == null) {
                        out.print("<form action='Formula?opc=1&fto=" + obj_formula[1] + "' method='post' name='FormVolver' id='FormVer'>");
                        out.print("<div style='float: left;; margin: 10px;'>");
                        out.print("<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' width='30px' height='30px' alt='edit' title='Volver a Formulas' /></a>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("<h3>Sin datos de Materias primas asociadas a la formula</h3>");
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de formulas registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Formula?opc=1&fto=" + obj_formula[1] + "' method='post' name='FormVolver' id='FormVer'>");
                        out.print("<div style='float: left;; margin: 10px;'>");
                        out.print("<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' width='30px' height='30px' alt='edit' title='Volver a Formulas' /></a>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("<h3>Materia Prima de la Formula " + obj_formula[1] + "</h3>");
                        out.print("<div id='NavPosicion2'></div>");
                        out.print("<table class='table' style='width:100%;' id='resultados_2'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>MP Principales</th>");
                        out.print("<th>MP Equivalentes</th>");
                        out.print("<th>Estado</th>");
                        //out.print("<th>Estado</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_formula_materia_prima.size(); i++) {
                            Object[] obj_formulas_materia_prima = (Object[]) lst_formula_materia_prima.get(i);
                            if (Integer.parseInt(obj_formulas_materia_prima[6].toString()) == 1) {
                                out.print("<tr>");
                                if (rol.equals("Coordinador_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta")) {
                                    out.print("<td align='center'>" + obj_formulas_materia_prima[7] + "</td>");
                                } else {
                                    out.print("<td align='center'><form id='FormPos" + i + "' name='FormPos" + i + "' method='post' action='Formula?opc=15&Id_formula=" + obj_formulas_materia_prima[1] + "&Id_formula_materia=" + obj_formulas_materia_prima[0] + "'><input type='text' id='Txt_posicion' name='Txt_posicion' value='" + obj_formulas_materia_prima[7] + "' style='text-align:center;border-width:0;width:15px;font-size: 11px;color:#292929;' /></form></td>");
                                }
                                out.print("<td>" + obj_formulas_materia_prima[4] + "</td>");
                                if (obj_formulas_materia_prima[5].toString() == null ? "" == null : obj_formulas_materia_prima[5].toString().equals("")) {
                                    out.print("<td>N/A</td>");
                                } else {
                                    out.print("<td>" + obj_formulas_materia_prima[5] + "</td>");
                                }
                                if (!(rol.equals("Coordinador_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta"))) {
                                    out.print("<td align='center'><a href='#' onclick='DesactivarFormulaMP(" + obj_formulas_materia_prima[0] + "," + obj_formulas_materia_prima[1] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='30px' height='30px' alt='edit' title='Desactivar MP' /></a></td>");
                                } else {
                                    out.print("<td align='center'><img src='Interfaz/Contenido/Iconos/Check.png' width='30px' height='30px' alt='edit' title='Sin permisos para desactivar MP' /></td>");
                                }
                                out.print("</tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                                if (rol.equals("Coordinador_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta")) {
                                    out.print("<td align='center'>" + obj_formulas_materia_prima[7] + "</td>");
                                } else {
                                    out.print("<td align='center'><form id='FormPos" + i + "' name='FormPos" + i + "' method='post' action='Formula?opc=15&Id_formula=" + obj_formulas_materia_prima[1] + "&Id_formula_materia=" + obj_formulas_materia_prima[0] + "'><input type='text' id='Txt_posicion' name='Txt_posicion' value='" + obj_formulas_materia_prima[7] + "' style='text-align:center;border-width:0;width:15px;font-size: 11px;color:#292929;' /></form></td>");
                                }
                                out.print("<td>" + obj_formulas_materia_prima[4] + "</td>");
                                if (obj_formulas_materia_prima[5].toString() == null ? "" == null : obj_formulas_materia_prima[5].toString().equals("")) {
                                    out.print("<td>N/A</td>");
                                } else {
                                    out.print("<td>" + obj_formulas_materia_prima[5] + "</td>");
                                }
                                if (!(rol.equals("Coordinador_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta"))) {
                                    out.print("<td align='center'><a href='#' onclick='ActivarFormulaMP(" + obj_formulas_materia_prima[0] + "," + obj_formulas_materia_prima[1] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Activar Formula' /></a></td>");
                                } else {
                                    out.print("<td align='center'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Sin permisos para activar MP' /></td>");
                                }
                                out.print("</tr>");
                            }
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager2 = new Pager('resultados_2', 10);");
                        out.print("pager2.init();");
                        out.print("pager2.showPageNav('pager2','NavPosicion2');");
                        out.print("pager2.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of content -->");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="5. HISTORIAL DE LA FORMULA">
                else if (pageContext.getRequest().getAttribute("Formula").toString().equals("Historial_formulas")) {
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    id_formula = Integer.parseInt(pageContext.getRequest().getAttribute("Id_formula").toString());
                    lst_formulas = jpacfml.Traer_formula_id(id_formula);
                    Object[] obj_formula = (Object[]) lst_formulas.get(0);
                    out.print("<div id='content_sin'>");
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        //lst_historial_formula = jpacrgt.Traer_registro_formula_id(id_formula);
                        lst_lotes = jpacrgt.Traer_lotes_formula(id_formula, 1);
                    } else {
                        //lst_historial_formula = jpacrgt.Traer_registro_formula_id_filtro(id_formula, filtro);
                        lst_lotes = jpacrgt.Traer_lotes_formula_filtro(id_formula, filtro, 1);
                        if (lst_lotes == null) {
                            lst_lotes = jpacrgt.Traer_lotes_formula(id_formula, 1);
                            //lst_historial_formula = jpacrgt.Traer_registro_formula_id(id_formula);
                        }
                    }
                    if (lst_lotes == null) {
                        out.print("<form action='Formula?opc=1&fto=" + obj_formula[1] + "' method='post' name='FormVolver' id='FormVer'>");
                        out.print("<div style='float: left;; margin: 10px;'>");
                        out.print("<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' width='30px' height='30px' alt='edit' title='Volver a Formulas' /></a>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("<h3>Sin datos de historial para la formula</h3>");
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de historial formula registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Formula?opc=1&fto=" + obj_formula[1] + "' method='post' name='FormVolver' id='FormVer'>");
                        out.print("<div style='float: left;; margin: 10px;'>");
                        out.print("<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' width='30px' height='30px' alt='edit' title='Volver a Formulas' /></a>");
                        out.print("</div>");
                        out.print("</form>");
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            out.print("<form action='Formula?opc=6' method='post'><div style='float: right;; margin: 20px;'><input type='hidden' name='Id_formula' value='" + id_formula + "'/><input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/></div></form>");
                        } else {
                            out.print("<form action='Formula?opc=6' method='post'><div style='float: right;; margin: 20px;'><input type='hidden' name='Id_formula' value='" + id_formula + "'/><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></div></form>");
                        }
                        out.print("<h3>Historial Formula " + obj_formula[1] + "</h3>");
                        //lst_lotes = jpacrgt.Traer_lotes_formula(id_formula, 1);
//                        out.print("<ul id='browser' class='treeview-famfamfam' style='width:100%'>");
//                        out.print("<center>");
                        for (int i = 0; i < lst_lotes.size(); i++) {
                            Object[] obj_lotes_formula = (Object[]) lst_lotes.get(i);
//                            out.print("<li class='closed'><span class='folder'>" + obj_lotes_formula[1] + "</span><ul>");
                            out.print("<button class=\"accordion\"><center>" + obj_lotes_formula[1] + "</center></button>");
//                            out.print("<div style='background-color:#fff;width:100%;border:2px solid #006666'>");
                            out.print("<div class=\"panel\">");
                            //out.print("<div id='NavPosicion2'></div>");
                            lst_historial_formula = jpacrgt.Traer_registro_formula_id_filtro(id_formula, obj_lotes_formula[1].toString());
                            out.print("<table class='table' style='width:100%' id='resultados_2'>");
                            out.print("<tr>");
                            out.print("<th>Lote</th>");
                            out.print("<th>Clasificación</th>");
                            out.print("<th>Fecha</th>");
                            out.print("<th>Compuesto</th>");
                            out.print("<th>Producción Insumos</th>");
                            out.print("<th>Calidad</th>");
                            out.print("<th>Observaciones</th>");
                            out.print("<th>Ver</th>");
                            out.print("</tr>");
                            for (int j = 0; j < lst_historial_formula.size(); j++) {
                                Object[] obj_registros = (Object[]) lst_historial_formula.get(j);
                                if ((Integer) obj_registros[8] == 1) {
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>" + obj_registros[4] + "</b></td>");
                                    if (obj_registros[12] == null) {
                                        out.print("<td align='center'>NINGUNA</td>");
                                    } else {
                                        out.print("<td align='center'>" + obj_registros[12] + "</td>");
                                    }
                                    out.print("<td align='center'>" + obj_registros[3] + "</td>");
                                    out.print("<td align='center'>" + obj_registros[5] + "</td>");
                                    out.print("<td>" + obj_registros[6] + "</td>");
                                    out.print("<td>" + obj_registros[7] + "</td>");
                                    if (obj_registros[11] == null) {
                                        out.print("<td><b class='negro'>NINGUNA</b></td>");
                                    } else {
                                        out.print("<td>" + obj_registros[11] + "</td>");
                                    }
                                    out.print("<td align='center'><a href='Formula?opc=7&Id_registro=" + obj_registros[0] + "' target='_blank'><img src='Interfaz/Contenido/Iconos/Ver.png' width='30px' height='30px' alt='edit' title='R-PI-004' /></a></td>");
                                    out.print("</tr>");
                                }
                            }
                            out.print("</table>");
                            out.print("</div>");
                        }
//                        out.print("</center>");
//                        out.print("</ul>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of content -->");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="6. CONSULTA DE R-PI-004">
                else if (pageContext.getRequest().getAttribute("Formula").toString().equals("Generacion_registro")) {
                    id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                    lst_registro = jpacrgt.Traer_registro_formula_id_registro(id_registro);
                    Object[] obj_registro = (Object[]) lst_registro.get(0);
                    lst_registro_detalle = jpacrdt.Traer_registro_detalle_id_registro(id_registro);
                    String[] fecha_decimal = obj_registro[3].toString().split("-");
                    double version = Double.parseDouble(fecha_decimal[0] + "." + fecha_decimal[1] + fecha_decimal[2]);
                    out.print("<table class='table' style='width:100%'>");
                    if (version >= 2016.0101) {
                        out.print("<tr>");
                        out.print("<td colspan='6' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                    }
                    out.print("<tr>");
                    out.print("<td align='center' colspan='2' rowspan='2'>"
                            + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:202.5px;height:67.5px' />"
                            + "</td>");
                    if (version >= 2015.0422) {
                        out.print("<td align='center' colspan='2'>REGISTRO</td>");
                        out.print("<td align='center' colspan='2'>CODIGO <b> R-PI-004 </b></td>");
                    } else {
                        out.print("<td align='center' colspan='2'>MANUAL DE REGISTROS</td>");
                        out.print("<td align='center' colspan='2'>CODIGO <b> R-PI-004 </b></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    if (version >= 2015.0422) {
                        out.print("<td align='center' colspan='2'>CONTROL LOTES DE MATERIAS<br />PRIMAS EN FORMULAS</td>");
                        out.print("<td align='center' colspan='2'>VERSIÓN: <b>1</td>");
                    } else {
                        out.print("<td align='center' colspan='2'>CONTROL LOTES DE MATERIAS<br />PRIMAS EN FORMULAS</td>");
                        out.print("<td align='center' colspan='2'>VERSION <b>0</b></td>");
                    }
                    out.print("</tr>");
//                    if (version >= 2016.0101) {
//                        out.print("<tr>");
//                        out.print("<td colspan='6' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
//                        out.print("</tr>");
//                    }
//                    out.print("<tr>");
//                    out.print("<td align='center' colspan='2'>"
//                            + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:202.5px;height:67.5px' />"
//                            + "</td>");
//                    out.print("<td align='center' colspan='2'><h3 class='negro'>MANUAL DE REGISTROS<br />CONTROL LOTES DE MATERIAS<br />PRIMAS EN FORMULAS</h3></td>");
//                    if (version >= 2015.0422) {
//                        out.print("<td align='center' colspan='2'><h3 class='negro'>CODIGO <b> R-PI-004 </b> VERSION <b>1</h3></td>");
//                    } else {
//                        out.print("<td align='center' colspan='2'><h3 class='negro'>CODIGO <b> R-PI-004 </b> VERSION <b>0</b></h3></td>");
//                    }
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th colspan='2'>FECHA</th>");
                    out.print("<th colspan='2'>RESPONSABLE POR PRODUCCIÓN</th>");
                    out.print("<th colspan='2'>RESPONSABLE POR CALIDAD</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='2'>" + obj_registro[3] + "</td>");
                    out.print("<td align='center' colspan='2'>" + obj_registro[6] + "</td>");
                    if (obj_registro[7].equals("PENDIENTE")) {
                        out.print("<td align='center' colspan='2'><b class='rojo'>" + obj_registro[7] + "</b></td>");
                    } else {
                        out.print("<td align='center' colspan='2'><b class='calidad'>" + obj_registro[7] + "</b></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>FORMULA</b></td>");
                    out.print("<td align='center'>" + obj_registro[2] + "</td>");
                    out.print("<td align='center'><b>LOTE</b></td>");
                    out.print("<td align='center'><b class='negro'>" + obj_registro[4] + "</b></td>");
                    out.print("<td align='center'><b>COD DEL COMPUESTO</b></td>");
                    out.print("<td align='center'>" + obj_registro[5] + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<table class='table' style='width:100%'>");
                    for (int i = 0; i < lst_registro_detalle.size(); i++) {
                        Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                        if ((Integer) obj_registro_detalle[2] == 1) {
                            contador_maestra++;
                        } else {
                            contador_equivalente++;
                        }
                    }
                    out.print("<tr>");
                    out.print("<th></td>");
                    if (contador_maestra > 0) {
                        out.print("<th colspan='" + contador_maestra + "'>M DESCRITOS EN LA FORMULA MAESTRA</td>");
                    }
                    if (contador_equivalente > 0) {
                        out.print("<th colspan='" + contador_equivalente + "'>M EQUIVALENTES</td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>CONSECUTIVO</td>");
                    for (int i = 0; i < lst_registro_detalle.size(); i++) {
                        Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                        if (obj_registro_detalle[3].toString() == null ? "" == null : obj_registro_detalle[3].toString().equals("")) {
                            out.print("<td style='background-color:#ddd'></td>");
                        } else {
                            out.print("<td>" + obj_registro_detalle[3] + "</td>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>LOTE PRINCIPAL</td>");
                    for (int i = 0; i < lst_registro_detalle.size(); i++) {
                        Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                        if (obj_registro_detalle[4].toString() == null ? "" == null : obj_registro_detalle[4].toString().equals("")) {
                            out.print("<td style='background-color:#ddd'></td>");
                        } else {
                            out.print("<td>" + obj_registro_detalle[4] + "</td>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>CONSECUTIVO DE CALIDAD</td>");
                    for (int i = 0; i < lst_registro_detalle.size(); i++) {
                        Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                        if (obj_registro_detalle[5].toString() == null ? "" == null : obj_registro_detalle[5].toString().equals("")) {
                            out.print("<td style='background-color:#ddd'></td>");
                        } else {
                            out.print("<td>" + obj_registro_detalle[5] + "</td>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>SUBLOTES DE MATERIA PRIMA</td>");
                    for (int i = 0; i < lst_registro_detalle.size(); i++) {
                        Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                        if (obj_registro_detalle[6].toString() == null ? "" == null : obj_registro_detalle[6].toString().equals("")) {
                            out.print("<td style='background-color:#ddd'></td>");
                        } else {
                            out.print("<td>" + obj_registro_detalle[6] + "</td>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>CONSECUTIVO DE CALIDAD</td>");
                    for (int i = 0; i < lst_registro_detalle.size(); i++) {
                        Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                        if (obj_registro_detalle[7].toString() == null ? "" == null : obj_registro_detalle[7].toString().equals("")) {
                            out.print("<td style='background-color:#ddd'></td>");
                        } else {
                            out.print("<td>" + obj_registro_detalle[7] + "</td>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>OBSERVACIONES</th>");
                    if (version >= 2015.0422) {
                        if (obj_registro[10] == null) {
                            out.print("<td colspan='" + ((contador_maestra + contador_equivalente) - 2) + "'><b class='rojo'>NINGUNA</b></td>");
                        } else {
                            out.print("<td colspan='" + ((contador_maestra + contador_equivalente) - 2) + "'>" + obj_registro[10] + "</td>");
                        }
                        out.print("<td colspan='2'><b>Clasificación</b><br /><b class='negro'>" + obj_registro[11] + "</b></td>");
                    } else if (obj_registro[10] == null) {
                        out.print("<td colspan='" + (contador_maestra + contador_equivalente) + "'><b class='rojo'>NINGUNA</b></td>");
                    } else {
                        out.print("<td colspan='" + (contador_maestra + contador_equivalente) + "'>" + obj_registro[10] + "</td>");
                    }
                    out.print("</tr>");
                    out.print("</table>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="7. REGISTRO DE R-PI-004">
                else if (pageContext.getRequest().getAttribute("Formula").toString().equals("Registro_PI")) {
                    id_formula = Integer.parseInt(pageContext.getRequest().getAttribute("Id_formula").toString());
                    lst_formula = jpacfml.Traer_formula_id(id_formula);
                    Object[] obj_formula = (Object[]) lst_formula.get(0);
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Generar R-PI-004</h3>");
                    if (rol.equals("Inspectora_calidad") || rol.equals("Consulta")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Formula?opc=9' onsubmit='registroC();' method='post' id='FormFormula'>");
                        out.print("<b>Fecha :</b>");
                        out.print("<input type='text' name='Txt_fecha' id='datepicker' placeholder='Fecha' value='" + ano + "/" + mes + "/" + dia + "' title='Fecha' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        out.print("<b>Lote :</b><br />");
                        out.print("<input type='text' name='Txt_lote_part1' id='Txt_lote_part1' style='width:45%' placeholder='Lote' value='" + obj_formula[1].toString().replace(" ", "") + "' title='Lote' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_part1');val1.add(Validate.Presence);</script>");
                        out.print("<b> - </b><input type='text' name='Txt_lote_part2' id='Txt_lote_part2' style='width:35%' placeholder='Lote' value='" + ano_lote + mes_lote + dia + "' maxlength='8' title='Lote' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_part2');val1.add(Validate.Presence);val1.add(Validate.LoteCP_medio);</script>");
                        out.print("<b>Codigo compuesto :</b>");
                        out.print("<input type='text' name='Txt_compuesto' id='Txt_compuesto' placeholder='Codigo compuesto' title='Codigo Compuesto' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_compuesto');val1.add(Validate.Presence);val1.add(Validate.EnterosNA);</script>");
                        if (rol.equals("Administrador")) {
                            out.print("<b>Clasificación :</b>");
                            out.println("<select name='Cbx_clasificacion' id='Cbx_clasificacion'>");
                            out.println("<option value='0'>Seleccionar clasificación</option>");
                            out.println("<option value='PRUEBAS MATERIA PRIMA'>PRUEBAS MATERIA PRIMA</option>");
                            out.println("<option value='PRUEBAS DE FORMULACIÓN'>PRUEBAS DE FORMULACIÓN</option>");
                            out.println("<option value='PRODUCCIÓN'>PRODUCCIÓN</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_clasificacion');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Responsable producción :</b>");
                            out.print("<input type='text' name='Txt_responsable_PI' id='Txt_responsable_PI' value='" + usuario + "' placeholder='Responsable producción' title='Responsable producción' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_responsable_PI');val1.add(Validate.Presence);</script>");
                            out.print("<b>Responsable calidad :</b>");
                            out.print("<input type='text' name='Txt_responsable_GC' id='Txt_responsable_GC' placeholder='Responsable calidad' title='Responsable calidad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_responsable_GC');val1.add(Validate.Presence);</script>");
                        } else {
                            out.print("<b>Clasificación :</b>");
                            out.println("<select name='Cbx_clasificacion' id='Cbx_clasificacion'>");
                            out.println("<option value='0'>Seleccionar clasificación</option>");
                            out.println("<option value='PRUEBAS DE MATERIA PRIMA'>PRUEBAS DE MATERIA PRIMA</option>");
                            out.println("<option value='PRUEBAS DE FORMULACIÓN'>PRUEBAS DE FORMULACIÓN</option>");
                            out.println("<option value='PRODUCCIÓN'>PRODUCCIÓN</option>");
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_clasificacion');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Responsable producción :</b>");
                            out.print("<input type='text' name='Txt_responsable_PI' id='Txt_responsable_PI' readonly='true' value='" + usuario + "' placeholder='Responsable producción' title='Responsable producción' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_responsable_PI');val1.add(Validate.Presence);</script>");
                            out.print("<input type='hidden' name='Txt_responsable_GC' id='Txt_responsable_GC' value='PENDIENTE' />");
                        }
                        out.print("<input type='hidden' name='Id_formula' id='Id_formula' value='" + obj_formula[0] + "'/>");
                        out.print("<input type='submit' id='btsubmit' value='Registrar' />");
                        out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "        </div>");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="8. CONSULTA DE R-PI-004 DE REGISTRO">
                    out.print("<div id='content'>");
                    lst_lotes = jpacrgt.Traer_lotes_formula(id_formula, 0);
                    //lst_historial_formula = jpacrgt.Traer_registro_formula_id(id_formula,"");
                    if (lst_lotes == null) {
                        out.print("<form action='Formula?opc=1&fto=" + obj_formula[1] + "' method='post' name='FormVolver' id='FormVer'>");
                        out.print("<div style='float: left; margin: 10px;'>");
                        out.print("<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' width='30px' height='30px' alt='edit' title='Volver a Formulas' /></a>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("<h3>Sin datos de R-PI-004 ha generar para la formula</h3>");
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de R-PI-004 ha generar para la formula</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Formula?opc=1&fto=" + obj_formula[1] + "' method='post' name='FormVolver' id='FormVer'>");
                        out.print("<div style='float: left;; margin: 10px;'>");
                        out.print("<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' width='30px' height='30px' alt='edit' title='Volver a Formulas' /></a>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("<h3>R-PI-004 En Generación" + obj_formula[1] + "</h3>");
//                        out.print("<ul id='browser' class='treeview-famfamfam' style='width: 100%;'>");
                        for (int i = 0; i < lst_lotes.size(); i++) {
                            Object[] obj_lotes_formula = (Object[]) lst_lotes.get(i);
//                            out.print("<li class='closed'><span class='folder'>" + obj_lotes_formula[1] + "</span><ul>");
                            out.print("<button class=\"accordion\"><center>" + obj_lotes_formula[1] + "</center></button>");
                            out.print("<div class='panel'>");
//                            out.print("<div style='background-color:#fff;width:900px;border:2px solid #006666'>");
                            lst_historial_formula = jpacrgt.Traer_registro_formula_id_filtro(id_formula, obj_lotes_formula[1].toString());
                            out.print("<table class='table' style='width:100%' id='resultados_2'>");
                            out.print("<tr>");
                            out.print("<th>Lote</th>");
                            out.print("<th>Clasificación</th>");
                            out.print("<th>Fecha</th>");
                            out.print("<th>Compuesto</th>");
                            out.print("<th>Producción Insumos</th>");
                            out.print("<th>Calidad</th>");
                            out.print("<th>Observaciones</th>");
                            out.print("<th>Ver</th>");
                            if (rol.equals("Inspectora_calidad") || rol.equals("Administrador")) {
                                out.print("<th>Guardar</th>");
                            }
                            out.print("</tr>");
                            for (int j = 0; j < lst_historial_formula.size(); j++) {
                                Object[] obj_registros = (Object[]) lst_historial_formula.get(j);
                                if ((Integer) obj_registros[8] == 0) {
                                    out.print("<tr>");
                                    out.print("<td align='center'><b>" + obj_registros[4] + "</b></td>");
                                    if (obj_registros[12] == null) {
                                        out.print("<td align='center'>NINGUNA</td>");
                                    } else {
                                        out.print("<td align='center'>" + obj_registros[12] + "</td>");
                                    }
                                    out.print("<td align='center'>" + obj_registros[3] + "</td>");
                                    out.print("<td>" + obj_registros[5] + "</td>");
                                    out.print("<td>" + obj_registros[6] + "</td>");
                                    if (obj_registros[7].equals("PENDIENTE")) {
                                        out.print("<td><b class='rojo'>" + obj_registros[7] + "</b></td>");
                                    } else {
                                        out.print("<td>" + obj_registros[7] + "</td>");
                                    }
                                    if (obj_registros[11] == null) {
                                        out.print("<td><b class='rojo'>NINGUNA</b></td>");
                                    } else {
                                        out.print("<td>" + obj_registros[11] + "</td>");
                                    }
                                    if (rol.equals("Colsulta")) {
                                        out.print("<td><a href='#'><img src='Interfaz/Contenido/Iconos/Warning.png' width='26px' height='26px' alt='edit' title='Sin permisos para ver generación R-PI-004' /></a></td>");
                                    } else {
                                        out.print("<td align='center'>"
                                                + "<form action='Formula?opc=10' method='post' name='FormVer" + obj_registros[0] + "' id='FormVer" + obj_registros[0] + "'>"
                                                + "<input type='hidden' name='Id_registro' value='" + obj_registros[0] + "' />"
                                                + "<input type='hidden' name='Id_formula' value='" + id_formula + "' />"
                                                + "<input type='hidden' name='Cbx_mp_maestra' value='0/0' />"
                                                + "<a href='JAVASCRIPT:FormVer" + obj_registros[0] + ".submit()'><img src='Interfaz/Contenido/Iconos/Ver.png' width='30px' height='30px' alt='edit' title='R-PI-004' /></a>"
                                                + "</form>"
                                                + "</td>");
                                    }
                                    if (rol.equals("Inspectora_calidad") || rol.equals("Administrador")) {
                                        out.print("<td align='center'><a  href='#' onclick='GuardarRegistro(" + obj_registros[0] + "," + id_formula + ")'><img src='Interfaz/Contenido/Iconos/Save.png' width='23px' height='23px' alt='edit' title='Guardar R-PI-004' /></a></td>");
                                    }
                                    out.print("</tr>");
                                }
                            }
                            out.print("</table>");
//                            out.print("</div></ul></li>");
                            out.print("</div>");
                        }
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of content -->");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="9. DILIGENCIAR R-PI-004">
                } else if (pageContext.getRequest().getAttribute("Formula").toString().equals("Registro_PI_detalle")) {
                    id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                    id_formula = Integer.parseInt(pageContext.getRequest().getAttribute("Id_formula").toString());
                    //int id_mp_maestra = Integer.parseInt(pageContext.getRequest().getAttribute("Cbx_mp_maestra").toString());
                    String[] mp_maestra_posicion = pageContext.getRequest().getAttribute("Cbx_mp_maestra").toString().split("/");
                    int id_mp_maestra = Integer.parseInt(mp_maestra_posicion[0].toString());
                    int posicion = Integer.parseInt(mp_maestra_posicion[1].toString());
                    String materia_posicion = id_mp_maestra + "/" + posicion;
                    lst_formula = jpacfml.Traer_formula_id(id_formula);
                    Object[] obj_formula = (Object[]) lst_formula.get(0);
                    out.print("<div id='sidebar'>");
                    out.print("<form action='Formula?opc=8&Id_formula=" + id_formula + "' method='post' name='FormVolver' id='FormVer'>");
                    out.print("<div style='float: left;'>");
                    out.print("<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' width='30px' height='30px' alt='edit' title='Volver a R-PI-004 a generar' /></a>");
                    out.print("</div>");
                    out.print("</form>");
                    //FORM PI GC
                    out.print("<script language='JavaScript'>");
                    out.print("function toggle(elemento) {");
                    out.print("if(elemento.value=='PI') {");
                    out.print("document.getElementById('Formulario_PI').style.display = 'block';");
                    out.print("document.getElementById('Formulario_GC').style.display = 'none';");
                    out.print(" }else{");
                    out.print("document.getElementById('Formulario_GC').style.display = 'block';");
                    out.print("document.getElementById('Formulario_PI').style.display = 'none';");
                    out.print("}}");
                    out.print("</script>");
                    //FIN FORM PI GC
                    out.print("<script language='JavaScript'>");
                    out.print("function muestra_oculta(){");
                    out.print("var sz = document.forms['FormFormula2'].elements['Rdb_tipo_dato'];");
                    out.print("for (var i=0, len=sz.length; i<len; i++) {");
                    out.print("sz[i].onclick = function() {");
                    out.print("if (this.value == 'Maestra') {");
                    out.print("var el = document.getElementById('Datos_formula');");
                    out.print("el.style.display = (el.style.display == 'block') ? 'none' : 'none';");
                    out.print("this.form.Cbx_mp_equivalente.value = 'N/A';");
                    out.print("this.form.Txt_valor.value = '';");
                    out.print("}else if (this.value == 'Equivalente'){");
                    out.print("var el = document.getElementById('Datos_formula');");
                    out.print("el.style.display = (el.style.display == 'none') ? 'block' : 'block'; ");
                    out.print("}");
                    out.print("};");
                    out.print("}");
                    out.print("}");
                    out.print("window.onload = function(){");
                    out.print("muestra_oculta('Datos_formula');");
                    out.print("}");
                    out.print("</script>");
                    lst_registro = jpacrgt.Traer_registro_formula_id_registro(id_registro);
                    Object[] obj_registro = (Object[]) lst_registro.get(0);
                    //<editor-fold defaultstate="collapsed" desc="9.1 DILIGENCIAR CALIDAD">
                    if (rol.equals("Inspectora_calidad")) {
                        out.print("<h3>Generar Detalle R-PI-004</h3>");
                        out.print("<form action='Formula?opc=13' onsubmit='registroD();' method='post' id='FormFormulaObservaciones'>");
                        out.print("<input type='hidden' name='Id_registro' value='" + id_registro + "' />");
                        out.print("<input type='hidden' name='Id_formula' value='" + id_formula + "' />");
                        out.print("<b>Responsable calidad :</b>");
                        out.print("<input type='text' name='Txt_responsable_GC' id='Txt_responsable_GC' readonly='true' value='" + usuario + "' placeholder='Responsable calidad' title='Responsable calidad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_responsable_GC');val1.add(Validate.Presence);</script>");
                        out.print("<b>Observaciones :</b>");
                        if (obj_registro[10] == null) {
                            out.print("<textarea name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones' title='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();' style='height:140px'></textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                        } else {
                            out.print("<textarea name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones' title='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();' style='height:140px'>" + obj_registro[10] + "</textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                        }
                        out.print("<br /><input type='submit' id='btsubmit' value='Registrar' />");
                        out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "        </div>");
                        out.print("</form>");
                    } //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="9.2 DILIGENCIAR INSUMOS">
                    else if (rol.equals("Consulta")) {
                        out.print("<h3>Generar Detalle R-PI-004</h3>");
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else if (rol.equals("Administrador")) {
                        out.print("<h3>Diligenciar</h3>");
                        out.print("<form action='#' method='post' id='FormTipo'>");
                        out.print("<input type='radio' name='Rdb_tipo_formulario' value='PI' onclick='toggle(this)' />Producción Insumos<br />");
                        out.print("<input type='radio' name='Rdb_tipo_formulario' value='GC' onclick='toggle(this)'/>Calidad<br />");
                        out.print("</form>");
                        out.print("<br />");
                        out.print("<div style='display: none' id='Formulario_PI'>");
                        out.print("<h3>Generar Detalle R-PI-004</h3>");
                        out.print("<form action='Formula?opc=10' method='post' id='FormFormula'>"
                                + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                + "<input type='hidden' name='Id_formula' value='" + id_formula + "' />");
                        lst_mp_maestras = jpacfmp.Traer_formula_materia_prima_id(id_formula);
                        out.print("<b>Consecutivo :</b>");
                        out.print("<select name='Cbx_mp_maestra' id='Cbx_mp_maestra' onChange='PostBackFormula()' title='MP Principal'>");
                        out.print("<option value='0' >Seleccionar MP Principal</option>");
                        for (int i = 0; i < lst_mp_maestras.size(); i++) {
                            Object[] obj_mp_maestras = (Object[]) lst_mp_maestras.get(i);
                            if ((Integer) obj_mp_maestras[6] == 1) {
                                if (id_mp_maestra > 0) {
                                    if ((Integer) obj_mp_maestras[0] == id_mp_maestra) {
                                        out.print("<option value='" + obj_mp_maestras[0] + "/" + obj_mp_maestras[7] + "' selected>" + obj_mp_maestras[4] + "</option>");
                                        mp_maestra = obj_mp_maestras[4].toString();
                                    } else {
                                        out.print("<option value='" + obj_mp_maestras[0] + "/" + obj_mp_maestras[7] + "'>" + obj_mp_maestras[4] + "</option>");
                                    }
                                } else {
                                    out.print("<option value='" + obj_mp_maestras[0] + "/" + obj_mp_maestras[7] + "'>" + obj_mp_maestras[4] + "</option>");
                                }
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_mp_maestra');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</form>");
                        out.print("<form action='Formula?opc=11' onsubmit='registroE();' method='post' id='FormFormula2'>"
                                + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                + "<input type='hidden' name='Cbx_mp_maestra' value='" + materia_posicion + "' />"
                                + "<input type='hidden' name='Txt_consecutivo' value='" + mp_maestra + "' />"
                                + "<input type='hidden' name='Id_formula' value='" + id_formula + "' />");
                        out.print("<b>Visualizar :</b><br />");
                        out.print("<input type='radio' name='Rdb_tipo_dato' value='Maestra' />MP Principal<br />");
                        out.print("<input type='radio' name='Rdb_tipo_dato' value='Equivalente' />MP Equivalente<br />");
                        out.print("<div style='display: none' id='Datos_formula'>");
                        out.print("<b>MP Equivalente :</b>");
                        out.print("<select name='Cbx_mp_equivalente' id='Cbx_mp_equivalente' title='MP Equivalente'>");
                        out.print("<option value='0' >Seleccionar MP Equivalente</option>");
                        out.print("<option value='N/A' style='display:none' >N/A</option>");
                        if (id_mp_maestra > 0) {
                            lst_mp_equivalentes = jpacfmp.Traer_formula_materia_prima_id_mp(id_mp_maestra);
                            Object[] obj_mp_equivalentes = (Object[]) lst_mp_equivalentes.get(0);
                            String vector_equivalentes[] = obj_mp_equivalentes[5].toString().split("-");
                            for (int i = 0; i < vector_equivalentes.length; i++) {
                                out.print("<option value='" + vector_equivalentes[i] + "' >" + vector_equivalentes[i] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_mp_equivalente');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</div>");
                        out.print("<b>Lote principal :</b>");
                        out.print("<input type='text' name='Txt_lote_principal' id='Txt_lote_principal' placeholder='Lote principal' title='Lote principal' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_principal');val1.add(Validate.Presence);</script>");
                        out.print("<b>Consecutivo calidad :</b>");
                        out.print("<input type='text' name='Txt_consecutivo_calidad' id='Txt_consecutivo_calidad' placeholder='Consecutivo calidad' title='Consecutivo calidad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_consecutivo_calidad');val1.add(Validate.Presence);</script>");
                        out.print("<b>Sub lote :</b>");
                        out.print("<input type='text' name='Txt_sub_lote' id='Txt_sub_lote' placeholder='Sub Lote de la MP' title='Sub Lote de la MP' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                        // + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sub_lote');val1.add(Validate.Presence);</script>");
                        out.print("<b>Consecutivo calidad :</b>");
                        out.print("<input type='text' name='Txt_sub_consecutivo_calidad' id='Txt_sub_consecutivo_calidad' placeholder='Consecutivo calidad' title='Consecutivo calidad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sub_consecutivo_calidad');val1.add(Validate.Enteros);</script>");
                        out.print("<br /><input type='submit' id='btsubmit' value='Registrar' />");
                        out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "        </div>");
                        out.print("</form>");
                        out.print("</div>");
                        //<editor-fold defaultstate="collapsed" desc="9.2.1 DILIGENCIAR CALIDAD">
                        out.print("<div style='display: none' id='Formulario_GC'>");
                        out.print("<h3>Generar Detalle R-PI-004</h3>");
                        out.print("<form action='Formula?opc=13' onsubmit='registroD();' method='post' id='FormFormulaObservaciones'>");
                        out.print("<input type='hidden' name='Id_registro' value='" + id_registro + "' />");
                        out.print("<input type='hidden' name='Id_formula' value='" + id_formula + "' />");
                        out.print("<b>Responsable calidad :</b>");
                        out.print("<input type='text' name='Txt_responsable_GC' id='Txt_responsable_GC' value='" + obj_registro[7] + "' placeholder='Responsable calidad' title='Responsable calidad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_responsable_GC');val1.add(Validate.Presence);</script>");
                        out.print("<b>Observaciones :</b>");
                        if (obj_registro[10] == null) {
                            out.print("<textarea name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones' title='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();' style='height:140px'></textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                        } else {
                            out.print("<textarea name='Txt_observaciones' id='Txt_observaciones' placeholder='Observaciones' title='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();' style='height:140px'>" + obj_registro[10] + "</textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script>");
                        }
                        out.print("<br /><input type='submit' id='btsubmit' value='Registrar' />");
                        out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "        </div>");
                        out.print("</form>");
                        out.print("</div>");
                    } //</editor-fold>
                    else {
                        out.print("<h3>Generar Detalle R-PI-004</h3>");
                        out.print("<form action='Formula?opc=10' method='post' id='FormFormula'>"
                                + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                + "<input type='hidden' name='Id_formula' value='" + id_formula + "' />");
                        lst_mp_maestras = jpacfmp.Traer_formula_materia_prima_id(id_formula);
                        out.print("<b>Consecutivo :</b>");
                        out.print("<select name='Cbx_mp_maestra' id='Cbx_mp_maestra' onChange='PostBackFormula()' title='MP Principal'>");
                        out.print("<option value='0' >Seleccionar MP Principal</option>");
                        for (int i = 0; i < lst_mp_maestras.size(); i++) {
                            Object[] obj_mp_maestras = (Object[]) lst_mp_maestras.get(i);
                            if (id_mp_maestra > 0) {
                                if ((Integer) obj_mp_maestras[0] == id_mp_maestra) {
                                    out.print("<option value='" + obj_mp_maestras[0] + "/" + obj_mp_maestras[7] + "' selected>" + obj_mp_maestras[4] + "</option>");
                                    mp_maestra = obj_mp_maestras[4].toString();
                                } else {
                                    out.print("<option value='" + obj_mp_maestras[0] + "/" + obj_mp_maestras[7] + "'>" + obj_mp_maestras[4] + "</option>");
                                }
                            } else {
                                out.print("<option value='" + obj_mp_maestras[0] + "/" + obj_mp_maestras[7] + "'>" + obj_mp_maestras[4] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_mp_maestra');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</form>");
                        out.print("<form action='Formula?opc=11' onsubmit='registroE();' method='post' id='FormFormula2'>"
                                + "<input type='hidden' name='Id_registro' value='" + id_registro + "' />"
                                + "<input type='hidden' name='Cbx_mp_maestra' value='" + materia_posicion + "' />"
                                + "<input type='hidden' name='Txt_consecutivo' value='" + mp_maestra + "' />"
                                + "<input type='hidden' name='Id_formula' value='" + id_formula + "' />");
                        out.print("<b>Visualizar :</b><br />");
                        out.print("<input type='radio' name='Rdb_tipo_dato' value='Maestra' />MP Principal<br />");
                        out.print("<input type='radio' name='Rdb_tipo_dato' value='Equivalente' />MP Equivalente<br />");
                        out.print("<div style='display: none' id='Datos_formula'>");
                        out.print("<b>MP Equivalente :</b>");
                        out.print("<select name='Cbx_mp_equivalente' id='Cbx_mp_equivalente' title='MP Equivalente'>");
                        out.print("<option value='0' >Seleccionar MP Equivalente</option>");
                        out.print("<option value='N/A' style='display:none' >N/A</option>");
                        if (id_mp_maestra > 0) {
                            lst_mp_equivalentes = jpacfmp.Traer_formula_materia_prima_id_mp(id_mp_maestra);
                            Object[] obj_mp_equivalentes = (Object[]) lst_mp_equivalentes.get(0);
                            String vector_equivalentes[] = obj_mp_equivalentes[5].toString().split("-");
                            for (int i = 0; i < vector_equivalentes.length; i++) {
                                out.print("<option value='" + vector_equivalentes[i] + "' >" + vector_equivalentes[i] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_mp_equivalente');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</div>");
                        out.print("<b>Lote principal :</b>");
                        out.print("<input type='text' name='Txt_lote_principal' id='Txt_lote_principal' placeholder='Lote principal' title='Lote principal' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote_principal');val1.add(Validate.Presence);</script>");
                        out.print("<b>Consecutivo calidad :</b>");
                        out.print("<input type='text' name='Txt_consecutivo_calidad' id='Txt_consecutivo_calidad' placeholder='Consecutivo calidad' title='Consecutivo calidad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_consecutivo_calidad');val1.add(Validate.Presence);</script>");
                        out.print("<b>Sub lote :</b>");
                        out.print("<input type='text' name='Txt_sub_lote' id='Txt_sub_lote' placeholder='Sub Lote de la MP' title='Sub Lote de la MP' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                        // + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sub_lote');val1.add(Validate.Presence);</script>");
                        out.print("<b>Consecutivo calidad :</b>");
                        out.print("<input type='text' name='Txt_sub_consecutivo_calidad' id='Txt_sub_consecutivo_calidad' placeholder='Consecutivo calidad' title='Consecutivo calidad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sub_consecutivo_calidad');val1.add(Validate.Enteros);</script>");
                        out.print("<br /><input type='submit' id='btsubmit' value='Registrar' />");
                        out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "        </div>");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");

//</editor-fold>
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="10. CONSULTAR DILIGENCIADO">
                    out.print("<div id='content'>");
                    lst_registro_detalle = jpacrdt.Traer_registro_detalle_id_registro(id_registro);
                    String[] fecha_decimal = obj_registro[3].toString().split("-");
                    double version = Double.parseDouble(fecha_decimal[0] + "." + fecha_decimal[1] + fecha_decimal[2]);
                    out.print("<br />");
                    out.print("<table class='table' style='width:100%'>");
                    if (version >= 2016.0101) {
                        out.print("<tr>");
                        out.print("<td colspan='6' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                    }
                    out.print("<tr>");
                    out.print("<td align='center' colspan='2' rowspan='2'>"
                            + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:202.5px;height:67.5px' />"
                            + "</td>");
                    if (version >= 2015.0422) {
                        out.print("<td align='center' colspan='2'>REGISTRO</td>");
                        out.print("<td align='center' colspan='2'>CODIGO <b> R-PI-004 </b></td>");
                    } else {
                        out.print("<td align='center' colspan='2'>MANUAL DE REGISTROS</td>");
                        out.print("<td align='center' colspan='2'>CODIGO <b> R-PI-004 </b></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    if (version >= 2015.0422) {
                        out.print("<td align='center' colspan='2'>CONTROL LOTES DE MATERIAS<br />PRIMAS EN FORMULAS</td>");
                        out.print("<td align='center' colspan='2'>VERSIÓN: <b>1</b><br /><b class='rojo'>EN ELABORACIÓN</b></td>");
                    } else {
                        out.print("<td align='center' colspan='2'>CONTROL LOTES DE MATERIAS<br />PRIMAS EN FORMULAS</td>");
                        out.print("<td align='center' colspan='2'>VERSION <b>0</b><b class='rojo'>EN ELABORACIÓN</b></td>");
                    }
                    out.print("</tr>");
//                    if (version >= 2016.0101) {
//                        out.print("<tr>");
//                        out.print("<td colspan='6' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
//                        out.print("</tr>");
//                    }
//                    out.print("<tr>");
//                    out.print("<td align='center' colspan='2'>"
//                            + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
//                            + "</td>");
//                    out.print("<td align='center' colspan='2'><h3 class='negro'>MANUAL DE REGISTROS<br />CONTROL LOTES DE MATERIAS<br />PRIMAS EN FORMULAS</h3></td>");
//                    if (version >= 2015.0422) {
//                        out.print("<td align='center' colspan='2'><h3 class='negro'>CODIGO <b> R-PI-004 </b> VERSION <b>1</b><br /><b class='rojo'>EN ELABORACIÓN</b></h3></td>");
//                    } else {
//                        out.print("<td align='center' colspan='2'><h3 class='negro'>CODIGO <b> R-PI-004 </b> VERSION <b>0</b><br /><b class='rojo'>EN ELABORACIÓN</b></h3></td>");
//                    }
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th colspan='2'>FECHA</th>");
                    out.print("<th colspan='2'>RESPONSABLE POR PRODUCCIÓN</th>");
                    out.print("<th colspan='2'>RESPONSABLE POR CALIDAD</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='2'>" + obj_registro[3] + "</td>");
                    out.print("<td align='center' colspan='2'>" + obj_registro[6] + "</td>");
                    if (obj_registro[7].equals("PENDIENTE")) {
                        out.print("<td align='center' colspan='2'><b class='rojo'>" + obj_registro[7] + "</b></td>");
                    } else {
                        out.print("<td align='center' colspan='2'><b class='calidad'>" + obj_registro[7] + "</b></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>FORMULA</b></td>");
                    out.print("<td align='center'>" + obj_registro[2] + "</td>");
                    out.print("<td align='center'><b>LOTE</b></td>");
                    out.print("<td align='center'><b class='negro'>" + obj_registro[4] + "</b></td>");
                    out.print("<td align='center'><b>COD DEL COMPUESTO</b></td>");
                    out.print("<td align='center'>" + obj_registro[5] + "</td>");
                    out.print("</tr>");
                    if (lst_registro_detalle == null) {
                        out.print("<tr>");
                        out.print("<td align='center' colspan='6'>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin detalle de registro</b>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th colspan='2'>OBSERVACIONES</th>");
                        if (version >= 2015.0422) {
                            if (obj_registro[10] == null) {
                                out.print("<td colspan='2'><b class='rojo'>NINGUNA</b></td>");
                            } else {
                                out.print("<td colspan='2'>" + obj_registro[10] + "</td>");
                            }
                            out.print("<td colspan='2'><b>Clasificación</b><br /><b class='negro'>" + obj_registro[11] + "</b></td>");
                        } else if (obj_registro[10] == null) {
                            out.print("<td colspan='4'><b class='rojo'>NINGUNA</b></td>");
                        } else {
                            out.print("<td colspan='4'>" + obj_registro[10] + "</td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<table class='table3' style='width:100%'>");
                    for (int i = 0; i < lst_registro_detalle.size(); i++) {
                        Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                        if ((Integer) obj_registro_detalle[2] == 1) {
                            contador_maestra++;
                        } else {
                            contador_equivalente++;
                        }
                    }
                    out.print("<tr>");
                    out.print("<th></td>");
                    if (contador_maestra > 0) {
                        out.print("<th colspan='" + contador_maestra + "'>M DESCRITOS EN LA FORMULA MAESTRA</td>");
                    }
                    if (contador_equivalente > 0) {
                        out.print("<th colspan='" + contador_equivalente + "'>M EQUIVALENTES</td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='padding: 3px 7px 7px 3px;'>CONSECUTIVO</td>");
                    for (int i = 0; i < lst_registro_detalle.size(); i++) {
                        Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                        if (rol.equals("Jefe_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta")) {
                            if (obj_registro_detalle[3].toString() == null ? "" == null : obj_registro_detalle[3].toString().equals("")) {
                                out.print("<td style='background-color:#ddd'></td>");
                            } else {
                                out.print("<td><b>" + obj_registro_detalle[3] + "</b></td>");
                            }
                        } else if (obj_registro_detalle[3].toString() == null ? "" == null : obj_registro_detalle[3].toString().equals("")) {
                            out.print("<td style='background-color:#ddd'></td>");
                        } else {
                            out.print("<td><b><a onclick='QuitarMPRegistros(" + obj_registro_detalle[0] + "," + id_formula + "," + id_registro + ")' title='Quitar " + obj_registro_detalle[3] + " de la generación del lote'>" + obj_registro_detalle[3] + "</a></b></td>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>LOTE PRINCIPAL</td>");
                    for (int i = 0; i < lst_registro_detalle.size(); i++) {
                        Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                        if (rol.equals("Jefe_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta")) {
                            if (obj_registro_detalle[4].toString() == null ? "" == null : obj_registro_detalle[4].toString().equals("")) {
                                out.print("<td style='background-color:#ddd'></td>");
                            } else {
                                out.print("<td>" + obj_registro_detalle[4] + "</td>");
                            }
                        } else if (obj_registro_detalle[4].toString() == null ? "" == null : obj_registro_detalle[4].toString().equals("")) {
                            out.print("<td style='background-color:#ddd'>"
                                    + "<form id='FormMod" + obj_registro_detalle[4] + "' name='FormMod" + obj_registro_detalle[4] + "' action='Formula?opc=16' method='post'>"
                                    + "<input type='hidden' id='Id_registro_detalle' name='Id_registro_detalle' value='" + obj_registro_detalle[0] + "' />"
                                    + "<input type='hidden' id='Id_registro' name='Id_registro' value='" + id_registro + "' />"
                                    + "<input type='hidden' id='Id_formula' name='Id_formula' value='" + id_formula + "' />"
                                    + "<input type='hidden' id='Tipo_parametro' name='Tipo_parametro' value='lote_principal' />"
                                    + "<input type='text' id='Txt_valor_" + obj_registro_detalle[0] + "_lote_principal' name='Txt_valor_" + obj_registro_detalle[0] + "_lote_principal' "
                                    + "style='background-color:#ddd;border-width:0;width:60px;font-size: 11px;color:#292929;' /></form></td>");
                        } else {
                            out.print("<td>"
                                    + "<form id='FormMod" + obj_registro_detalle[4] + "' name='FormMod" + obj_registro_detalle[4] + "' action='Formula?opc=16' method='post'>"
                                    + "<input type='hidden' id='Id_registro_detalle' name='Id_registro_detalle' value='" + obj_registro_detalle[0] + "' />"
                                    + "<input type='hidden' id='Id_registro' name='Id_registro' value='" + id_registro + "' />"
                                    + "<input type='hidden' id='Id_formula' name='Id_formula' value='" + id_formula + "' />"
                                    + "<input type='hidden' id='Tipo_parametro' name='Tipo_parametro' value='lote_principal' />"
                                    + "<input type='text' id='Txt_valor_" + obj_registro_detalle[0] + "_lote_principal' name='Txt_valor_" + obj_registro_detalle[0] + "_lote_principal' value='" + obj_registro_detalle[4] + "'"
                                    + "style='border-width:0;width:60px;font-size: 11px;color:#292929;' /></form></td>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>CONSECUTIVO DE CALIDAD</td>");
                    for (int i = 0; i < lst_registro_detalle.size(); i++) {
                        Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                        if (rol.equals("Jefe_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta")) {
                            if (obj_registro_detalle[5].toString() == null ? "" == null : obj_registro_detalle[5].toString().equals("")) {
                                out.print("<td style='background-color:#ddd'></td>");
                            } else {
                                out.print("<td>" + obj_registro_detalle[5] + "</td>");
                            }
                        } else if (obj_registro_detalle[5].toString() == null ? "" == null : obj_registro_detalle[5].toString().equals("")) {
                            out.print("<td style='background-color:#ddd'>"
                                    + "<form id='FormMod" + obj_registro_detalle[5] + "' name='FormMod" + obj_registro_detalle[5] + "' action='Formula?opc=16' method='post'>"
                                    + "<input type='hidden' id='Id_registro_detalle' name='Id_registro_detalle' value='" + obj_registro_detalle[0] + "' />"
                                    + "<input type='hidden' id='Id_registro' name='Id_registro' value='" + id_registro + "' />"
                                    + "<input type='hidden' id='Id_formula' name='Id_formula' value='" + id_formula + "' />"
                                    + "<input type='hidden' id='Tipo_parametro' name='Tipo_parametro' value='consecutivo_calidad_principal' />"
                                    + "<input type='text' id='Txt_valor_" + obj_registro_detalle[0] + "_consecutivo_calidad_principal' name='Txt_valor_" + obj_registro_detalle[0] + "_consecutivo_calidad_principal' "
                                    + "style='background-color:#ddd;border-width:0;width:60px;font-size: 11px;color:#292929;' /></form></td>");
                        } else {
                            out.print("<td>"
                                    + "<form id='FormMod" + obj_registro_detalle[5] + "' name='FormMod" + obj_registro_detalle[5] + "' action='Formula?opc=16' method='post'>"
                                    + "<input type='hidden' id='Id_registro_detalle' name='Id_registro_detalle' value='" + obj_registro_detalle[0] + "' />"
                                    + "<input type='hidden' id='Id_registro' name='Id_registro' value='" + id_registro + "' />"
                                    + "<input type='hidden' id='Id_formula' name='Id_formula' value='" + id_formula + "' />"
                                    + "<input type='hidden' id='Tipo_parametro' name='Tipo_parametro' value='consecutivo_calidad_principal' />"
                                    + "<input type='text' id='Txt_valor_" + obj_registro_detalle[0] + "_consecutivo_calidad_principal' name='Txt_valor_" + obj_registro_detalle[0] + "_consecutivo_calidad_principal' value='" + obj_registro_detalle[5] + "'"
                                    + "style='border-width:0;width:60px;font-size: 11px;color:#292929;' /></form></td>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>SUBLOTES DE MATERIA PRIMA</td>");
                    for (int i = 0; i < lst_registro_detalle.size(); i++) {
                        Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                        if (rol.equals("Jefe_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta")) {
                            if (obj_registro_detalle[6].toString() == null ? "" == null : obj_registro_detalle[6].toString().equals("")) {
                                out.print("<td style='background-color:#ddd'></td>");
                            } else {
                                out.print("<td>" + obj_registro_detalle[6] + "</td>");
                            }
                        } else if (obj_registro_detalle[6].toString() == null ? "" == null : obj_registro_detalle[6].toString().equals("")) {
                            out.print("<td style='background-color:#ddd'>"
                                    + "<form id='FormMod" + obj_registro_detalle[6] + "' name='FormMod" + obj_registro_detalle[6] + "' action='Formula?opc=16' method='post'>"
                                    + "<input type='hidden' id='Id_registro_detalle' name='Id_registro_detalle' value='" + obj_registro_detalle[0] + "' />"
                                    + "<input type='hidden' id='Id_registro' name='Id_registro' value='" + id_registro + "' />"
                                    + "<input type='hidden' id='Id_formula' name='Id_formula' value='" + id_formula + "' />"
                                    + "<input type='hidden' id='Tipo_parametro' name='Tipo_parametro' value='sub_lote' />"
                                    + "<input type='text' id='Txt_valor_" + obj_registro_detalle[0] + "_sub_lote' name='Txt_valor_" + obj_registro_detalle[0] + "_sub_lote' "
                                    + "style='background-color:#ddd;border-width:0;width:60px;font-size: 11px;color:#292929;' /></form></td>");
                        } else {
                            out.print("<td>"
                                    + "<form id='FormMod" + obj_registro_detalle[6] + "' name='FormMod" + obj_registro_detalle[6] + "' action='Formula?opc=16' method='post'>"
                                    + "<input type='hidden' id='Id_registro_detalle' name='Id_registro_detalle' value='" + obj_registro_detalle[0] + "' />"
                                    + "<input type='hidden' id='Id_registro' name='Id_registro' value='" + id_registro + "' />"
                                    + "<input type='hidden' id='Id_formula' name='Id_formula' value='" + id_formula + "' />"
                                    + "<input type='hidden' id='Tipo_parametro' name='Tipo_parametro' value='sub_lote' />"
                                    + "<input type='text' id='Txt_valor_" + obj_registro_detalle[0] + "_sub_lote' name='Txt_valor_" + obj_registro_detalle[0] + "_sub_lote' value='" + obj_registro_detalle[6] + "'"
                                    + "style='border-width:0;width:60px;font-size: 11px;color:#292929;' /></form></td>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>CONSECUTIVO DE CALIDAD</td>");
                    for (int i = 0; i < lst_registro_detalle.size(); i++) {
                        Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                        if (rol.equals("Jefe_PI") || rol.equals("Inspectora_calidad") || rol.equals("Consulta")) {
                            if (obj_registro_detalle[7].toString() == null ? "" == null : obj_registro_detalle[7].toString().equals("")) {
                                out.print("<td style='background-color:#ddd'></td>");
                            } else {
                                out.print("<td>" + obj_registro_detalle[7] + "</td>");
                            }
                        } else if (obj_registro_detalle[7].toString() == null ? "" == null : obj_registro_detalle[7].toString().equals("")) {
                            out.print("<td style='background-color:#ddd'>"
                                    + "<form id='FormMod" + obj_registro_detalle[7] + "' name='FormMod" + obj_registro_detalle[7] + "' action='Formula?opc=16' method='post'>"
                                    + "<input type='hidden' id='Id_registro_detalle' name='Id_registro_detalle' value='" + obj_registro_detalle[0] + "' />"
                                    + "<input type='hidden' id='Id_registro' name='Id_registro' value='" + id_registro + "' />"
                                    + "<input type='hidden' id='Id_formula' name='Id_formula' value='" + id_formula + "' />"
                                    + "<input type='hidden' id='Tipo_parametro' name='Tipo_parametro' value='consecutivo_calidad_sub' />"
                                    + "<input type='text' id='Txt_valor_" + obj_registro_detalle[0] + "_consecutivo_calidad_sub' name='Txt_valor_" + obj_registro_detalle[0] + "_consecutivo_calidad_sub' "
                                    + "style='background-color:#ddd;border-width:0;width:60px;font-size: 11px;color:#292929;' /></form></td>");
                        } else {
                            out.print("<td>"
                                    + "<form id='FormMod" + obj_registro_detalle[7] + "' name='FormMod" + obj_registro_detalle[7] + "' action='Formula?opc=16' method='post'>"
                                    + "<input type='hidden' id='Id_registro_detalle' name='Id_registro_detalle' value='" + obj_registro_detalle[0] + "' />"
                                    + "<input type='hidden' id='Id_registro' name='Id_registro' value='" + id_registro + "' />"
                                    + "<input type='hidden' id='Id_formula' name='Id_formula' value='" + id_formula + "' />"
                                    + "<input type='hidden' id='Tipo_parametro' name='Tipo_parametro' value='consecutivo_calidad_sub' />"
                                    + "<input type='text' id='Txt_valor_" + obj_registro_detalle[0] + "_consecutivo_calidad_sub' name='Txt_valor_" + obj_registro_detalle[0] + "_consecutivo_calidad_sub' value='" + obj_registro_detalle[7] + "'"
                                    + "style='border-width:0;width:60px;font-size: 11px;color:#292929;' /></form></td>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>OBSERVACIONES</th>");
                    if (version >= 2015.0422) {
                        if (obj_registro[10] == null) {
                            out.print("<td colspan='" + ((contador_maestra + contador_equivalente) - 2) + "'><b class='rojo'>NINGUNA</b></td>");
                        } else {
                            out.print("<td colspan='" + ((contador_maestra + contador_equivalente) - 2) + "'>" + obj_registro[10] + "</td>");
                        }
                        out.print("<td colspan='2'><b>Clasificación</b><br /><b class='negro'>" + obj_registro[11] + "</b></td>");
                    } else if (obj_registro[10] == null) {
                        out.print("<td colspan='" + (contador_maestra + contador_equivalente) + "'><b class='rojo'>NINGUNA</b></td>");
                    } else {
                        out.print("<td colspan='" + (contador_maestra + contador_equivalente) + "'>" + obj_registro[10] + "</td>");
                    }
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of content -->");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="11. GAPP GENERACIÓN DE LOTES">
                else if (pageContext.getRequest().getAttribute("Formula").toString().equals("Generacion_lotes_app")) {
                    lote = pageContext.getRequest().getAttribute("Lote").toString();
                    lst_lotes = jpacrgt.Traer_registro_formula_lote_app(lote);
                    for (int z = 0; z < lst_lotes.size(); z++) {
                        Object[] obj_lotes_app = (Object[]) lst_lotes.get(z);
                        id_registro = Integer.parseInt(obj_lotes_app[0].toString());
                        lst_registro = jpacrgt.Traer_registro_formula_id_registro(id_registro);
                        Object[] obj_registro = (Object[]) lst_registro.get(0);
                        lst_registro_detalle = jpacrdt.Traer_registro_detalle_id_registro(id_registro);
                        String[] fecha_decimal = obj_registro[3].toString().split("-");
                        double version = Double.parseDouble(fecha_decimal[0] + "." + fecha_decimal[1] + fecha_decimal[2]);
                        out.print("<table class='table' style='width:100%'>");
                        if (version >= 2016.0101) {
                            out.print("<tr>");
                            out.print("<td colspan='6' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<td align='center' colspan='2' rowspan='2'>"
                                + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:202.5px;height:67.5px' />"
                                + "</td>");
                        if (version >= 2015.0422) {
                            out.print("<td align='center' colspan='2'>REGISTRO</td>");
                            out.print("<td align='center' colspan='2'>CODIGO <b> R-PI-004 </b></td>");
                        } else {
                            out.print("<td align='center' colspan='2'>MANUAL DE REGISTROS</td>");
                            out.print("<td align='center' colspan='2'>CODIGO <b> R-PI-004 </b></td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        if (version >= 2015.0422) {
                            out.print("<td align='center' colspan='2'>CONTROL LOTES DE MATERIAS<br />PRIMAS EN FORMULAS</td>");
                            out.print("<td align='center' colspan='2'>VERSIÓN: <b>1</td>");
                        } else {
                            out.print("<td align='center' colspan='2'>CONTROL LOTES DE MATERIAS<br />PRIMAS EN FORMULAS</td>");
                            out.print("<td align='center' colspan='2'>VERSION <b>0</b></td>");
                        }
                        out.print("</tr>");
//                        if (version >= 2016.0101) {
//                            out.print("<tr>");
//                            out.print("<td colspan='6' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
//                            out.print("</tr>");
//                        }
//                        out.print("<tr>");
//                        out.print("<td align='center' colspan='2'>"
//                                + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:202.5px;height:67.5px' />"
//                                + "</td>");
//                        out.print("<td align='center' colspan='2'><h3 class='negro'>MANUAL DE REGISTROS<br />CONTROL LOTES DE MATERIAS<br />PRIMAS EN FORMULAS</h3></td>");
//                        if (version >= 2015.0422) {
//                            out.print("<td align='center' colspan='2'><h3 class='negro'>CODIGO <b> R-PI-004 </b> VERSION <b>1</h3></td>");
//                        } else {
//                            out.print("<td align='center' colspan='2'><h3 class='negro'>CODIGO <b> R-PI-004 </b> VERSION <b>0</b></h3></td>");
//                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th colspan='2'>FECHA</th>");
                        out.print("<th colspan='2'>RESPONSABLE POR PRODUCCIÓN</th>");
                        out.print("<th colspan='2'>RESPONSABLE POR CALIDAD</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='2'>" + obj_registro[3] + "</td>");
                        out.print("<td align='center' colspan='2'>" + obj_registro[6] + "</td>");
                        if (obj_registro[7].equals("PENDIENTE")) {
                            out.print("<td align='center' colspan='2'><b class='rojo'>" + obj_registro[7] + "</b></td>");
                        } else {
                            out.print("<td align='center' colspan='2'><b class='calidad'>" + obj_registro[7] + "</b></td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>FORMULA</b></td>");
                        out.print("<td align='center'>" + obj_registro[2] + "</td>");
                        out.print("<td align='center'><b>LOTE</b></td>");
                        out.print("<td align='center'><b class='negro'>" + obj_registro[4] + "</b></td>");
                        out.print("<td align='center'><b>COD DEL COMPUESTO</b></td>");
                        out.print("<td align='center'>" + obj_registro[5] + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("<table class='table' style='width:100%'>");
                        for (int i = 0; i < lst_registro_detalle.size(); i++) {
                            Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                            if ((Integer) obj_registro_detalle[2] == 1) {
                                contador_maestra++;
                            } else {
                                contador_equivalente++;
                            }
                        }
                        out.print("<tr>");
                        out.print("<th></td>");
                        if (contador_maestra > 0) {
                            out.print("<th colspan='" + contador_maestra + "'>M DESCRITOS EN LA FORMULA MAESTRA</td>");
                        }
                        if (contador_equivalente > 0) {
                            out.print("<th colspan='" + contador_equivalente + "'>M EQUIVALENTES</td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td>CONSECUTIVO</td>");
                        for (int i = 0; i < lst_registro_detalle.size(); i++) {
                            Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                            if (obj_registro_detalle[3].toString() == null ? "" == null : obj_registro_detalle[3].toString().equals("")) {
                                out.print("<td style='background-color:#ddd'></td>");
                            } else {
                                out.print("<td>" + obj_registro_detalle[3] + "</td>");
                            }
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td>LOTE PRINCIPAL</td>");
                        for (int i = 0; i < lst_registro_detalle.size(); i++) {
                            Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                            if (obj_registro_detalle[4].toString() == null ? "" == null : obj_registro_detalle[4].toString().equals("")) {
                                out.print("<td style='background-color:#ddd'></td>");
                            } else {
                                out.print("<td>" + obj_registro_detalle[4] + "</td>");
                            }
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td>CONSECUTIVO DE CALIDAD</td>");
                        for (int i = 0; i < lst_registro_detalle.size(); i++) {
                            Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                            if (obj_registro_detalle[5].toString() == null ? "" == null : obj_registro_detalle[5].toString().equals("")) {
                                out.print("<td style='background-color:#ddd'></td>");
                            } else {
                                out.print("<td>" + obj_registro_detalle[5] + "</td>");
                            }
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td>SUBLOTES DE MATERIA PRIMA</td>");
                        for (int i = 0; i < lst_registro_detalle.size(); i++) {
                            Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                            if (obj_registro_detalle[6].toString() == null ? "" == null : obj_registro_detalle[6].toString().equals("")) {
                                out.print("<td style='background-color:#ddd'></td>");
                            } else {
                                out.print("<td>" + obj_registro_detalle[6] + "</td>");
                            }
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td>CONSECUTIVO DE CALIDAD</td>");
                        for (int i = 0; i < lst_registro_detalle.size(); i++) {
                            Object[] obj_registro_detalle = (Object[]) lst_registro_detalle.get(i);
                            if (obj_registro_detalle[7].toString() == null ? "" == null : obj_registro_detalle[7].toString().equals("")) {
                                out.print("<td style='background-color:#ddd'></td>");
                            } else {
                                out.print("<td>" + obj_registro_detalle[7] + "</td>");
                            }
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th>OBSERVACIONES</th>");
                        if (version >= 2015.0422) {
                            if (obj_registro[10] == null) {
                                out.print("<td colspan='" + ((contador_maestra + contador_equivalente) - 2) + "'><b class='rojo'>NINGUNA</b></td>");
                            } else {
                                out.print("<td colspan='" + ((contador_maestra + contador_equivalente) - 2) + "'>" + obj_registro[10] + "</td>");
                            }
                            out.print("<td colspan='2'><b>Clasificación</b><br /><b class='negro'>" + obj_registro[11] + "</b></td>");
                        } else if (obj_registro[10] == null) {
                            out.print("<td colspan='" + (contador_maestra + contador_equivalente) + "'><b class='rojo'>NINGUNA</b></td>");
                        } else {
                            out.print("<td colspan='" + (contador_maestra + contador_equivalente) + "'>" + obj_registro[10] + "</td>");
                        }
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</hr>");
                    }
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="12. REGISTRAR DUREZA">
                else if (pageContext.getRequest().getAttribute("Formula").toString().equals("Control_durezas")) {
                    id_formula = Integer.parseInt(pageContext.getRequest().getAttribute("Id_formula").toString());
                    id_dureza = Integer.parseInt(pageContext.getRequest().getAttribute("Id_dureza").toString());
                    List lista_lotes = jpacfml.Traer_lotes(id_formula);
                    lst_formula = jpacfml.Traer_formula_id(id_formula);
                    Object[] obj_formula = (Object[]) lst_formula.get(0);
                    lst_durezas = jpacfml.Traer_durezas_id_formula(id_formula);
                    if (id_dureza > 0) {
                        lst_dureza_id = jpacfml.Traer_durezas_id_formula(id_formula);
                        Object[] obj_dato_dureza = (Object[]) lst_dureza_id.get(0);
                        out.print("<div id='sidebar'>");
                        out.print("<div align=\"right\"><a href='Formula?opc=1&Id_formula=0&fto='><img src=\"Interfaz/Contenido/Iconos/Delete.png\" width=\"26px\" height=\"26px\" alt=\"edit\" title=\"Cancelar Modificación\"></a></div>");
                        out.print("<h3>Modificar Dureza</h3>");
                        if (rol.equals("Coordinador_PI") || rol.equals("Jefe_PI") || rol.equals("Consulta")) {
                            //<editor-fold defaultstate="collapsed" desc="PERMISOS POR ROL">
                            out.print("<center>");
                            out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='Sin permisos' /><br />");
                            out.print("<b>Sin permisos de modificaciones</b>");
                            out.print("</center>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                            out.print("<form action='Formula?opc=21&idz=" + id_dureza + "&ifm=" + id_formula + "' onsubmit='registroD();' method='post' id='FormDureza'>");
                            out.print("<input type='hidden' id='Txt_parametro_max' value='" + obj_formula[8] + "' />");
                            out.print("<input type='hidden' id='Txt_parametro_min' value='" + obj_formula[9] + "' />");
                            out.print("<b>Fecha :</b>");
                            out.print("<input type='text' name='Txt_fecha' id='datepicker' placeholder='Fecha' value='" + obj_dato_dureza[3] + "' title='Fecha' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                            out.print("<b>Lote :</b><br />");
                            out.print("<select name='Txt_lote' id='Txt_lote'>");
                            if (lista_lotes != null) {
                                out.print("<option value='" + obj_dato_dureza[4] + "'> " + obj_dato_dureza[4] + "</option>");
                                for (int i = 0; i < lista_lotes.size(); i++) {
                                    Object[] obj_lotes = (Object[]) lista_lotes.get(i);
                                    out.print("<option value='" + obj_lotes[0] + "'>" + obj_lotes[0] + "</option>");
                                }
                            }
                            out.print("</select>" + "<script type='text/javascript'>var mySelect = new LiveValidation('Txt_lote');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Lectura 1 :</b>");
                            out.print("<input type='text' name='Txt_lectura1' id='Txt_lectura1' autofocus onfocus=\"cal(\'1\')\" oninput=\"cal(\'1\')\" value='" + obj_dato_dureza[5] + "'  placeholder='Lectura 1' title='Lectura 1' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lectura1');val1.add(Validate.Presence);</script>");
                            out.print("<b>Lectura 2 :</b>");
                            out.print("<input type='text' name='Txt_lectura2' id='Txt_lectura2' onfocus=\"cal(\'2\')\" oninput=\"cal(\'2\')\" value='" + obj_dato_dureza[6] + "'  placeholder='Lectura 2' title='Lectura 2' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lectura2');val1.add(Validate.Presence);</script>");
                            out.print("<b>Lectura 3 :</b>");
                            out.print("<input type='text' name='Txt_lectura3' id='Txt_lectura3' onfocus=\"cal(\'3\')\" oninput=\"cal(\'3\')\" value='" + obj_dato_dureza[7] + "' placeholder='Lectura 3' title='Lectura 3' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lectura3');val1.add(Validate.Presence);</script>");
                            out.print("<b>Lectura 4 :</b>");
                            out.print("<input type='text' name='Txt_lectura4' id='Txt_lectura4' onfocus=\"cal(\'4\')\" oninput=\"cal(\'4\')\" value='" + obj_dato_dureza[8] + "'  placeholder='Lectura 4' title='Lectura 4' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lectura4');val1.add(Validate.Presence);</script>");
                            out.print("<b>Promedio :</b>");
                            out.print("<input type='text' id='promedio' name='promedio' placeholder='Promedio' readonly />");
                            out.print("<b id='label_concepto' class='parpadea'>Concepto :</b><br>");
                            out.print("<input type='hidden' name='Txt_concepto' id='Txt_concepto' placeholder='Concepto' title='Concepto' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_concepto');val1.add(Validate.Presence);</script>");
                            lst_instrumentos = mtdmtl.Metrology_serials();
                            out.print("<b id='label_instrumento' >Instrumento de Medición:</b>");
                            if (lst_instrumentos != null) {
                                out.print("<select name='Txt_instrumento' id='Txt_instrumento'>");
                                out.print("<option value='0'>Seleccione Instrumento</option>");
                                for (int i = 0; i < lst_instrumentos.size(); i++) {
                                    String[] Arg_serial = lst_instrumentos.toString().replace("[", "").replace("]", "").replace(",", "").split("////");
                                    for (int j = 0; j < Arg_serial.length; j++) {
                                        String[] obj_serial = Arg_serial[i].split("---");
                                        switch (Integer.parseInt(obj_serial[11])) {
                                            case 0:
                                                out.print("<option value='0' " + ((obj_dato_dureza[14].toString().contains(obj_serial[3])) ? "selected" : "") + " style='color:red;'>" + obj_serial[3] + "</option>");
                                                break;
                                            case 1:
                                                out.print("<option value='" + obj_serial[3] + "' " + ((obj_dato_dureza[14].toString().contains(obj_serial[3])) ? "selected" : "") + " style='color:orange;'>" + obj_serial[3] + "</option>");
                                                break;
                                            case 2:
                                                out.print("<option value='" + obj_serial[3] + "' " + ((obj_dato_dureza[14].toString().contains(obj_serial[3])) ? "selected" : "") + " style='color:green;'>" + obj_serial[3] + "</option>");
                                                break;
                                            default:
                                                break;
                                        }
                                        j = Arg_serial.length;
                                    }
                                }
                                out.print("</select>" + "<script type='text/javascript'>var mySelect = new LiveValidation('Txt_instrumento');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br><br>");
                                out.print("<input type='submit' value='Modificar'>");
                            } else {
                                out.print("<p>No existe conexion</p>");
                                out.print("<input type='submit' value='Modificar'>");
                            }
                            out.print("</form>");
                            //</editor-fold>
                        }
                        out.print("</div> <!-- END of sidebar -->");
                    } else {
                        out.print("<div id='sidebar'>");
                        out.print("<h3>Registrar Dureza</h3>");
                        if (rol.equals("Coordinador_PI") || rol.equals("Jefe_PI") || rol.equals("Consulta")) {
                            //<editor-fold defaultstate="collapsed" desc="PERMISOS POR ROL">
                            out.print("<center>");
                            out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='Sin permisos' /><br />");
                            out.print("<b>Sin permisos de registro</b>");
                            out.print("</center>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                            out.print("<form action='Formula?opc=20&ifm=" + id_formula + "' onsubmit='registroD();' method='post' id='FormDureza'>");
                            out.print("<input type='hidden' id='Txt_parametro_max' value='" + obj_formula[8] + "' />");
                            out.print("<input type='hidden' id='Txt_parametro_min' value='" + obj_formula[9] + "' />");
                            out.print("<b>Fecha :</b>");
                            out.print("<input type='text' name='Txt_fecha' id='datepicker' placeholder='Fecha' value='" + ano + "/" + mes + "/" + dia + "' title='Fecha' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                            out.print("<b>Lote :</b><br />");
                            out.print("<select name='Txt_lote' id='Txt_lote'>");
                            out.print("<option value='0'>Seleccione Lote</option>");
                            if (lista_lotes != null) {
                                for (int i = 0; i < lista_lotes.size(); i++) {
                                    Object[] obj_lotes = (Object[]) lista_lotes.get(i);
                                    out.print("<option value='" + obj_lotes[0] + "'>" + obj_lotes[0] + "</option>");
                                }
                            }
                            out.print("</select>" + "<script type='text/javascript'>var mySelect = new LiveValidation('Txt_lote');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Lectura 1 :</b>");
                            out.print("<input type='text' name='Txt_lectura1' id='Txt_lectura1' onkeyup=\"cambiarCampo(event,'Txt_lectura2')\" onchange=\"cal(\'1\')\" placeholder='Lectura 1' title='Lectura 1' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lectura1');val1.add(Validate.Presence);</script>");
                            out.print("<b>Lectura 2 :</b>");
                            out.print("<input type='text' name='Txt_lectura2' id='Txt_lectura2' onkeyup=\"cambiarCampo(event,'Txt_lectura3')\" onchange=\"cal(\'2\')\" placeholder='Lectura 2' title='Lectura 2' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lectura2');val1.add(Validate.Presence);</script>");
                            out.print("<b>Lectura 3 :</b>");
                            out.print("<input type='text' name='Txt_lectura3' id='Txt_lectura3' onkeyup=\"cambiarCampo(event,'Txt_lectura4')\" onchange=\"cal(\'3\')\" placeholder='Lectura 3' title='Lectura 3' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lectura3');val1.add(Validate.Presence);</script>");
                            out.print("<b>Lectura 4 :</b>");
                            out.print("<input type='text' name='Txt_lectura4' id='Txt_lectura4' onchange=\"cal(\'4\')\" placeholder='Lectura 4' title='Lectura 4' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lectura4');val1.add(Validate.Presence);</script>");
                            out.print("<input type='hidden' name='Txt_concepto' id='Txt_concepto' placeholder='Concepto' title='Concepto' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_concepto');val1.add(Validate.Presence);</script>");
                            out.print("<b>Promedio :</b>");
                            out.print("<input type='text' id='promedio' name='promedio' placeholder='Promedio' readonly=''/>");
                            out.print("<b id='label_concepto' class='parpadea'>Concepto :</b></br>");
                            //<editor-fold defaultstate="collapsed" desc="INSTRUMENTOS">
                            lst_instrumentos = mtdmtl.Metrology_serials();
                            out.print("<b id='label_instrumento' >Instrumento de Medición:</b>");
                            if (lst_instrumentos != null) {
                                out.print("<select name='Txt_instrumento' id='Txt_instrumento'>");
                                out.print("<option value='0'>Seleccione Instrumento</option>");
                                for (int i = 0; i < lst_instrumentos.size(); i++) {
                                    String[] Arg_serial = lst_instrumentos.toString().replace("[", "").replace("]", "").replace(",", "").split("////");
                                    for (int j = 0; j < Arg_serial.length; j++) {
                                        String[] obj_serial = Arg_serial[i].split("---");
                                        switch (Integer.parseInt(obj_serial[11])) {
                                            case 0:
                                                out.print("<option value='0' style='color:red;'>" + obj_serial[3] + "</option>");
                                                break;
                                            case 1:
                                                out.print("<option value='" + obj_serial[3] + "' style='color:orange;'>" + obj_serial[3] + "</option>");
                                                break;
                                            case 2:
                                                out.print("<option value='" + obj_serial[3] + "' style='color:green;'>" + obj_serial[3] + "</option>");
                                                break;
                                            default:
                                                break;
                                        }
                                        j = Arg_serial.length;
                                    }
                                }
                                out.print("</select>" + "<script type='text/javascript'>var mySelect = new LiveValidation('Txt_instrumento');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br><br>");
                            } else {
                                out.print("<p>No existe conexion con metrologia</p>");
                            }
////</editor-fold>
                            out.print("<input type='submit' value='Registrar'>");
                            out.print("</form>");
                            //</editor-fold>
                        }
                        out.print("</div> <!-- END of sidebar -->");
                    }
                    //<editor-fold defaultstate="collapsed" desc="R-GC-065">
                    out.print("<div id='content'>");
                    out.print("<div id='NavPosicion'></div>");
                    if (lst_durezas != null) {
                        out.print("<table class='table' style='width:100%;'>");
                        out.print("<tr> <td colspan='11' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                        out.print("<tr>");
                        out.print("<td colspan='3' style='width:20%' align='center'>");
                        out.print("<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:200px;height:70px' /></td>");
                        out.print("<td colspan='5' align='center' style='width:60%'><b style='color:#292929'>REGISTRO<br/><br/><b style='color:black;'> Durezas </br></td>");
                        out.print("<td colspan='3' align='center' style='width:40%' contenteditable='false'><b style='color:#292929'>CODIGO : R-GC-065 <hr /> VERSION : 002</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        try {
                            Object[] obj_durezas = (Object[]) lst_durezas.get(0);
                            out.print("<td colspan='10'><u><b>FORMULA: " + obj_durezas[2] + "</u></td>");
                            out.print("<td align='center'><a href='Formula?opc=19&Id_dureza=" + obj_durezas[0] + "&Id_formula=" + id_formula + "'><img src='Interfaz/Contenido/Iconos/Edit.png' width='25' height='25'></a></td>");
                        } catch (Exception ex) {
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th>Fecha</th>");
                        out.print("<th colspan='2'>Lote</th>");
                        out.print("<th>Lectura 1</th>");
                        out.print("<th>Lectura 2</th>");
                        out.print("<th>Lectura 3</th>");
                        out.print("<th>Lectura 4</th>");
                        out.print("<th>Promedio</th>");
                        out.print("<th>Concepto</th>");
                        out.print("<th>Instrumento</th>");
                        out.print("<th>Responsable</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_durezas.size(); i++) {
                            Object[] obj_durezas = (Object[]) lst_durezas.get(i);
                            int concepto = Integer.parseInt(obj_durezas[9].toString());
                            if (concepto == 1) {
                                clase = "cumple";
                            } else {
                                clase = "rojo";
                            }
                            out.print("<tr class='" + clase + "'>");
                            out.print("<td align='center'>" + obj_durezas[3] + "</td>");
                            out.print("<td  colspan='2' align='center'>" + obj_durezas[4] + "</td>");
                            //CONDICIÓN QUE CARGA ICONO DE LECTURA MÁS ALTA Y LECTURA MÁS BAJA
//                            float lect1 = Float.parseFloat(obj_durezas[5].toString());
//                            float lect2 = Float.parseFloat(obj_durezas[6].toString());
//                            float lect3 = Float.parseFloat(obj_durezas[7].toString());
//                            float lect4 = Float.parseFloat(obj_durezas[8].toString());
//                            String imgMax = "<img src='Interfaz/Contenido/Iconos/mayor.png' style='width:11px;height:11px' alt='edit' title='Lectura Mayor'/>";
//                            String imgMin = "<img src='Interfaz/Contenido/Iconos/menor.png' style='width:11px;height:11px' alt='edit' title='Lectura Menor'/>";
//                            out.print("<td align='center'>" + obj_durezas[5] + "&nbsp;&nbsp;&nbsp;&nbsp;"+((lect1 > lect2 && lect1 > lect3 && lect1 > lect4 )? imgMax :((lect1 < lect2 && lect1 < lect3 && lect1 < lect4 )?imgMin:""))+"</td>");
//                            out.print("<td align='center'>" + obj_durezas[6] + "&nbsp;&nbsp;&nbsp;&nbsp;"+((lect2 > lect1 && lect2 > lect3 && lect2 > lect4 )? imgMax :((lect2 < lect1 && lect2 < lect3 && lect2 < lect4 )?imgMin:""))+"</td>");
//                            out.print("<td align='center'>" + obj_durezas[7] + "&nbsp;&nbsp;&nbsp;&nbsp;"+((lect3 > lect1 && lect3 > lect2 && lect3 > lect4 )? imgMax :((lect3 < lect1 && lect3 < lect2 && lect3 < lect4 )?imgMin:""))+"</td>");
//                            out.print("<td align='center'>" + obj_durezas[8] + "&nbsp;&nbsp;&nbsp;&nbsp;"+((lect4 > lect1 && lect4 > lect2 && lect4 > lect3 )? imgMax :((lect4 < lect1 && lect4 < lect2 && lect4 < lect3 )?imgMin:""))+"</td>");
//                            out.print("<td align='center'>" + obj_durezas[8] + "&nbsp;&nbsp;&nbsp;&nbsp;"+((lect4 > lect1 && lect4 > lect2 && lect4 > lect3 )? imgMax :((lect4 < lect1 && lect4 < lect2 && lect4 < lect3 )?imgMin:""))+"</td>");
                            String imgMax = "<img src='Interfaz/Contenido/Iconos/mayor.png' style='width:11px;height:11px' alt='edit' title='Lectura Mayor'/>";
                            String imgMin = "<img src='Interfaz/Contenido/Iconos/menor.png' style='width:11px;height:11px' alt='edit' title='Lectura Menor'/>";
                            out.print("<td align='center'>" + obj_durezas[5] + "&nbsp;&nbsp;&nbsp;&nbsp;" + ((Float.parseFloat(obj_durezas[5].toString()) == Float.parseFloat(obj_durezas[12].toString())) ? imgMax : ((Float.parseFloat(obj_durezas[5].toString()) == Float.parseFloat(obj_durezas[13].toString())) ? imgMin : "")) + "</td>");
                            out.print("<td align='center'>" + obj_durezas[6] + "&nbsp;&nbsp;&nbsp;&nbsp;" + ((Float.parseFloat(obj_durezas[6].toString()) == Float.parseFloat(obj_durezas[12].toString())) ? imgMax : ((Float.parseFloat(obj_durezas[6].toString()) == Float.parseFloat(obj_durezas[13].toString())) ? imgMin : "")) + "</td>");
                            out.print("<td align='center'>" + obj_durezas[7] + "&nbsp;&nbsp;&nbsp;&nbsp;" + ((Float.parseFloat(obj_durezas[7].toString()) == Float.parseFloat(obj_durezas[12].toString())) ? imgMax : ((Float.parseFloat(obj_durezas[7].toString()) == Float.parseFloat(obj_durezas[13].toString())) ? imgMin : "")) + "</td>");
                            out.print("<td align='center'>" + obj_durezas[8] + "&nbsp;&nbsp;&nbsp;&nbsp;" + ((Float.parseFloat(obj_durezas[8].toString()) == Float.parseFloat(obj_durezas[12].toString())) ? imgMax : ((Float.parseFloat(obj_durezas[8].toString()) == Float.parseFloat(obj_durezas[13].toString())) ? imgMin : "")) + "</td>");
                            out.print("<td align='center'>" + obj_durezas[11] + "</td>");
                            if (Integer.parseInt(obj_durezas[9].toString()) == 1) {
                                out.print("<td align='center' style='color:#006666;'><b>Cumple</b></td>");
                            } else {
                                out.print("<td align='center' >No Cumple</td>");
                            }
                            out.print("<td align='center' >" + ((obj_durezas[14] != null) ? "" + obj_durezas[14] + "" : "N/A") + "</td>");
                            out.print("<td>" + obj_durezas[10] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    } else {
                        out.print("<div style='float: left; margin: 10px;'>");
                        out.print("<a href='Formula?opc=1&Id_formula=0&fto='><img src='Interfaz/Contenido/Iconos/Volver.png' width='30px' height='30px' alt='edit' title='Volver a Formulas' /></a>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' width='126.5px' height='112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay Durezas Registradas</b>");
                        out.print("</center>");
                        out.print("</div>");
                    }
                    out.print("</div> <!-- END of Content -->");
//</editor-fold>
                }
//</editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_formula.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
