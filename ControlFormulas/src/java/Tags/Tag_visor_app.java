package Tags;

import Controladores.FormulaJpaController;
import Controladores.FormulaMateriaPrimaJpaController;
import Controladores.MateriaPrimaJpaController;
import Controladores.RegistroDetalleJpaController;
import Controladores.RegistroJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_visor_app extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="0. VARIABLES">
            //JPA´S
            FormulaJpaController jpacfml = new FormulaJpaController();
            MateriaPrimaJpaController jpacmpm = new MateriaPrimaJpaController();
            FormulaMateriaPrimaJpaController jpacfmp = new FormulaMateriaPrimaJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            RegistroDetalleJpaController jpacrdt = new RegistroDetalleJpaController();
            String lote = "";
            int id_registro = 0;
            int contador_maestra = 0;
            int contador_equivalente = 0;
            List lst_lotes = null;
            List lst_registro = null;
            List lst_registro_detalle = null;
            List lst_dureza = null;
//</editor-fold>
            if (pageContext.getRequest().getAttribute("Formula_app") != null) {
                //<editor-fold defaultstate="collapsed" desc="11. GAPP GENERACIÓN DE LOTES">
                if (pageContext.getRequest().getAttribute("Formula_app").toString().equals("Generacion_lotes_app")) {
                    lote = pageContext.getRequest().getAttribute("Lote").toString();
                    lst_lotes = jpacrgt.Traer_registro_formula_lote_app(lote);
                    //<editor-fold defaultstate="collapsed" desc="R-GC-065">

                    lst_dureza = jpacfml.Traer_durezas_lote(lote);
                    if (lst_dureza != null) {
                        out.print("<button class='accordion active'><center> DUREZAS </center></button>");
                        out.print("<div class='panel show'>");
                        out.print("<table class='table' style='width:100%;'>");
                        out.print("<tr> <td colspan='11' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                        out.print("<tr>");
                        out.print("<td align='center' style='width:30%;' colspan='3' rowspan='2'>");
                        out.print("<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:200px;height:70px' /></td>");
                        out.print("<td colspan='5' align='center' style='width:50%;'><b class='negro'>REGISTRO</b></td>");
                        out.print("<td colspan='2' align='center' style='width:20%;'><b class='negro'>CODIGO:<br> R-GC-065 </b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='5' align='center'><b class='negro'>Durezas</b></td>");
                        out.print("<td colspan='2' align='center'><b class='negro'>VERSIÓN: 002</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th style='width:75px;'>Fecha</th>");
                        out.print("<th>Lote</th>");
                        out.print("<th>Lectura 1</th>");
                        out.print("<th>Lectura 2</th>");
                        out.print("<th>Lectura 3</th>");
                        out.print("<th>Lectura 4</th>");
                        out.print("<th>Promedio</th>");
                        out.print("<th>Concepto</th>");
                        out.print("<th>Instrumento</th>");
                        out.print("<th>Responsable</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_dureza.size(); i++) {
                            Object[] obj_durezas_lote = (Object[]) lst_dureza.get(i);
//                            int concepto = Integer.parseInt(obj_durezas_lote[9].toString());
                            out.print("<tr>");
                            out.print("<td align='center'>" + obj_durezas_lote[3] + "</td>");
                            out.print("<td align='center'>" + obj_durezas_lote[4] + "</td>");
                            String imgMax = "<img src='Interfaz/Contenido/Iconos/mayor.png' style='width:11px;height:11px' alt='edit' title='Lectura Mayor'/>";
                            String imgMin = "<img src='Interfaz/Contenido/Iconos/menor.png' style='width:11px;height:11px' alt='edit' title='Lectura Menor'/>";
                            out.print("<td align='center'>" + obj_durezas_lote[5] + "&nbsp;&nbsp;&nbsp;&nbsp;" + ((Float.parseFloat(obj_durezas_lote[5].toString()) == Float.parseFloat(obj_durezas_lote[12].toString())) ? imgMax : ((Float.parseFloat(obj_durezas_lote[5].toString()) == Float.parseFloat(obj_durezas_lote[13].toString())) ? imgMin : "")) + "</td>");
                            out.print("<td align='center'>" + obj_durezas_lote[6] + "&nbsp;&nbsp;&nbsp;&nbsp;" + ((Float.parseFloat(obj_durezas_lote[6].toString()) == Float.parseFloat(obj_durezas_lote[12].toString())) ? imgMax : ((Float.parseFloat(obj_durezas_lote[6].toString()) == Float.parseFloat(obj_durezas_lote[13].toString())) ? imgMin : "")) + "</td>");
                            out.print("<td align='center'>" + obj_durezas_lote[7] + "&nbsp;&nbsp;&nbsp;&nbsp;" + ((Float.parseFloat(obj_durezas_lote[7].toString()) == Float.parseFloat(obj_durezas_lote[12].toString())) ? imgMax : ((Float.parseFloat(obj_durezas_lote[7].toString()) == Float.parseFloat(obj_durezas_lote[13].toString())) ? imgMin : "")) + "</td>");
                            out.print("<td align='center'>" + obj_durezas_lote[8] + "&nbsp;&nbsp;&nbsp;&nbsp;" + ((Float.parseFloat(obj_durezas_lote[8].toString()) == Float.parseFloat(obj_durezas_lote[12].toString())) ? imgMax : ((Float.parseFloat(obj_durezas_lote[8].toString()) == Float.parseFloat(obj_durezas_lote[13].toString())) ? imgMin : "")) + "</td>");
                            out.print("<td align='center'>" + obj_durezas_lote[11] + "</td>");
                            if (Integer.parseInt(obj_durezas_lote[9].toString()) == 1) {
                                out.print("<td align='center' style='color:#006666;'><b>Cumple</b></td>");
                            } else {
                                out.print("<td style='color:red; font-weight: bold;' align='center'>No Cumple</td>");
                            }
                            out.print("<td align='center' >" + ((obj_durezas_lote[14] != null) ? "" + obj_durezas_lote[14] + "" : "N/A") + "</td>");
                            out.print("<td>" + obj_durezas_lote[10] + "</td>");
                        }
                        out.print("</table>");
                        out.print("</div>");
                    } else {
                        out.print("<br />");
                        out.print("<center><b style='font-size:16px;' class='naranja'>Sin Registro de Durezas. </b></center>");
                    }
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="R-PI-004">

                    for (int z = 0; z < lst_lotes.size(); z++) {
                        out.print("<button class='accordion'><center>" + (z + 1) + ") " + lote + "</center></button>");
//                            out.print("<div style='background-color:#fff;width:100%;border:2px solid #006666'>");
                        out.print("<div class='panel'>");
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
                        out.print("</div>");
                        contador_maestra = 0;
                        contador_equivalente = 0;
                    }
//</editor-fold>
                }
//</editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_visor_app.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
