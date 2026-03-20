package Tags;

import Controladores.CalibradorJpaController;
import Controladores.ControlDmsCJpaController;
import Controladores.ControlDmsDJpaController;
import Controladores.CuarentenaJpaController;
import Controladores.DefectoJpaController;
import Controladores.DimensionalJpaController;
import Controladores.MaquinaJpaController;
import Controladores.OrdenJpaController;
import Controladores.PruebaFuncionalJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Metodos.Consultas_metrologia;

public class Tag_turnos extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String Usuario = sesion.getAttribute("Nombre").toString();
        int id_rol = Integer.parseInt(sesion.getAttribute("id_rol").toString());
        OrdenJpaController jpa_orden = new OrdenJpaController();
        ControlDmsCJpaController jpa_turno = new ControlDmsCJpaController();
        MaquinaJpaController jpa_maquina = new MaquinaJpaController();
        CalibradorJpaController jpa_calibrador = new CalibradorJpaController();
        DefectoJpaController jpa_defecto = new DefectoJpaController();
        ControlDmsDJpaController jpa_DimensionalT = new ControlDmsDJpaController();
        DimensionalJpaController jpa_dimensional = new DimensionalJpaController();
        CuarentenaJpaController jpa_cuarentena = new CuarentenaJpaController();
        PruebaFuncionalJpaController jpa_pruebaF = new PruebaFuncionalJpaController();
        Consultas_metrologia Jpa_metrologia = new Consultas_metrologia();
        List lst_turno = null;
        List lst_turnos = null;
        List lst_maquinas = null;
        List lst_calibradores = null;
        List lst_toma = null;
        List lst_defecto = null;
        List lst_NumCavidades = null;
        List lst_ParametrosFichaTecnica = null;
        List lst_ControlDimensional = null;
        List lst_cpk = null;
        List lst_pruebasF = null;
        List lst_pruebasFS = null;
        List lst_seguimientoT = null;
        List lst_seguimiento = null;
        List lst_parameter = null;
        List lst_CantDimensional = null;
        List lst_CantDimensionalT = null;
        List lst_CantDefecto = null;
        List lst_prbFll = null;
        List lst_cuarentena = null;
        List lst_metrologia = null;
        int suma = 0, valor = 0, cavidad = 0, count = 0;
        int Vregistro = Integer.parseInt(pageContext.getRequest().getAttribute("Vregistro").toString());
        int id_turno = Integer.parseInt(pageContext.getRequest().getAttribute("id_turno").toString());
        int id_orden = Integer.parseInt(pageContext.getRequest().getAttribute("id_orden").toString());
        String filtro = (String) pageContext.getRequest().getAttribute("filtro");
        String arg_cuarentenas = pageContext.getRequest().getAttribute("Arg_cuarentenas").toString();
        String num_cuarentena = "", estacion = "", dato = "";
        try {
            if (Vregistro == 1) {
                // <editor-fold defaultstate="collapsed" desc="REGISTO-VISTA R-GC-116 o R-GC-014 por turno">
                lst_toma = jpa_turno.consultaToma(id_turno);
                lst_turno = jpa_turno.consultaTurnoId(id_turno);
                Object[] obj_turno = (Object[]) lst_turno.get(0);
                lst_defecto = jpa_defecto.consultarDefectoTurno(id_turno);
                Object[] obj_tma = (Object[]) lst_toma.get(0);
                String datoFecha = obj_tma[11].toString().replace("-", "");
                int fechaint = Integer.parseInt(datoFecha);
                if (fechaint <= 20160220) {
                    // <editor-fold defaultstate="collapsed" desc="registro old">
                    // <editor-fold defaultstate="collapsed"  desc="Cabecera Registro.">
                    out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "<a href='#' onclick=\"tableToExcel('testTable')\">"
                            + "<img src='Interfaz/Contenido/Iconos/Excel.png' width='22' height='22' title='Exportar'>"
                            + "</a>&nbsp;<i>Exportar Excel</i>");
                    out.print("<div id='content_sin'>");
                    out.print("<table class='table' style='width:100%' id='testTable'>");
                    if (fechaint >= 20160101) {
                        out.print("<tr>");
                        out.print("<td colspan='15' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                    }
                    out.print("<tr>");
                    out.print("<td ROWSPAN='2' COLSPAN='2' style='text-align: center;'><b><img src='Interfaz/Contenido/images/Cabecera.png'></b></td>");
                    out.print("<td COLSPAN='3' style='text-align: center;'><b>CONSECUTIVO ENSAMBLE</b> <b>" + obj_tma[26] + "</b></td>");
                    if (obj_tma[9].equals("R-GC-014")) {
                        out.print("<td COLSPAN='3' style='text-align: center;'><b>R-GC-014</b></td>");
                    } else {
                        out.print("<td COLSPAN='3' style='text-align: center;'><b>R-GC-116</b></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    if (obj_tma[9].equals("R-GC-014")) {
                        out.print("<td COLSPAN='2' style='text-align: center;'><b>MANUAL DE REGISTRO</b></td>");
                        if (fechaint >= 20160101) {
                            out.print("<td COLSPAN='2' style='text-align: center;'><b>VERSIÓN 17</b></td>");
                        } else {
                            out.print("<td COLSPAN='2' style='text-align: center;'><b>VERSIÓN 15</b></td>");
                        }
                        out.print("<td COLSPAN='2' style='text-align: center;'><b>CONTROL DIMENSIONAL PUNTO DE INYECCIÓN ESTANDÁR</b></td>");
                    } else {
                        out.print("<td COLSPAN='2' style='text-align: center;'><b>MOLDE " + obj_tma[25] + "</b></td>");
                        if (fechaint >= 20160101) {
                            out.print("<td COLSPAN='2' style='text-align: center;'><b>VERSIÓN 3</b></td>");
                        } else {
                            out.print("<td COLSPAN='2' style='text-align: center;'><b>VERSIÓN 2</b></td>");
                        }
                        out.print("<td COLSPAN='2' style='text-align: center;'><b>CONTROL DIMENSIONAL PUNTO DE INYECCIÓN USA</b></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr >");
                    out.print("<td COLSPAN='2' style='text-align: center; height: 30px;'><b>FECHA</b></td>");
                    out.print("<td>" + obj_tma[11] + "</td>");
                    out.print("<td COLSPAN='2' style='text-align: center;'><b>SERIAL(ES)</b></td>");
                    out.print("<td>" + obj_tma[28] + "</td>");
                    out.print("<td  style='text-align: center;'><b>MÁQUINA</b></td>");
                    out.print("<td>" + obj_tma[29] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td COLSPAN='2' style='text-align: center; height: 30px;'><b>LOTE ENSAMBLE</b></td>");
                    out.print("<td>" + obj_tma[19] + "</td>");
                    out.print("<td COLSPAN='2' style='text-align: center;'><b>LOTE BASE C</b></td>");
                    out.print("<td>" + obj_tma[15] + "</td>");
                    out.print("<td style='text-align: center;'><b>LOTE BASE P</b></td>");
                    out.print("<td>" + obj_tma[16] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td COLSPAN='2' style='text-align: center; height: 30px;'><b> ORDEN DE PRODUCCIÓN</b></td>");
                    out.print("<td>" + obj_tma[14] + "</td>");
                    out.print("<td COLSPAN='2' style='text-align: center;'><b>LOTE PISTÓN C</b></td>");
                    out.print("<td>" + obj_tma[17] + "</td>");
                    out.print("<td style='text-align: center;'><b>LOTE PISTÓN P</b></td>");
                    out.print("<td>" + obj_tma[18] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td COLSPAN='8'></td>");
                    out.print("</tr>");
                    // </editor-fold>

                    // <editor-fold defaultstate="collapsed"  desc="Detalle Registro.">
                    out.print("<tr>");
                    out.print("<td COLSPAN='2' style='text-align: center;'><b>FECHA/HORA TOMA</b></td>");
                    out.print("<td style='text-align: center;'><b>ESTACIÓN</b></td>");
                    out.print("<td style='text-align: center;'><b>ALTURA PISTÓN<br />" + obj_tma[22] + "</b></td>");
                    out.print("<td style='text-align: center;'><b>DIAMETRO PISTÓN<br />" + obj_tma[21] + "</b></td>");
                    out.print("<td style='text-align: center;'><b>LONGITUD A INTRODUCIR<br />" + obj_tma[20] + "</b></td>");
                    out.print("<td style='text-align: center;'><b>Ø INTERNO CONFORMADO<br />" + obj_tma[23] + "</b></td>");
                    out.print("<td style='text-align: center;'><b>Ø CONEXIÓN<br />" + obj_tma[24] + "</b></td>");
                    out.print("</tr>");
                    int est1 = 0;
                    int est2 = 0;
                    int est3 = 0;
                    int est4 = 0;
                    int est5 = 0;
                    int est6 = 0;
                    int est7 = 0;
                    int est8 = 0;
                    int conteo_estacion1 = 0;
                    int conteo_estacion2 = 0;
                    int conteo_estacion3 = 0;
                    int conteo_estacion4 = 0;
                    int conteo_estacion5 = 0;
                    int conteo_estacion6 = 0;
                    int conteo_estacion7 = 0;
                    int conteo_estacion8 = 0;
                    for (int i = 0; i < lst_toma.size(); i++) {
                        Object[] obj_toma = (Object[]) lst_toma.get(i);
                        if (obj_toma[2].equals("E1")) {
                            est1++;
                        } else if (obj_toma[2].equals("E2")) {
                            est2++;
                        } else if (obj_toma[2].equals("E3")) {
                            est3++;
                        } else if (obj_toma[2].equals("E4")) {
                            est4++;
                        } else if (obj_toma[2].equals("E5")) {
                            est5++;
                        } else if (obj_toma[2].equals("E6")) {
                            est6++;
                        } else if (obj_toma[2].equals("E7")) {
                            est7++;
                        } else if (obj_toma[2].equals("E8")) {
                            est8++;
                        }
                    }
                    for (int i = 0; i < lst_toma.size(); i++) {
                        Object[] obj_toma = (Object[]) lst_toma.get(i);
                        out.print("<tr>");
                        out.print("<td style='text-align: center;'>" + obj_toma[1] + "</td>");
                        if (!(conteo_estacion1 > 0)) {
                            if (obj_toma[2].equals("E1")) {
                                out.print("<td ROWSPAN='" + est1 + "' style='text-align: center;'><b>E1</b></td>");
                                conteo_estacion1++;
                            }
                        }
                        if (!(conteo_estacion2 > 0)) {
                            if (obj_toma[2].equals("E2")) {
                                out.print("<td ROWSPAN='" + est2 + "' style='text-align: center;'><b>E2</b></td>");
                                conteo_estacion2++;
                            }
                        }
                        if (!(conteo_estacion3 > 0)) {
                            if (obj_toma[2].equals("E3")) {
                                out.print("<td ROWSPAN='" + est3 + "' style='text-align: center;'><b>E3</b></td>");
                                conteo_estacion3++;
                            }
                        }
                        if (!(conteo_estacion4 > 0)) {
                            if (obj_toma[2].equals("E4")) {
                                out.print("<td ROWSPAN='" + est4 + "' style='text-align: center;'><b>E4</b></td>");
                                conteo_estacion4++;
                            }
                        }
                        if (!(conteo_estacion5 > 0)) {
                            if (obj_toma[2].equals("E5")) {
                                out.print("<td ROWSPAN='" + est5 + "' style='text-align: center;'><b>E5</b></td>");
                                conteo_estacion5++;
                            }
                        }
                        if (!(conteo_estacion6 > 0)) {
                            if (obj_toma[2].equals("E6")) {
                                out.print("<td ROWSPAN='" + est6 + "' style='text-align: center;'><b>E6</b></td>");
                                conteo_estacion6++;
                            }
                        }
                        if (!(conteo_estacion7 > 0)) {
                            if (obj_toma[2].equals("E7")) {
                                out.print("<td ROWSPAN='" + est7 + "' style='text-align: center;'><b>E7</b></td>");
                                conteo_estacion7++;
                            }
                        }
                        if (!(conteo_estacion8 > 0)) {
                            if (obj_toma[2].equals("E8")) {
                                out.print("<td ROWSPAN='" + est8 + "' style='text-align: center;'><b>E8</b></td>");
                                conteo_estacion8++;
                            }
                        }
                        out.print("<td style='text-align: center;'>" + obj_toma[3] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_toma[4] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_toma[5] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_toma[6] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_toma[7] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_toma[8] + "</td>");
                        out.print("</tr>");

                    }
                    out.print("<tr>");
                    out.print("<td COLSPAN='2' style='text-align: center; height: 30px;'><b>TURNO</b></td>");
                    out.print("<td>" + obj_tma[12] + "</td>");
                    out.print("<td COLSPAN='2' style='text-align: center;'><b>RESPONSABLE</b></td>");
                    out.print("<td>" + obj_tma[30] + "</td>");
                    out.print("<td style='text-align: center;'><b>CUARENTENA</b></td>");
                    if (obj_tma[27].equals("aprobado")) {
                        out.print("<td>APROBADA</td>");
                    } else {
                        out.print("<td>" + obj_tma[27] + "</td>");
                    }
                    out.print("</tr>");
                    out.print("</table>");
                    // </editor-fold>

                    // <editor-fold defaultstate="collapsed"  desc="Defectos Fisicos.">
                    out.print("<br/>");
                    out.print("<table class='table' style='width:100%'>");
                    out.print("<tr>");
                    for (int i = 0; i < lst_defecto.size(); i++) {
                        Object[] obj_dfc = (Object[]) lst_defecto.get(i);
                        out.print("<td style='text-align: center;'><b>" + obj_dfc[4] + "</b></td>");
                        if (i == lst_defecto.size() - 1) {
                            out.print("<td style='text-align: center;'><b>TOTAL RECHAZO</b></td>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    for (int i = 0; i < lst_defecto.size(); i++) {
                        Object[] obj_dfc = (Object[]) lst_defecto.get(i);
                        out.print("<td style='text-align: center;'>");
                        if (obj_dfc[5] != null) {
                            out.print("<b>" + obj_dfc[5] + "</b>");
                            suma = suma + Integer.parseInt(obj_dfc[5].toString());
                        } else {
                            out.print("");
                        }
                        out.print("</td>");
                        if (i == lst_defecto.size() - 1) {
                            out.print("<td style='text-align: center;'><b>" + suma + "</b></td>");
                        }
                        out.print("</form>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('validateCan" + i + "');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script>");
                    }
                    out.print("</tr>");

                    out.print("</table>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                    // </editor-fold>

                    // </editor-fold>
                } else {
                    // <editor-fold defaultstate="collapsed" desc="registro new">
                    // <editor-fold defaultstate="collapsed"  desc="Cabecera Registro.">
                    out.print("<a href='Turno?opc=1&idO=" + id_orden + "&idT=0&registro=0&Sr=0&txt_bus='><img src=\"Interfaz/Contenido/Iconos/Volver.png\" alt=\"Logo\" width=\"22\" height=\"22\" title=\"Volver\"></a>");
                    out.print("&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "<span class='fas fa-file-excel fa-size_small' onclick=\"tableToExcel('testTable')\">"
                            + "</span>&nbsp;<i>Exportar Excel</i>");
                    out.print("<div style='float:right'><span class='fas fa-print fa-size_small' onclick='Imprimir();' title='Imprimir' /></span> Imprimir o PDF</div>");
                    out.print("<div style='float:right'><span class='fas fa-print fa-size_small' onclick='Imprimir();' title='Imprimir' /></span> Imprimir o PDF</div>");
//                    out.print("<div style='float:right'><a onclick='Imprimir();'><img src='Interfaz/Contenido/Iconos/Printer.png' style='width: 25px;height: 25px' alt='' title='Imprimir' /></a> Imprimir o PDF</div>");
                    out.print("<div id='content_sin'>");
                    out.print("<div id='Imprimir'>");
                    out.print("<table class='table' style='width:100%' id='testTable'>");
                    if (obj_turno[25].equals("R-GC-014")) {
                        // <editor-fold defaultstate="collapsed" desc="R-gc-014">
                        out.print("<tr>");
                        out.print("<td colspan='12' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td ROWSPAN='2' COLSPAN='4' align='center'><b><img src='Interfaz/Contenido/images/Cabecera.png' style='width:60%;'></b></td>");
                        out.print("<td align='center' colspan='4'><b>REGISTRO</b></td>");
                        if ((Integer) obj_tma[10] < 9087) {
                            out.print("<td align='center' rowspan='2' colspan='4'><b>Codigo: </b><br /><b style='color:#000;'>R-GC-014 VERSIÓN 18</b></td>");
                        } else {
                            out.print("<td align='center' rowspan='2' colspan='4'><b>Codigo: </b><br /><b style='color:#000;'>R-GC-014 VERSIÓN 19</b></td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='4'><b>CONTROL DIMENSIONAL PUNTO DE INYECCIÓN ESTANDÁR</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th colspan='12' style='background-color:#00838f;'>DETALLE DEL REGISTRO</th>");
                        out.print("</tr>");
                        out.print("<td align='center' colspan='4'><b>CONSECUTIVO ENSAMBLE: </b>" + obj_tma[26] + "</td>");
                        out.print("<td align='center' colspan='2'><b>FECHA: </b>" + obj_tma[11] + "</td>");
                        out.print("<td align='center' colspan='2'><b>SERIAL(ES): </b>" + obj_tma[28] + "</td>");
                        out.print("<td align='center' colspan='4'><b>MÁQUINA: </b>" + obj_tma[29] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='4' rowspan='2'><b>LOTE ENSAMBLE: </b>" + obj_tma[19] + "</td>");
                        out.print("<td align='center' colspan='2'><b>LOTE BASE C: </b>" + obj_tma[15] + "</td>");
                        out.print("<td align='center' colspan='2'><b>LOTE PISTÓN C: </b>" + obj_tma[17] + "</td>");
//                        out.print("<td align='center' colspan='4' rowspan='2'><b> ORDEN DE PRODUCCIÓN: </b>" + obj_tma[14] + "</td>");
                        out.print("<td align='center' colspan='4'><b> ORDEN DE PRODUCCIÓN: </b>" + obj_tma[14] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='2'><b>LOTE BASE P: </b>" + obj_tma[16] + "</td>");
                        out.print("<td align='center' colspan='2'><b>LOTE PISTÓN P: </b>" + obj_tma[18] + "</td>");
                        out.print("<td align='center' colspan='4'><b>Prueba de estaqueidad: </b>Cumple</td>");
                        out.print("</tr>");
                        // </editor-fold>
                    } else {
                        // <editor-fold defaultstate="collapsed" desc="R-GC-116">
                        out.print("<tr>");
                        out.print("<td colspan='12' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td ROWSPAN='2' COLSPAN='4' align='center'><b><img src='Interfaz/Contenido/images/Cabecera.png' style='width:60%;'></b></td>");
                        out.print("<td align='center' colspan='4'><b>REGISTRO</b></td>");
                        out.print("<td align='center' rowspan='2' colspan='4'><b>Codigo: </b><br /><b style='color:#000;'>R-GC-116 VERSIÓN 4</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='4'><b>CONTROL DIMENSIONAL PUNTO DE INYECCIÓN USA</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th colspan='12' style='background-color:#00838f;'>DETALLE DEL REGISTRO</th>");
                        out.print("</tr>");
                        out.print("<td align='center' colspan='4'><b>CONSECUTIVO ENSAMBLE: </b>" + obj_tma[26] + "</td>");
                        out.print("<td align='center' colspan='2'><b>FECHA: </b>" + obj_tma[11] + "</td>");
                        out.print("<td align='center' colspan='2'><b>SERIAL(ES): </b>" + obj_tma[28] + "</td>");
                        out.print("<td align='center' colspan='2'><b>MÁQUINA: </b>" + obj_tma[29] + "</td>");
                        out.print("<td align='center' colspan='2'><b>MOLDE: </b> " + obj_tma[25] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='4' rowspan='2'><b>LOTE ENSAMBLE: </b>" + obj_tma[19] + "</td>");
                        out.print("<td align='center' colspan='2'><b>LOTE BASE C: </b>" + obj_tma[15] + "</td>");
                        out.print("<td align='center' colspan='2'><b>LOTE PISTÓN C: </b>" + obj_tma[17] + "</td>");
//                        out.print("<td align='center' colspan='4' rowspan='2'><b> ORDEN DE PRODUCCIÓN: </b>" + obj_tma[14] + "</td>");
                        out.print("<td align='center' colspan='4'><b> ORDEN DE PRODUCCIÓN: </b>" + obj_tma[14] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='2'><b>LOTE BASE P: </b>" + obj_tma[16] + "</td>");
                        out.print("<td align='center' colspan='2'><b>LOTE PISTÓN P: </b>" + obj_tma[18] + "</td>");
                        out.print("<td align='center' colspan='4'><b>Prueba de estaqueidad: </b>Cumple</td>");
                        out.print("</tr>");
                        // </editor-fold>
                    }
// </editor-fold>
                    // <editor-fold defaultstate="collapsed"  desc="CONTROL DIMENSIONAL">
                    out.print("<tr>");
                    out.print("<th colspan='12' style='background-color:#00838f;'>CONTROL DIMENSIONAL</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td COLSPAN='4' style='text-align: center;'><b>FECHA/HORA TOMA</b></td>");
                    out.print("<td style='text-align: center;'><b>ESTACIÓN</b></td>");
                    out.print("<td style='text-align: center;'><b>ALTURA PORTAPISTÓN<br />" + obj_tma[22] + "</b></td>");
                    out.print("<td style='text-align: center;'><b>DIAMETRO EXTERIOR<br />" + obj_tma[21] + "</b></td>");
                    out.print("<td style='text-align: center;'><b>LONGITUD A INTRODUCIR<br />" + obj_tma[20] + "</b></td>");
                    out.print("<td style='text-align: center;' COLSPAN='2'><b>DIAMETRO DE CONFORMADO<br />" + obj_tma[23] + "</b></td>");
                    out.print("<td style='text-align: center;' COLSPAN='2'><b>DIAMETRO MAXIMO DE CONEXIÓN<br />" + obj_tma[24] + "</b></td>");
                    out.print("</tr>");
                    int est1 = 0;
                    int est2 = 0;
                    int est3 = 0;
                    int est4 = 0;
                    int est5 = 0;
                    int est6 = 0;
                    int est7 = 0;
                    int est8 = 0;
                    int conteo_estacion1 = 0;
                    int conteo_estacion2 = 0;
                    int conteo_estacion3 = 0;
                    int conteo_estacion4 = 0;
                    int conteo_estacion5 = 0;
                    int conteo_estacion6 = 0;
                    int conteo_estacion7 = 0;
                    int conteo_estacion8 = 0;
                    for (int i = 0; i < lst_toma.size(); i++) {
                        Object[] obj_toma = (Object[]) lst_toma.get(i);
                        if (obj_toma[2].equals("E1")) {
                            est1++;
                        } else if (obj_toma[2].equals("E2")) {
                            est2++;
                        } else if (obj_toma[2].equals("E3")) {
                            est3++;
                        } else if (obj_toma[2].equals("E4")) {
                            est4++;
                        } else if (obj_toma[2].equals("E5")) {
                            est5++;
                        } else if (obj_toma[2].equals("E6")) {
                            est6++;
                        } else if (obj_toma[2].equals("E7")) {
                            est7++;
                        } else if (obj_toma[2].equals("E8")) {
                            est8++;
                        }
                    }
                    for (int i = 0; i < lst_toma.size(); i++) {
                        Object[] obj_toma = (Object[]) lst_toma.get(i);
                        out.print("<tr>");
                        out.print("<td style='text-align: center;' colspan='3'>" + obj_toma[1] + "</td>");
                        if (!(conteo_estacion1 > 0)) {
                            if (obj_toma[2].equals("E1")) {
                                out.print("<td ROWSPAN='" + est1 + "'  style='text-align: center;'><b>E1</b></td>");
                                conteo_estacion1++;
                            }
                        }
                        if (!(conteo_estacion2 > 0)) {
                            if (obj_toma[2].equals("E2")) {
                                out.print("<td ROWSPAN='" + est2 + "'  style='text-align: center;'><b>E2</b></td>");
                                conteo_estacion2++;
                            }
                        }
                        if (!(conteo_estacion3 > 0)) {
                            if (obj_toma[2].equals("E3")) {
                                out.print("<td ROWSPAN='" + est3 + "'  style='text-align: center;'><b>E3</b></td>");
                                conteo_estacion3++;
                            }
                        }
                        if (!(conteo_estacion4 > 0)) {
                            if (obj_toma[2].equals("E4")) {
                                out.print("<td ROWSPAN='" + est4 + "'  style='text-align: center;'><b>E4</b></td>");
                                conteo_estacion4++;
                            }
                        }
                        if (!(conteo_estacion5 > 0)) {
                            if (obj_toma[2].equals("E5")) {
                                out.print("<td ROWSPAN='" + est5 + "'  style='text-align: center;'><b>E5</b></td>");
                                conteo_estacion5++;
                            }
                        }
                        if (!(conteo_estacion6 > 0)) {
                            if (obj_toma[2].equals("E6")) {
                                out.print("<td ROWSPAN='" + est6 + "'  style='text-align: center;'><b>E6</b></td>");
                                conteo_estacion6++;
                            }
                        }
                        if (!(conteo_estacion7 > 0)) {
                            if (obj_toma[2].equals("E7")) {
                                out.print("<td ROWSPAN='" + est7 + "'  style='text-align: center;'><b>E7</b></td>");
                                conteo_estacion7++;
                            }
                        }
                        if (!(conteo_estacion8 > 0)) {
                            if (obj_toma[2].equals("E8")) {
                                out.print("<td ROWSPAN='" + est8 + "'  style='text-align: center;'><b>E8</b></td>");
                                conteo_estacion8++;
                            }
                        }
                        out.print("<td style='text-align: center;'>" + obj_toma[3] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_toma[4] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_toma[5] + "</td>");
                        out.print("<td style='text-align: center;'>" + obj_toma[6] + "</td>");
                        out.print("<td style='text-align: center;' colspan='2'>" + obj_toma[7] + "</td>");
                        out.print("<td style='text-align: center;' colspan='2'>" + obj_toma[8] + "</td>");
                        out.print("</tr>");

                    }
                    out.print("<tr>");
                    out.print("<td COLSPAN='4' style='text-align: center; height: 30px;'><b>TURNO: </b>" + obj_tma[12] + "</td>");
                    out.print("<td COLSPAN='4' style='text-align: center;'><b>RESPONSABLE: </b>" + obj_tma[30] + "</td>");
                    out.print("<td align='center' colspan='4'><b>CUARENTENA: </b>");
                    if (obj_tma[27].equals("aprobado")) {
                        out.print("APROBADA</td>");
                    } else {
                        out.print("" + obj_tma[27] + "</td>");
                    }
                    out.print("</tr>");
                    // </editor-fold>
                    // <editor-fold defaultstate="collapsed"  desc="Defectos Fisicos.">
                    out.print("<tr>");
                    out.print("<td colspan='12'></td>");
                    out.print("</tr>");
                    out.print("<tr>");

                    for (int i = 0; i < lst_defecto.size(); i++) {
                        Object[] obj_dfc = (Object[]) lst_defecto.get(i);
                        out.print("<td style='text-align: center;'><b>" + obj_dfc[4] + "</b></td>");
                        if (i == lst_defecto.size() - 1) {
                            out.print("<td style='text-align: center;'><b>TOTAL RECHAZO</b></td>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    for (int i = 0; i < lst_defecto.size(); i++) {
                        Object[] obj_dfc = (Object[]) lst_defecto.get(i);
                        out.print("<td style='text-align: center;'>");
                        if (obj_dfc[5] != null) {
                            out.print("<b>" + obj_dfc[5] + "</b>");
                            suma = suma + Integer.parseInt(obj_dfc[5].toString());
                        } else {
                            out.print("");
                        }
                        out.print("</td>");
                        if (i == lst_defecto.size() - 1) {
                            out.print("<td style='text-align: center;'><b>" + suma + "</b></td>");
                        }
                        out.print("</form>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('validateCan" + i + "');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script>");
                    }
                    out.print("</tr>");

                    out.print("</table>");
                    out.print("</div>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                    // </editor-fold>
                    // </editor-fold>
                }
                // </editor-fold>
            } else if (Vregistro == 2) {
                //<editor-fold defaultstate="collapsed" desc="VISTA R-GC-116 Y R-GC-014">
                lst_turno = jpa_turno.consultaTurnoId(id_turno);
                Object[] obj_turno = (Object[]) lst_turno.get(0);
                lst_defecto = jpa_defecto.consultarDefectoTurno(id_turno);
                lst_NumCavidades = jpa_DimensionalT.ConsultaNumCavidadesPorEstacion(id_turno);
                num_cuarentena = obj_turno[5] + "-" + obj_turno[2].toString().replace("-", "") + "-" + obj_turno[3] + "-" + obj_turno[27];
                // <editor-fold defaultstate="collapsed"  desc="Cabecera Registro.">
                out.print("<a href='Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=" + 0 + "&registro=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' width='22' height='22' title='Volver'></a>");
                lst_CantDimensionalT = jpa_DimensionalT.ConsultaCantidadTotalDimensional((Integer) obj_turno[0]);
                for (int j = 0; j < lst_CantDimensionalT.size(); j++) {
                    Object[] num = (Object[]) lst_CantDimensionalT.get(j);
                    count = count + Integer.parseInt(num[1].toString());
                }
                if (count == 72 || count == 96) {
                    if (!obj_turno[28].equals("rechazado")) {
                        out.print("<div style='float:right;'><span class='fa fa-award fa-size_small' onclick='mostrarEmergente(1)' title='Definir Estado'></span></div>");
                        //<editor-fold defaultstate="collapsed" desc="ESTADO RECHAZADO/CUARENTENA">
                        out.print("<div class='sweet-local' id='Convecion1' tabindex='-1' style='opacity: 1.03;  display:none;'>");
                        out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:18%; height:50%; position: absolute;top:25%; left:70%; text-align:left '>");
                        out.print("<a href='Turno?opc=1&idO=" + id_orden + "&idT=" + obj_turno[0] + "&registro=" + 2 + "&txt_bus=" + 0 + "'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio'  style='margin-left:94%;'/></a>");
                        out.print("<legend>Definir Estado</legend>");
                        out.print("<form method='post' action='Turno?opc=19';'>");
                        out.print("<b class='naranja'>Cuarentena</b>");
                        out.print("<input type='radio' id='cuarentena' name='txt_estado' value='" + num_cuarentena + "' required checkend >");
                        out.print("<b> | </b>");
                        out.print("<b class='rojo'> Rechazado</b>");
                        out.print("<input type='radio' id='rechazado' name='txt_estado' value='rechazado' required checkend>");
                        out.print("<input type='hidden' name='idO' value='" + id_orden + "' />");
                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "' />");
                        out.print("<br><br><br><b>Justificación:</b><br/>");
                        out.print("<textarea rows='6'  name='txt_justificacion'  placeholder='Justificación' style='width:95%; height:45%' required></textarea>");
                        out.print("<center><input type='submit' name='Btn_construir' id='Btn_construir' value='Definir'/></center>");
                        out.print("</form>");
                        out.print("</fieldset></div>");
                        //</editor-fold> 
                    } else if (obj_turno[18].equals("cerrado") || Integer.parseInt(obj_turno[19].toString()) == 1) {
                        out.print("<div style='float:right;'><span class='fa fa-award fa-size_small' onclick='mostrarEmergente(1)' title='Definir Estado'></span></div>");
                        //<editor-fold defaultstate="collapsed" desc="ESTADO RECHAZADO/CUARENTENA">
                        out.print("<div class='sweet-local' id='Convecion1' tabindex='-1' style='opacity: 1.03;  display:none;'>");
                        out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:18%; height:50%; position: absolute;top:25%; left:70%; text-align:left '>");
                        out.print("<a href='Turno?opc=1&idO=" + id_orden + "&idT=" + obj_turno[0] + "&registro=" + 2 + "&txt_bus=" + 0 + "'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Volver al inicio'  style='margin-left:94%;'/></a>");
                        out.print("<legend>Definir Estado</legend>");
                        out.print("<form method='post' action='Turno?opc=19';'>");
                        out.print("<b class='naranja'>Cuarentena</b>");
                        out.print("<input type='radio' id='cuarentena' name='txt_estado' value='" + num_cuarentena + "' required checkend >");
                        out.print("<b> | </b>");
                        out.print("<b class='rojo'> Rechazado</b>");
                        out.print("<input type='radio' id='rechazado' name='txt_estado' value='rechazado' required checkend>");
                        out.print("<input type='hidden' name='idO' value='" + id_orden + "' />");
                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "' />");
                        out.print("<br><br><br><b>Justificación:</b><br/>");
                        out.print("<textarea rows='6'  name='txt_justificacion'  placeholder='Justificación' style='width:95%; height:45%' required></textarea>");
                        out.print("<center><input type='submit' name='Btn_construir' id='Btn_construir' value='Definir'/></center>");
                        out.print("</form>");
                        out.print("</fieldset></div>");
                        //</editor-fold>  
                    }
                }
                out.print("<table class='table' style='width:100%;' id='testTable'>");
                String datoFecha = obj_turno[2].toString().replace("-", "");
                int fechaint = Integer.parseInt(datoFecha);
                if (fechaint >= 20160101) {
                    out.print("<tr>");
                    out.print("<td colspan='15' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                }
                if (obj_turno[25].equals("R-GC-014")) {
                    out.print("<tr>");
                    out.print("<td align='center' rowspan='2' style='width:24%;' ><b><img src='Interfaz/Contenido/images/Cabecera.png' style='width:60%;'></b></td>");
                    out.print("<td align='center' colspan='4'><b style='color:#000;'>Registro</b></td>");
                    out.print("<td align='center' style='width:24%;'><b>Codigo: </b><b style='color:#000;'>R-GC-014</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='4'><b style='color:#000;'>CONTROL DIMENSIONAL PUNTO DE INYECCIÓN ESTANDÁR</b></td>");
                    if ((Integer) obj_turno[0] >= 9087) {
                        out.print("<td align='center'><b>VERSIÓN: </b><b style='color:#000;'>19</b></td>");
                    } else if (fechaint >= 20160318) {
                        out.print("<td align='center'><b>VERSIÓN: </b><b style='color:#000;'>18</b></td>");
                    } else if (fechaint >= 20160101) {
                        out.print("<td align='center'><b>VERSIÓN: </b><b style='color:#000;'>17</b></td>");
                    } else {
                        out.print("<td align='center'><b>VERSIÓN: </b><b style='color:#000;'> 15</b></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr >");
                    out.print("<td align='center' rowspan='2'><b>CONSECUTIVO ENSAMBLE: </b>" + obj_turno[27] + "</td>");
                    out.print("<td align='center'><b>FECHA: </b>" + obj_turno[2] + "</td>");
                    out.print("<td align='center' colspan='2'><b>RESPONSABLE: </b>" + obj_turno[24] + "</td>");
                    out.print("<td align='center'><b>TURNO: </b>" + obj_turno[3] + "</td>");
                    out.print("<td align='center' rowspan='2'><b>MÁQUINA: </b>" + obj_turno[22] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    if (obj_turno[20] == null) {
                        out.print("<td align='center' colspan='2'><b>SERIAL(ES): </b>No se ha seleccionado ningun serial</td>");
                    } else {
                        out.print("<td align='center' colspan='2'><b>SERIAL(ES): </b>" + obj_turno[20] + "</td>");
                    }
                    out.print("<td align='center' colspan='2'><b> ORDEN DE PRODUCCIÓN: </b>" + obj_turno[5] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>LOTE ENSAMBLE: </b>" + obj_turno[10] + "</td>");
                    out.print("<td align='center' colspan='2'><b>LOTE BASE C: </b>" + obj_turno[6] + "<hr /><b>LOTE BASE P:</b>" + obj_turno[7] + "</td>");
                    out.print("<td align='center' colspan='2'><b>LOTE PISTÓN C: </b>" + obj_turno[8] + "<hr /><b>LOTE PISTÓN P: </b> " + obj_turno[9] + "</td>");
                    if (obj_turno[28].equals("seguimiento")) {
                        out.print("<td align='center'><b>COD. CUARENTENA: </b>&nbsp; <b class='azul'>EN SEGUIMIENTO</b>");
                    } else {
                        out.print("<td align='center'><b>COD. CUARENTENA: </b>");
                        if (!obj_turno[28].equals("aprobado")) {
                            out.print("" + num_cuarentena + "");
                        } else {
                            out.print("N/A");
                        }
                    }
                    if (Integer.parseInt(obj_turno[0].toString()) <= 58284) {
                        out.print("<hr><b>Pru. Estaqueidad: </b><b class='verde'>Cumple</b></b></td>");
                    } else if (obj_turno[43] != null) {
                        out.print("<hr /><b>Pru. Estaqueidad: </b>" + (((Integer) obj_turno[44] == 1) ? "<b class='" + ((obj_turno[47] != null) ? "naranja" : "verde") + "'>Cumple</b>" : ((Integer) obj_turno[44] == 0) ? "<b class='" + ((obj_turno[47] != null) ? "rojo" : "naranja") + "'>No se ha registrado</b>" : "<b class='rojo'>No cumple</b>") + "</td>");
                    } else {
                        out.print("<hr /><b>Pru. Estaqueidad: </b><b class='naranja'>No se ha registrado</b></td>");
                    }
                    out.print("</tr>");
                } else {
                    out.print("<tr>");
                    out.print("<td align='center' rowspan='2' style='width:24%;' ><b><img src='Interfaz/Contenido/images/Cabecera.png' style='width:60%;'></b></td>");
                    out.print("<td align='center' colspan='4'><b style='color:#000;'>Registro</b></td>");
                    out.print("<td align='center' style='width:24%;'><b>Codigo: </b><b style='color:#000;'>R-GC-116</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='4'><b>CONTROL DIMENSIONAL PUNTO DE INYECCIÓN USA</b></td>");
                    if (fechaint >= 20160318) {
                        out.print("<td align='center'><b>VERSIÓN: </b><b style='color:#000;'>4</b></td>");
                    } else if (fechaint >= 20160101) {
                        out.print("<td align='center'><b>VERSIÓN: </b><b style='color:#000;'>2</b></td>");
                    } else {
                        out.print("<td align='center'><b>VERSIÓN: </b><b style='color:#000;'>2</b></td>");
                    }
                    out.print("</tr>");
                    List lst_turnosE = jpa_turno.consultaTurnosEstadoSeguimiento((Integer) obj_turno[0]);
                    Object[] obj_turnoR = (Object[]) lst_turnosE.get(0);
                    out.print("<tr >");
                    if ((Integer) obj_turno[53] == 1) {
                        out.print("<td align='center' rowspan='2'><b>CONSECUTIVO ENSAMBLE: </b>" + obj_turnoR[4] + "</td>");
                    } else {
                        out.print("<td align='center' rowspan='2'><b>CONSECUTIVO ENSAMBLE: </b>" + obj_turno[27] + "</td>");
                    }
                    out.print("<td align='center'><b>FECHA: </b>" + obj_turno[2] + "</td>");
                    out.print("<td align='center' colspan='2'><b>RESPONSABLE: </b>" + obj_turno[24] + "</td>");
                    out.print("<td align='center'><b>TURNO: </b>" + obj_turno[3] + "</td>");
                    out.print("<td align='center' rowspan='2'><b>MÁQUINA: </b>" + obj_turno[22] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    if (obj_turno[20] == null) {
                        out.print("<td align='center'><b>SERIAL(ES): </b>No se ha seleccionado ningun serial</td>");
                    } else {
                        out.print("<td align='center'><b>SERIAL(ES): </b>" + obj_turno[20] + "</td>");
                    }
                    out.print("<td align='center' colspan='2'><b>MOLDE: </b>" + obj_turno[26] + "</td>");
                    out.print("<td align='center'><b> ORDEN DE PRODUCCIÓN: </b>" + obj_turno[5] + "</td>");
                    out.print("<tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>LOTE ENSAMBLE: </b>" + obj_turno[10] + "</td>");
                    out.print("<td align='center' colspan='2'><b>LOTE BASE C: </b>" + obj_turno[6] + "<hr /><b>LOTE BASE P:</b>" + obj_turno[7] + "</td>");
                    out.print("<td align='center' colspan='2'><b>LOTE PISTÓN C: </b>" + obj_turno[8] + "<hr /><b>LOTE PISTÓN P: </b> " + obj_turno[9] + "</td>");
                    if (obj_turno[28].equals("seguimiento")) {
                        out.print("<td align='center'><b>COD. CUARENTENA: </b>&nbsp; <b class='azul'>EN SEGUIMIENTO</b>");
                    } else {
                        out.print("<td align='center'><b>COD. CUARENTENA: </b>");
                        if (!obj_turno[28].equals("aprobado")) {
                            out.print("" + num_cuarentena + "");
                        } else {
                            out.print("N/A");
                        }
                    }
                    if (Integer.parseInt(obj_turno[0].toString()) == 58284) {
                        out.print("<hr /><b>Pru. Estaqueidad: </b><b class='verde'>Cumple</b></td>");
                    } else if (obj_turno[43] != null) {
                        out.print("<hr /><b>Pru. Estaqueidad: </b>" + (((Integer) obj_turno[44] == 1) ? "<b class='" + ((obj_turno[47] != null) ? "naranja" : "verde") + "'>Cumple</b>" : "<b class='rojo'>No cumple</b>") + "</td>");
                    } else {
                        out.print("<hr /><b>Pru. Estaqueidad: </b><b class='naranja'>No se ha registrado</b></td>");
                    }
                    out.print("</tr>");
                }
                out.print("<tr>");
                out.print("<td COLSPAN='8'></td>");
                out.print("</tr>");
                // </editor-fold>

                // <editor-fold defaultstate="collapsed" desc="Detalle estaciones Registro.">
                out.print("<table class='table' style='width:100%;'>");
                if (obj_turno[22].equals("GRAFADORA 5")) {
                    for (int i = 0; i < 2; i++) {
                        out.print("<tr>");
                        if (i == 0) {
                            // <editor-fold defaultstate="collapsed" desc="Estaciones de 1-4.">
                            for (int j = 1; j <= 4; j++) {
                                out.print("<td style='text-align: center;'>");
                                if (j == 1) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 1.">
                                    if ((Integer) obj_turno[32] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                } else if (j == 2) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 2.">
                                    if ((Integer) obj_turno[33] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                } else if (j == 3) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 3.">
                                    if ((Integer) obj_turno[34] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                } else {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 4.">
                                    if ((Integer) obj_turno[35] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
// </editor-fold>
                                    out.print("</td>");
                                }
                            }
                            // </editor-fold>
                        } else if (i == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Estaciones de 5-8.">
                            for (int j = 5; j <= 8; j++) {
                                out.print("<td style='text-align: center;'>");
                                if (j == 5) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 5.">
                                    if ((Integer) obj_turno[36] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                } else if (j == 6) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 6.">
                                    if ((Integer) obj_turno[37] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                }
                                if (obj_turno[22].equals("GRAFADORA 5")) {
                                    if (j == 7) {
                                        // <editor-fold defaultstate="collapsed" desc="Estacion 7.">
                                        if ((Integer) obj_turno[38] == 1) {
                                            out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                            estacion = "E" + j + "";
                                            long var = 0;
                                            for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                                Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                                if (estacion.equals(obj_conteo[0])) {
                                                    var = (Long) obj_conteo[1];
                                                }
                                            }
                                            if (var != 12) {
                                                out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br />");
                                            } else {
                                                out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                            }
                                            if (id_rol == 1 || id_rol == 2) {
                                                if (var >= 1) {
                                                    out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                                } else {
                                                    out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                    out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                    out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                    out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                    out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                    out.print("</form>");
                                                }
                                            }
                                        } else if (id_rol == 1 || id_rol == 2) {
                                            out.print("<b>Deshabilitada</b>");
                                            out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                            out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                            out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                            out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                            out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                            out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                            out.print("</form>");
                                        } else {
                                            out.print("<b>Deshabilitada</b>");
                                        }
                                        // </editor-fold>
                                    } else if (j == 8) {
                                        // <editor-fold defaultstate="collapsed" desc="Estacion 8.">
                                        if ((Integer) obj_turno[39] == 1) {
                                            out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                            estacion = "E" + j + "";
                                            long var = 0;
                                            for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                                Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                                if (estacion.equals(obj_conteo[0])) {
                                                    var = (Long) obj_conteo[1];
                                                }
                                            }
                                            if (var != 12) {
                                                out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                            } else {
                                                out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                            }
                                            if (id_rol == 1 || id_rol == 2) {
                                                if (var >= 1) {
                                                    out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                                } else {
                                                    out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                    out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                    out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                    out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                    out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                    out.print("</form>");
                                                }
                                            }
                                        } else if (id_rol == 1 || id_rol == 2) {
                                            out.print("<b>Deshabilitada</b>");
                                            out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                            out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                            out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                            out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                            out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                            out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                            out.print("</form>");
                                        } else {
                                            out.print("<b>Deshabilitada</b>");
                                        }
                                        // </editor-fold>
                                    }
                                }
                                out.print("</td>");
                            }
                            // </editor-fold>
                        }
                    }
                } else if (obj_turno[22].equals("GRAFADORA 6")) {
                    for (int i = 0; i < 2; i++) {
                        out.print("<tr>");
                        if (i == 0) {
                            // <editor-fold defaultstate="collapsed" desc="Estaciones de 1-4.">
                            for (int j = 1; j <= 4; j++) {
                                out.print("<td style='text-align: center;'>");
                                if (j == 1) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 1.">
                                    if ((Integer) obj_turno[32] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                } else if (j == 2) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 2.">
                                    if ((Integer) obj_turno[33] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                } else if (j == 3) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 3.">
                                    if ((Integer) obj_turno[34] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                } else {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 4.">
                                    if ((Integer) obj_turno[35] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
// </editor-fold>
                                    out.print("</td>");
                                }
                            }
                            // </editor-fold>
                        } else if (i == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Estaciones de 5-8.">
                            for (int j = 5; j <= 8; j++) {
                                out.print("<td style='text-align: center;'>");
                                if (j == 5) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 5.">
                                    if ((Integer) obj_turno[36] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                } else if (j == 6) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 6.">
                                    if ((Integer) obj_turno[37] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                }
                                if (j == 7) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 7.">
                                    if ((Integer) obj_turno[38] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br />");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                } else if (j == 8) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 8.">
                                    if ((Integer) obj_turno[39] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                }
                                out.print("</td>");
                            }
                            // </editor-fold>
                        }
                    }
                } else {
                    for (int i = 0; i < 2; i++) {
                        out.print("<tr>");
                        if (i == 0) {
                            // <editor-fold defaultstate="collapsed" desc="Estaciones de 1-3.">
                            for (int j = 1; j <= 3; j++) {
                                out.print("<td style='text-align: center;'>");
                                if (j == 1) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 1.">
                                    if ((Integer) obj_turno[32] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                } else if (j == 2) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 2.">
                                    if ((Integer) obj_turno[33] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                } else // <editor-fold defaultstate="collapsed" desc="Estacion 3.">
                                {
                                    if ((Integer) obj_turno[34] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                }
                                // </editor-fold>
                                out.print("</td>");
                            }
                            // </editor-fold>
                        } else if (i == 1) {
                            // <editor-fold defaultstate="collapsed" desc="Estaciones de 4-6.">
                            for (int j = 4; j <= 6; j++) {
                                out.print("<td style='text-align: center;'>");
                                if (j == 4) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 4.">
                                    if ((Integer) obj_turno[35] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                } else if (j == 5) {
                                    // <editor-fold defaultstate="collapsed" desc="Estacion 5.">
                                    if ((Integer) obj_turno[36] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    }
                                    // </editor-fold>
                                } else // <editor-fold defaultstate="collapsed" desc="Estacion 6.">
                                {
                                    if ((Integer) obj_turno[37] == 1) {
                                        out.print("<a style='text-decoration: none;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + id_turno + "&registro=" + 3 + "&txt_bus=" + filtro + "&estacion=E" + j + "'><b>ESTACIÓN " + j + "</b></a><br/><br/>");
                                        estacion = "E" + j + "";
                                        long var = 0;
                                        for (int k = 0; k < lst_NumCavidades.size(); k++) {
                                            Object[] obj_conteo = (Object[]) lst_NumCavidades.get(k);
                                            if (estacion.equals(obj_conteo[0])) {
                                                var = (Long) obj_conteo[1];
                                            }
                                        }
                                        if (var != 12) {
                                            out.print("<b style='color: #000;'>Cavidad(es) " + var + "</b><br/>");
                                        } else {
                                            out.print("<b style='color: #000;'>Controles Finalizados</b><br/>");
                                        }
                                        if (id_rol == 1 || id_rol == 2) {
                                            if (var >= 1) {
                                                out.print("<b style='color: #000;'>Función deshabilitada</b><br/>");
                                            } else {
                                                out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                                out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                                out.print("<b style='color: #000;'>Habilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='0'/></b><br/>");
                                                out.print("</form>");
                                            }
                                        }
                                    } else if (id_rol == 1 || id_rol == 2) {
                                        out.print("<b>Deshabilitada</b>");
                                        out.print("<form method='post' action='Turno?opc=7' name='form_check" + j + "'>");
                                        out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                                        out.print("<input type='hidden' name='estacion' value='" + j + "'>");
                                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                                        out.print("<b style='color: #000;'>Deshabilitada\n<input type='checkbox' name='check_habilitado' onclick='javascript:document.form_check" + j + ".submit()' value='1'/></b><br/>");
                                        out.print("</form>");
                                    } else {
                                        out.print("<b>Deshabilitada</b>");
                                    } // </editor-fold>
                                }
                                out.print("</td>");
                            }
                            // </editor-fold>
                        }
                    }
                }
                out.print("</tr>");

                out.print("</table>");
                out.print("<br/>");
                // </editor-fold>

                // <editor-fold defaultstate="collapsed"  desc="Consulta de Defectos.">
                out.print("<table class='table' style='width:100%;'");
                out.print("<tr>");
                if (!obj_turno[28].equals("rechazado")) {
                    for (int i = 0; i < lst_defecto.size(); i++) {
                        Object[] obj_dfc = (Object[]) lst_defecto.get(i);
                        out.print("<td style='text-align: center;'><b>" + obj_dfc[4] + "</b></td>");
                        if (i == lst_defecto.size() - 1) {
                            out.print("<td style='text-align: center;'><b>TOTAL RECHAZO</b></td>");
                        }
                    }
                }
                out.print("</tr>");

                out.print("<tr>");
                if (!obj_turno[28].equals("rechazado")) {
                    for (int i = 0; i < lst_defecto.size(); i++) {
                        Object[] obj_dfc = (Object[]) lst_defecto.get(i);
                        out.print("<script type='text/javascript'>");
                        out.print("function enviar_cantidad" + i + "(){");
                        out.print("document.enviar" + i + ".submit()");
                        out.print("}");
                        out.print("</script>");
                        if (id_rol == 1 || id_rol == 2 || id_rol == 3) {
                            out.print("<form method='post' name='enviar" + i + "' id='enviar" + i + "' action='Visual?opc=1'>");
                            out.print("<input type='hidden' name='idV' value='" + obj_dfc[0] + "'/>");
                            out.print("<input type='hidden' name='idT' value='" + obj_dfc[2] + "'/>");
                            out.print("<input type='hidden' name='idD' value='" + obj_dfc[3] + "'/>");
                            out.print("<input type='hidden' name='idO' value='" + id_orden + "'/>");
                            out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'/>");
                            num_cuarentena = obj_turno[5] + "-" + obj_turno[2].toString().replace("-", "") + "-" + obj_turno[3] + "-" + obj_turno[27];
                            if (Integer.parseInt(obj_turno[53].toString()) == 1) {
                                out.print("<input type='hidden' name='cuarentena' value='seguimiento'/>");
                            } else {
                                out.print("<input type='hidden' name='cuarentena' value='" + num_cuarentena + "'/>");
                            }
                            out.print("<td style='text-align: center;'>");
                            if (obj_turno[18].equals("abierto")) {
                                out.print("<input style='text-align: center; width: 50px;' type='number' value='" + ((obj_dfc[5] != null) ? obj_dfc[5] : "") + "' min='0' name='txt_cantidad' id='validateCan" + i + "' onkeypress='if (event.keyCode == 13) enviar_cantidad" + i + "()' placeholder='0'>");
                            } else {
                                out.print("<input style='text-align: center; width: 50px;' type='number' min='0' value='" + ((obj_dfc[5] != null) ? obj_dfc[5] : "") + "' name='txt_cantidad' id='validateCan" + i + "' readonly='true' placeholder='0'>");
                            }
                            if (obj_dfc[5] != null) {
                                suma = suma + Integer.parseInt(obj_dfc[5].toString());
                            }
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('validateCan" + i + "');");
                            out.print("validation.add( Validate.Decimal );");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script>");
                            out.print("</td>");
                            if (i == lst_defecto.size() - 1) {
                                out.print("<td style='text-align: center;'><b>" + suma + "</b></td>");
                            }
                            out.print("</form>");
                        } else {
                            out.print("<td style='text-align: center;'>");
                            out.print("<input style='text-align: center; width: 50px;' type='number' min='0' value='" + ((obj_dfc[5] != null) ? obj_dfc[5] : "") + "' name='txt_cantidad' id='validateCan" + i + "' readonly='true' placeholder='0'>");
                            if (obj_dfc[5] != null) {
                                suma = suma + Integer.parseInt(obj_dfc[5].toString());
                            }
                            out.print("</td>");
                            if (i == lst_defecto.size() - 1) {
                                out.print("<td style='text-align: center;'><b>" + suma + "</b></td>");
                            }
                        }
                    }
                }
                out.print("</tr>");
                out.print("</table>");
                // </editor-fold>
                //</editor-fold>
            } else if (Vregistro == 3) {
                // <editor-fold defaultstate="collapsed"  desc="REGISTRO TOMAS.">
                estacion = (String) pageContext.getRequest().getAttribute("estacion");
                lst_turno = jpa_turno.consultaTurnoId(id_turno);
                Object[] obj_turno = (Object[]) lst_turno.get(0);
                lst_ControlDimensional = jpa_DimensionalT.ConsultaControlDimensional(id_turno, estacion);
                lst_ParametrosFichaTecnica = jpa_turno.consultarParametrosFichaTecnica(id_turno);
                Object[] obj_ParametroFT = (Object[]) lst_ParametrosFichaTecnica.get(0);
                out.print("<a href='Turno?opc=1&idO=" + id_orden + "&idT=" + obj_turno[0] + "&registro=" + 2 + "&txt_bus=" + filtro + "' ><img src='Interfaz/Contenido/Iconos/Volver.png' width='22' height='22' title='Volver'></a>");
                out.print("<table class='table' style='width:100%;'>");
                out.print("<tr>");
                out.print("<td COLSPAN='8' style='text-align: center;'><b>" + estacion + "</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 50px;'><b>CAVIDAD</b></td>");
                out.print("<td style='text-align: center;'><b>ALTURA PORTAPISTÓN<br />" + obj_ParametroFT[9] + "</b></td>");
                out.print("<td style='text-align: center;'><b>DIÁMETRO EXTERIOR<br />" + obj_ParametroFT[6] + "</b></td>");
                out.print("<td style='text-align: center;'><b>LONGITUD A INTRODUCIR<br />" + obj_ParametroFT[3] + "</b></td>");
                out.print("<td style='text-align: center;'><b>DIAMETRO DE CONFORMADO<br />" + obj_ParametroFT[12] + "</b></td>");
                out.print("<td style='text-align: center;'><b>DIAMETRO MAXIMO DE CONEXIÓN<br />" + obj_ParametroFT[15] + "</b></td>");
                out.print("<td COLSPAN='2' style='text-align: center;'><b>ESTADO</b></td>");
                out.print("</tr>");
                for (int i = 1; i <= 12; i++) {
                    if (id_rol == 1 || id_rol == 2 || id_rol == 3) {
                        out.print("<form method='post' action='Turno?opc=5' id='formu" + valor + "' name='formu" + valor + "' onsubmit='registroTomas();'>");
                        out.print("<input type='hidden' name='valor_miny2' value='" + obj_ParametroFT[10] + "'/>");
                        out.print("<input type='hidden' name='valor_maxy2' value='" + obj_ParametroFT[11] + "'/>");
                        out.print("<input type='hidden' name='valor_minx1' value='" + obj_ParametroFT[7] + "'/>");
                        out.print("<input type='hidden' name='valor_maxx1' value='" + obj_ParametroFT[8] + "'/>");
                        out.print("<input type='hidden' name='valor_miny1' value='" + obj_ParametroFT[4] + "'/>");
                        out.print("<input type='hidden' name='valor_maxy1' value='" + obj_ParametroFT[5] + "'/>");
                        out.print("<input type='hidden' name='valor_minx2' value='" + obj_ParametroFT[13] + "'/>");
                        out.print("<input type='hidden' name='valor_maxx2' value='" + obj_ParametroFT[14] + "'/>");
                        out.print("<input type='hidden' name='valor_minx3' value='" + obj_ParametroFT[16] + "'/>");
                        out.print("<input type='hidden' name='valor_maxx3' value='" + obj_ParametroFT[17] + "'/>");
                        out.print("<input type='hidden' name='estacion' value='" + estacion + "'/>");
                        out.print("<input type='hidden' name='cavidad' value='" + i + "'/>");
                        out.print("<input type='hidden' name='idT' value='" + id_turno + "'/>");
                        out.print("<input type='hidden' name='idO' value='" + id_orden + "'/>");
                        out.print("<input type='hidden' name='registro' value='" + obj_turno[25] + "'/>");
                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'/>");
                        out.print("<tr>");
                        out.print("<td style='text-align: center;'><b>" + i + "</b></td>");
                        // <editor-fold defaultstate="collapsed"  desc="Parámetro Eje Y2.">
                        if (!lst_ControlDimensional.isEmpty()) {
                            for (int j = 0; j < lst_ControlDimensional.size(); j++) {
                                Object[] obj_completar = (Object[]) lst_ControlDimensional.get(j);
                                if (i == (Integer) obj_completar[3]) {
                                    dato = obj_completar[4].toString();
                                    cavidad = (Integer) obj_completar[3];
                                }
                            }
                            if (i == cavidad) {
                                out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + dato + "</b></td>");
//                                out.print("<td style='background-color: #97FF97; text-align: center;'><input class='registro_toma' style='width: 100px; text-align: center;' name='txt_y2' id='txt_parameter" + count_ + "' type='text' value='" + dato + "' onChange='validacion" + count_ + "(" + obj_prm[10] + ", " + obj_prm[11] + ",document.formu" + vlr_ + ".txt_parameter" + (count_ + 1) + ")'/></td>");
                            } else {
                                out.print("<td style='text-align: center;'><input class='registro_toma' style='width: 100px; text-align: center;margin-bottom:0px;' name='txt_y2' id='txt_parameter" + count + "' type='text' placeholder='Valor' onChange=\"validacion('txt_parameter" + count + "'," + obj_ParametroFT[10] + ", " + obj_ParametroFT[11] + ",'txt_parameter" + (count + 1) + "'," + i + ")\"/></td>");
                            }
                        } else {
                            out.print("<td style='text-align: center;'><input class='registro_toma' style='width: 100px; text-align: center;margin-bottom:0px;' name='txt_y2' id='txt_parameter" + count + "' type='text' placeholder='Valor' onChange=\"validacion('txt_parameter" + count + "'," + obj_ParametroFT[10] + ", " + obj_ParametroFT[11] + ",'txt_parameter" + (count + 1) + "'," + i + ")\"/></td>");
                        }
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('txt_parameter" + count + "');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("validation.add( Validate.Decimal );");
                        out.print("</script>");
//                        out.print("<script type='text/javascript'>");
//                        out.print("function validacion" + count + "(mn, my, pr) {");
//                        out.print("var vari = parseFloat(formu" + valor + ".txt_parameter" + count + ".value);");
//                        out.print("var max = parseFloat(my);");
//                        out.print("var min = parseFloat(mn);");
//                        out.print("if (vari <= max && vari >= min) {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.style.backgroundColor = '#97FF97';");
//                        out.print("} else {");
//                        out.print("var redondeo = vari.toFixed(2);");
//                        out.print("if (redondeo <= max && redondeo >= min) {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.value = redondeo;");
//                        out.print("objv.style.backgroundColor = '#97FF97';");
//                        out.print("} else {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.style.backgroundColor = '#FF6363';");
//                        out.print("}");
//                        out.print("}");
//                        out.print("var campo = document.getElementById('txt_parameter" + count + "').value;");
//                        out.print("campo = campo.replace(/ /g,'');");
//                        out.print("document.getElementById('txt_parameter" + count + "').value = campo;");
//                        out.print("pr.focus();");
//                        out.print("}");
//                        out.print("</script>");
                        count++;
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed"  desc="Parámetro Eje X1.">
                        if (!lst_ControlDimensional.isEmpty()) {
                            for (int j = 0; j < lst_ControlDimensional.size(); j++) {
                                Object[] obj_completar = (Object[]) lst_ControlDimensional.get(j);
                                if (i == (Integer) obj_completar[3]) {
                                    dato = obj_completar[5].toString();
                                    cavidad = (Integer) obj_completar[3];
                                }
                            }
                            if (i == cavidad) {
                                out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + dato + "</b></td>");
//                                out.print("<td style='background-color: #97FF97; text-align: center;'><input style='width: 100px; text-align: center;' name='txt_x1' id='txt_parameter" + count_ + "' type='text' value='" + dato + "' onChange='validacion" + count_ + "(" + obj_prm[7] + ", " + obj_prm[8] + ",document.formu" + vlr_ + ".txt_parameter" + (count_ + 1) + ")'/></td>");
                            } else {
                                out.print("<td style='text-align: center;'><input style='width: 100px; text-align: center;margin-bottom:0px;' name='txt_x1' id='txt_parameter" + count + "' type='text' placeholder='Valor' onChange=\"validacion('txt_parameter" + count + "'," + obj_ParametroFT[7] + ", " + obj_ParametroFT[8] + ",'txt_parameter" + (count + 1) + "'," + i + ")\"/></td>");
                            }
                        } else {
                            out.print("<td style='text-align: center;'><input style='width: 100px; text-align: center;margin-bottom:0px;' name='txt_x1' id='txt_parameter" + count + "' type='text' placeholder='Valor' onChange=\"validacion('txt_parameter" + count + "'," + obj_ParametroFT[7] + ", " + obj_ParametroFT[8] + ",'txt_parameter" + (count + 1) + "'," + i + ")\"/></td>");
                        }
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('txt_parameter" + count + "');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("validation.add( Validate.Decimal );");
                        out.print("</script>");
//                        out.print("<script type='text/javascript'>");
//                        out.print("function validacion" + count + "(mn, my, pr) {");
//                        out.print("var vari = parseFloat(formu" + valor + ".txt_parameter" + count + ".value);");
//                        out.print("var max = parseFloat(my);");
//                        out.print("var min = parseFloat(mn);");
//                        out.print("if (vari <= max && vari >= min) {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.style.backgroundColor = '#97FF97';");
//                        out.print("} else {");
//                        out.print("var redondeo = vari.toFixed(2);");
//                        out.print("if (redondeo <= max && redondeo >= min) {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.value = redondeo;");
//                        out.print("objv.style.backgroundColor = '#97FF97';");
//                        out.print("} else {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.style.backgroundColor = '#FF6363';");
//                        out.print("}");
//                        out.print("}");
//                        out.print("var campo = document.getElementById('txt_parameter" + count + "').value;");
//                        out.print("campo = campo.replace(/ /g,'');");
//                        out.print("document.getElementById('txt_parameter" + count + "').value = campo;");
//                        out.print("pr.focus();");
//                        out.print("}");
//                        out.print("</script>");
                        count++;
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed"  desc="Parámetro Eje Y1.">
                        if (!lst_ControlDimensional.isEmpty()) {
                            for (int j = 0; j < lst_ControlDimensional.size(); j++) {
                                Object[] obj_completar = (Object[]) lst_ControlDimensional.get(j);
                                if (i == (Integer) obj_completar[3]) {
                                    dato = obj_completar[6].toString();
                                    cavidad = (Integer) obj_completar[3];
                                }
                            }
                            if (i == cavidad) {
                                out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + dato + "</b></td>");
//                                out.print("<td style='background-color: #97FF97; text-align: center;'><input style='width: 100px; text-align: center;' name='txt_y1' id='txt_parameter" + count_ + "' type='text' value='" + dato + "' onChange='validacion" + count_ + "(" + obj_prm[4] + ", " + obj_prm[5] + ",document.formu" + vlr_ + ".txt_parameter" + (count_ + 1) + ")'/></td>");
                            } else {
                                out.print("<td style='text-align: center;'><input style='width: 100px; text-align: center;margin-bottom:0px;' name='txt_y1' id='txt_parameter" + count + "' type='text' placeholder='Valor' onChange=\"validacion('txt_parameter" + count + "'," + obj_ParametroFT[4] + ", " + obj_ParametroFT[5] + ",'txt_parameter" + (count + 1) + "'," + i + ")\"/></td>");
//                                out.print("<td style='text-align: center;'><input style='width: 100px; text-align: center;margin-bottom:0px;' name='txt_y1' id='txt_parameter" + count + "' type='text' placeholder='Valor' onChange='validacion" + count + "(" + obj_ParametroFT[4] + ", " + obj_ParametroFT[5] + ",document.formu" + valor + ".txt_parameter" + (count + 1) + ")'/></td>");
                            }
                        } else {
                            out.print("<td style='text-align: center;'><input style='width: 100px; text-align: center;margin-bottom:0px;' name='txt_y1' id='txt_parameter" + count + "' type='text' placeholder='Valor' onChange=\"validacion('txt_parameter" + count + "'," + obj_ParametroFT[4] + ", " + obj_ParametroFT[5] + ",'txt_parameter" + (count + 1) + "'," + i + ")\"/></td>");
//                            out.print("<td style='text-align: center;'><input style='width: 100px; text-align: center;margin-bottom:0px;' name='txt_y1' id='txt_parameter" + count + "' type='text' placeholder='Valor' onChange='validacion" + count + "(" + obj_ParametroFT[4] + ", " + obj_ParametroFT[5] + ",document.formu" + valor + ".txt_parameter" + (count + 1) + ")'/></td>");
                        }
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('txt_parameter" + count + "');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("validation.add( Validate.Decimal );");
                        out.print("</script>");
//                        out.print("<script type='text/javascript'>");
//                        out.print("function validacion" + count + "(mn, my, pr) {");
//                        out.print("var vari = parseFloat(formu" + valor + ".txt_parameter" + count + ".value);");
//                        out.print("var max = parseFloat(my);");
//                        out.print("var min = parseFloat(mn);");
//                        out.print("if (vari <= max && vari >= min) {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.style.backgroundColor = '#97FF97';");
//                        out.print("} else {");
//                        out.print("var redondeo = vari.toFixed(2);");
//                        out.print("if (redondeo <= max && redondeo >= min) {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.value = redondeo;");
//                        out.print("objv.style.backgroundColor = '#97FF97';");
//                        out.print("} else {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.style.backgroundColor = '#FF6363';");
//                        out.print("}");
//                        out.print("}");
//                        out.print("var campo = document.getElementById('txt_parameter" + count + "').value;");
//                        out.print("campo = campo.replace(/ /g,'');");
//                        out.print("document.getElementById('txt_parameter" + count + "').value = campo;");
//                        out.print("pr.focus();");
//                        out.print("}");
//                        out.print("</script>");
                        count++;
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed"  desc="Parámetro Eje X2.">
                        if (!lst_ControlDimensional.isEmpty()) {
                            for (int j = 0; j < lst_ControlDimensional.size(); j++) {
                                Object[] obj_completar = (Object[]) lst_ControlDimensional.get(j);
                                if (i == (Integer) obj_completar[3]) {
                                    dato = obj_completar[7].toString();
                                    cavidad = (Integer) obj_completar[3];
                                }
                            }
                            if (i == cavidad) {
                                out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + dato + "</b></td>");
//                                out.print("<td style='background-color: #97FF97; text-align: center;'><input style='width: 100px; text-align: center;' name='txt_x2' id='txt_parameter" + count_ + "' type='text' value='" + dato + "' onChange='validacion" + count_ + "(" + obj_prm[13] + ", " + obj_prm[14] + ",document.formu" + vlr_ + ".txt_parameter" + (count_ + 1) + ")'/></td>");
                            } else {
                                out.print("<td style='text-align: center;'><input style='width: 100px; text-align: center;margin-bottom:0px;' name='txt_x2' id='txt_parameter" + count + "' type='text' placeholder='Valor' onChange=\"validacion('txt_parameter" + count + "'," + obj_ParametroFT[13] + ", " + obj_ParametroFT[14] + ",'txt_parameter" + (count + 1) + "'," + i + ")\"/></td>");
//                                out.print("<td style='text-align: center;'><input style='width: 100px; text-align: center;margin-bottom:0px;' name='txt_x2' id='txt_parameter" + count + "' type='text' placeholder='Valor' onChange='validacion" + count + "(" + obj_ParametroFT[13] + ", " + obj_ParametroFT[14] + ",document.formu" + valor + ".txt_parameter" + (count + 1) + ")'/></td>");
                            }
                        } else {
                            out.print("<td style='text-align: center;'><input style='width: 100px; text-align: center;margin-bottom:0px;' name='txt_x2' id='txt_parameter" + count + "' type='text' placeholder='Valor' onChange=\"validacion('txt_parameter" + count + "'," + obj_ParametroFT[13] + ", " + obj_ParametroFT[14] + ",'txt_parameter" + (count + 1) + "'," + i + ")\"/></td>");
//                            out.print("<td style='text-align: center;'><input style='width: 100px; text-align: center;margin-bottom:0px;' name='txt_x2' id='txt_parameter" + count + "' type='text' placeholder='Valor' onChange='validacion" + count + "(" + obj_ParametroFT[13] + ", " + obj_ParametroFT[14] + ",document.formu" + valor + ".txt_parameter" + (count + 1) + ")'/></td>");
                        }
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('txt_parameter" + count + "');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("validation.add( Validate.Decimal );");
                        out.print("</script>");
//                        out.print("<script type='text/javascript'>");
//                        out.print("function validacion" + count + "(mn, my, pr) {");
//                        out.print("var vari = parseFloat(formu" + valor + ".txt_parameter" + count + ".value);");
//                        out.print("var max = parseFloat(my);");
//                        out.print("var min = parseFloat(mn);");
//                        out.print("if (vari <= max && vari >= min) {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.style.backgroundColor = '#97FF97';");
//                        out.print("} else {");
//                        out.print("var redondeo = vari.toFixed(2);");
//                        out.print("if (redondeo <= max && redondeo >= min) {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.value = redondeo;");
//                        out.print("objv.style.backgroundColor = '#97FF97';");
//                        out.print("} else {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.style.backgroundColor = '#FF6363';");
//                        out.print("}");
//                        out.print("}");
//                        out.print("var campo = document.getElementById('txt_parameter" + count + "').value;");
//                        out.print("campo = campo.replace(/ /g,'');");
//                        out.print("document.getElementById('txt_parameter" + count + "').value = campo;");
//                        out.print("pr.focus();");
//                        out.print("}");
//                        out.print("</script>");
                        count++;
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed"  desc="Parámetro Eje X3.">
                        if (!lst_ControlDimensional.isEmpty()) {
                            for (int j = 0; j < lst_ControlDimensional.size(); j++) {
                                Object[] obj_completar = (Object[]) lst_ControlDimensional.get(j);
                                if (i == (Integer) obj_completar[3]) {
                                    dato = obj_completar[8].toString();
                                    cavidad = (Integer) obj_completar[3];
                                }
                            }
                            if (i == cavidad) {
                                out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + dato + "</b></td>");
                            } else {
                                out.print("<td style='text-align: center;'><input style='width: 100px; text-align: center;margin-bottom:0px;' name='txt_x3' id='txt_parameter" + count + "' type='text' placeholder='Valor' onChange=\"validacion('txt_parameter" + count + "'," + obj_ParametroFT[16] + ", " + obj_ParametroFT[17] + ",'Sin'," + i + ")\"/></td>");
                            }
                        } else {
                            out.print("<td style='text-align: center;'><input style='width: 100px; text-align: center;margin-bottom:0px;' name='txt_x3' id='txt_parameter" + count + "' type='text' placeholder='Valor' onChange=\"validacion('txt_parameter" + count + "'," + obj_ParametroFT[16] + ", " + obj_ParametroFT[17] + ",'Sin'," + i + ")\"/></td>");
                        }
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('txt_parameter" + count + "');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("validation.add( Validate.Decimal );");
                        out.print("</script>");
//                        out.print("<script type='text/javascript'>");
//                        out.print("function validacion" + count + "(mn, my, pr) {");
//                        out.print("var vari = parseFloat(formu" + valor + ".txt_parameter" + count + ".value);");
//                        out.print("var max = parseFloat(my);");
//                        out.print("var min = parseFloat(mn);");
//                        out.print("if (vari <= max && vari >= min) {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.style.backgroundColor = '#97FF97';");
//                        out.print("} else {");
//                        out.print("var redondeo = vari.toFixed(2);");
//                        out.print("if (redondeo <= max && redondeo >= min) {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.value = redondeo;");
//                        out.print("objv.style.backgroundColor = '#97FF97';");
//                        out.print("} else {");
//                        out.print("objv = document.getElementById('txt_parameter" + count + "');");
//                        out.print("objv.style.backgroundColor = '#FF6363';");
//                        out.print("}");
//                        out.print("}");
////                        out.print("var campo = document.getElementById('txt_parameter" + count + "').value;");
////                        out.print("campo = campo.replace(/ /g,'');");
////                        out.print("document.getElementById('txt_parameter" + count + "').value = campo;");
//                        out.print("pr.focus();");
//                        out.print("}");
//                        out.print("</script>");
                        count++;
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed"  desc="Estado por Cavidad.">
                        if (!lst_ControlDimensional.isEmpty()) {
                            for (int j = 0; j < lst_ControlDimensional.size(); j++) {
                                Object[] obj_completar = (Object[]) lst_ControlDimensional.get(j);
                                if (i == (Integer) obj_completar[3]) {
                                    cavidad = (Integer) obj_completar[3];
                                }
                            }
                            if (i == cavidad) {
                                out.print("<td style='text-align: center;'><img src='Interfaz/Contenido/Iconos/chulo1.png' width='15' height='15'></td>");
                            } else {
                                out.print("<td style='text-align: center;'><div id='DVerificar" + i + "' style='display:none'><input type='checkbox' id='Verificar" + i + "' name='Cbx_validar' required /></div>");
                                out.print("<input type='hidden' id='Arg_datos" + i + "' value='0' required/></td>");
                            }
                        } else if (count == 0) {
                            out.print("<td style='text-align: center;'><div id='DVerificar" + i + "' style='display:none'><input type='checkbox' id='Verificar" + i + "' name='Cbx_validar' required /></div>");
                            out.print("<input type='hidden' id='Arg_datos" + i + "' value='0' required/></td>");
                        } else {
                            out.print("<td style='text-align: center;'><div id='DVerificar" + i + "' style='display:none'><input type='checkbox' id='Verificar" + i + "' name='Cbx_validar' required /></div>");
                            out.print("<input type='hidden' id='Arg_datos" + i + "' value='0' required/></td>");

                        }
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('Verificar" + count + "');");
                        out.print("validation.add( Validate.Acceptance );");
                        out.print("</script>");
                        count++;
                        if (!lst_ControlDimensional.isEmpty()) {
                            for (int j = 0; j < lst_ControlDimensional.size(); j++) {
                                Object[] obj_completar = (Object[]) lst_ControlDimensional.get(j);
                                if (i == (Integer) obj_completar[3]) {
                                    cavidad = (Integer) obj_completar[3];
                                }
                            }
                            if (i == cavidad) {
                                out.print("<td style='text-align: center;'><b>Cumple\nespecificación</b></td>");
                            } else {
                                out.print("<td style='text-align: center;width:10%;'><input type='submit' id='btsubmit' value='Guardar' style='width:60%'>");
                                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 30px;display:none;' id='puntos'>\n"
                                        + "          <div></div>\n"
                                        + "          <div></div>\n"
                                        + "          <div></div>\n"
                                        + "        </div></td>");
                            }
                        } else {
                            out.print("<td style='text-align: center;width:10%;'><input type='submit' id='btsubmit' value='Guardar' style='width:60%'>");
                            out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 30bpx;display:none;' id='puntos'>\n"
                                    + "          <div></div>\n"
                                    + "          <div></div>\n"
                                    + "          <div></div>\n"
                                    + "        </div></td>");
                        }
                        out.print("</tr>");
                        // </editor-fold>
                        out.print("</form>");
                        valor++;
                    } else {
                        out.print("<tr>");
                        out.print("<td style='text-align: center;'><b>" + i + "</b></td>");
                        // <editor-fold defaultstate="collapsed"  desc="Parámetro Eje Y2.">
                        if (!lst_ControlDimensional.isEmpty()) {
                            for (int j = 0; j < lst_ControlDimensional.size(); j++) {
                                Object[] obj_completar = (Object[]) lst_ControlDimensional.get(j);
                                if (i == (Integer) obj_completar[3]) {
                                    dato = obj_completar[4].toString();
                                    cavidad = (Integer) obj_completar[3];
                                }
                            }
                            if (i == cavidad) {
                                out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + dato + "</b></td>");
//                                out.print("<td style='background-color: #97FF97; text-align: center;'><input class='registro_toma' style='width: 100px; text-align: center;' name='txt_y2' id='txt_parameter" + count_ + "' type='text' value='" + dato + "' onChange='validacion" + count_ + "(" + obj_prm[10] + ", " + obj_prm[11] + ",document.formu" + vlr_ + ".txt_parameter" + (count_ + 1) + ")'/></td>");
                            } else {
                                out.print("<td style='text-align: center;'>x</td>");
                            }
                        } else {
                            out.print("<td style='text-align: center;'>x</td>");
                        }

                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed"  desc="Parámetro Eje X1.">
                        if (!lst_ControlDimensional.isEmpty()) {
                            for (int j = 0; j < lst_ControlDimensional.size(); j++) {
                                Object[] obj_completar = (Object[]) lst_ControlDimensional.get(j);
                                if (i == (Integer) obj_completar[3]) {
                                    dato = obj_completar[5].toString();
                                    cavidad = (Integer) obj_completar[3];
                                }
                            }
                            if (i == cavidad) {
                                out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + dato + "</b></td>");
//                                out.print("<td style='background-color: #97FF97; text-align: center;'><input style='width: 100px; text-align: center;' name='txt_x1' id='txt_parameter" + count_ + "' type='text' value='" + dato + "' onChange='validacion" + count_ + "(" + obj_prm[7] + ", " + obj_prm[8] + ",document.formu" + vlr_ + ".txt_parameter" + (count_ + 1) + ")'/></td>");
                            } else {
                                out.print("<td style='text-align: center;'>x</td>");
                            }
                        } else {
                            out.print("<td style='text-align: center;'>x</td>");
                        }
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed"  desc="Parámetro Eje Y1.">
                        if (!lst_ControlDimensional.isEmpty()) {
                            for (int j = 0; j < lst_ControlDimensional.size(); j++) {
                                Object[] obj_completar = (Object[]) lst_ControlDimensional.get(j);
                                if (i == (Integer) obj_completar[3]) {
                                    dato = obj_completar[6].toString();
                                    cavidad = (Integer) obj_completar[3];
                                }
                            }
                            if (i == cavidad) {
                                out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + dato + "</b></td>");
//                                out.print("<td style='background-color: #97FF97; text-align: center;'><input style='width: 100px; text-align: center;' name='txt_y1' id='txt_parameter" + count_ + "' type='text' value='" + dato + "' onChange='validacion" + count_ + "(" + obj_prm[4] + ", " + obj_prm[5] + ",document.formu" + vlr_ + ".txt_parameter" + (count_ + 1) + ")'/></td>");
                            } else {
                                out.print("<td style='text-align: center;'>x</td>");
                            }
                        } else {
                            out.print("<td style='text-align: center;'>x</td>");
                        }
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed"  desc="Parámetro Eje X2.">
                        if (!lst_ControlDimensional.isEmpty()) {
                            for (int j = 0; j < lst_ControlDimensional.size(); j++) {
                                Object[] obj_completar = (Object[]) lst_ControlDimensional.get(j);
                                if (i == (Integer) obj_completar[3]) {
                                    dato = obj_completar[7].toString();
                                    cavidad = (Integer) obj_completar[3];
                                }
                            }
                            if (i == cavidad) {
                                out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + dato + "</b></td>");
//                                out.print("<td style='background-color: #97FF97; text-align: center;'><input style='width: 100px; text-align: center;' name='txt_x2' id='txt_parameter" + count_ + "' type='text' value='" + dato + "' onChange='validacion" + count_ + "(" + obj_prm[13] + ", " + obj_prm[14] + ",document.formu" + vlr_ + ".txt_parameter" + (count_ + 1) + ")'/></td>");
                            } else {
                                out.print("<td style='text-align: center;'>x</td>");
                            }
                        } else {
                            out.print("<td style='text-align: center;'>x</td>");
                        }
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed"  desc="Parámetro Eje X3.">
                        if (!lst_ControlDimensional.isEmpty()) {
                            for (int j = 0; j < lst_ControlDimensional.size(); j++) {
                                Object[] obj_completar = (Object[]) lst_ControlDimensional.get(j);
                                if (i == (Integer) obj_completar[3]) {
                                    dato = obj_completar[8].toString();
                                    cavidad = (Integer) obj_completar[3];
                                }
                            }
                            if (i == cavidad) {
                                out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + dato + "</b></td>");
//                                out.print("<td style='background-color: #97FF97; text-align: center;'><input style='width: 100px; text-align: center;' name='txt_x3' id='txt_parameter" + count_ + "' type='text' value='" + dato + "' onChange='validacion" + count_ + "(" + obj_prm[16] + ", " + obj_prm[17] + ")'/></td>");
                            } else {
                                out.print("<td style='text-align: center;'>x</td>");
                            }
                        } else {
                            out.print("<td style='text-align: center;'>x</td>");
                        }
                        // </editor-fold>
                        if (!lst_ControlDimensional.isEmpty()) {
                            for (int j = 0; j < lst_ControlDimensional.size(); j++) {
                                Object[] obj_completar = (Object[]) lst_ControlDimensional.get(j);
                                if (i == (Integer) obj_completar[3]) {
                                    cavidad = (Integer) obj_completar[3];
                                }
                            }
                            if (i == cavidad) {
                                out.print("<td style='text-align: center;'><img src='Interfaz/Contenido/Iconos/chulo1.png' width='15' height='15'></td>");
                                out.print("<td style='text-align: center;'><b>Cumple\nespecificación</b></td>");
                            } else {
                                out.print("<td style='text-align: center;' colspan='2'><b class='naranja'>Sin Permisos<b></td>");
                            }
                        } else {
                            out.print("<td style='text-align: center;' colspan='2'><b class='naranja'>Sin Permisos<b></td>");
                        }
                        out.print("<tr>");
                    }
                }
                // <editor-fold defaultstate="collapsed"  desc="Estadistico por Estación.">
                lst_cpk = jpa_DimensionalT.Consultacpk(id_turno, estacion);
                Object[] obj_cpk = (Object[]) lst_cpk.get(0);
                //<editor-fold defaultstate="collapsed" desc="media">
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 50px;'><b>MEDIA</b></td>");
                if (obj_cpk[0] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[0] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[7] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[7] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[14] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[14] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[21] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[21] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[28] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[28] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                out.print("<td COLSPAN='2'></td>");
                out.print("</tr>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="min">
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 50px;'><b>MIN</b></td>");
                if (obj_cpk[1] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[1] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[8] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[8] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[15] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[15] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[22] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[22] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[29] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[29] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                out.print("<td COLSPAN='2'></td>");
                out.print("</tr>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="max">
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 50px;'><b>MAX</b></td>");
                if (obj_cpk[2] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[2] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[9] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[9] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[16] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[16] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[23] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[23] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[30] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[30] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                out.print("<td COLSPAN='2'></td>");
                out.print("</tr>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="desv estandar">
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 50px;'><b>DESV ESTANDAR</b></td>");
                if (obj_cpk[3] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[3] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[10] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[10] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[17] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[17] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[24] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[24] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[31] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[31] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                out.print("<td COLSPAN='2'></td>");
                out.print("</tr>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="cpk <">
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 50px;'><b>CPK <</b></td>");
                if (obj_cpk[4] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[4] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[11] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[11] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[18] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[18] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[25] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[25] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[32] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[32] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                out.print("<td COLSPAN='2'></td>");
                out.print("</tr>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="cpk >">
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 50px;'><b>CPK ></b></td>");
                if (obj_cpk[5] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[5] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[12] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[12] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[19] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[19] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[26] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[26] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[33] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[33] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                out.print("<td COLSPAN='2'></td>");
                out.print("</tr>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="cp">
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 50px;'><b>CP</b></td>");
                if (obj_cpk[6] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[6] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[13] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[13] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[20] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[20] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[27] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[27] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                if (obj_cpk[34] != null) {
                    out.print("<td style='text-align: center;'>" + obj_cpk[34] + "</td>");
                } else {
                    out.print("<td style='text-align: center;'><b>Calcular !</b></td>");
                }
                out.print("<td COLSPAN='2'></td>");
                out.print("</tr>");
                //</editor-fold>
                // </editor-fold>
                out.print("</table>");
                // </editor-fold>
            } else if (Vregistro == 4) {
                // <editor-fold defaultstate="collapsed"  desc="SEGUIMIENTO DIMESIONAL.">
                estacion = (String) pageContext.getRequest().getAttribute("estacion").toString();
                cavidad = Integer.parseInt(pageContext.getRequest().getAttribute("cavidad").toString());
                lst_turno = jpa_turno.consultaTurnoId(id_turno);
                lst_seguimientoT = jpa_dimensional.ConsultaSeguimientoTurno(estacion, cavidad, id_turno);
                lst_parameter = jpa_turno.consultarParametrosFichaTecnica(id_turno);
                Object[] obj_turno = (Object[]) lst_turno.get(0);
                Object[] obj_seguimientoT = (Object[]) lst_seguimientoT.get(0);
                out.print("<div id='sidebar'>");
                // <editor-fold defaultstate="collapsed"  desc="Formulario Control.">
                num_cuarentena = obj_turno[5] + "-" + obj_turno[2].toString().replace("-", "") + "-" + obj_turno[3] + "-" + obj_turno[27];
                out.print("<form method='post' action='Turno?opc=6' onsubmit='ControlC();'>");
                out.print("<input type='hidden' name='idS' value='" + obj_seguimientoT[0] + "'>");
                out.print("<input type='hidden' name='idT' value='" + obj_turno[0] + "'>");
                out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                out.print("<input type='hidden' name='estacion' value='" + estacion + "'>");
                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                if (num_cuarentena.contains("null")) {
                    out.print("<input type='hidden' name='cuarentena' value='seguimiento'>");
                } else {
                    out.print("<input type='hidden' name='cuarentena' value='" + num_cuarentena + "'>");
                }
                out.print("<h3>Control Cuarentena</h3>");
                out.print("<b>Responsable:</b><br/>" + obj_turno[24] + "<br/>");
                out.print("<b>Fecha:</b><br/>" + obj_turno[2] + "<br/>");
                out.print("<b>Turno:</b><br/>" + obj_turno[3] + "<br/>");
                out.print("<b>Orden Producción:</b><br/>" + obj_turno[5] + "<br/>");
                if (num_cuarentena.contains("null")) {
                    out.print("<b>Núm Cuarentena:</b><br/><b class='azul'>Seguimiento</b><br/>");
                } else {
                    out.print("<b>Núm Cuarentena:</b><br/>" + num_cuarentena + "<br/>");
                }
                out.print("<b>Justificación:</b><br/>");
                out.print("<textarea rows='6' id='validateJust' name='txt_justificacion' placeholder='Justificación Cuarentena'></textarea>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateJust');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit' value='Registrar'>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "        </div>");
                out.print("</form>");
                // </editor-fold>
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
                out.print("<div id='content'>");
                // <editor-fold defaultstate="collapsed"  desc="Indicador de la Pieza.">
                Object[] obj_parameter = (Object[]) lst_parameter.get(0);
                out.print("<h3>Indicardor Pieza(s)</h3>");
                out.print("<table class='table' style='width:100%;'>");
                out.print("<tr>");
                out.print("<td style='text-align: center; height: 30px;'><b>ALTURA PORTAPISTÓN<br/>" + obj_parameter[9] + "</b></td>");
                out.print("<td style='text-align: center;'><b>DIÁMETRO EXTERIOR<br/>" + obj_parameter[6] + "</b></td>");
                out.print("<td style='text-align: center;'><b>LONGITUD A INTRODUCIR<br/>" + obj_parameter[3] + "</b></td>");
                out.print("<td style='text-align: center;'><bDIAMETRO DE CONFORMADO<br/>" + obj_parameter[12] + "</b></td>");
                out.print("<td style='text-align: center;'><b>DIAMETRO MAXIMO DE CONEXIÓN<br/>" + obj_parameter[15] + "</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                if (Double.parseDouble(obj_seguimientoT[4].toString()) <= (Double) obj_parameter[11] && Double.parseDouble(obj_seguimientoT[4].toString()) >= (Double) obj_parameter[10]) {
                    out.print("<td style='text-align: center;'>" + obj_seguimientoT[4] + "</td>");
                } else {
                    out.print("<td style='text-align: center; background-color: #FF6363; color: #FFF;'>" + obj_seguimientoT[4] + "</td>");
                }
                if (Double.parseDouble(obj_seguimientoT[5].toString()) <= (Double) obj_parameter[8] && Double.parseDouble(obj_seguimientoT[5].toString()) >= (Double) obj_parameter[7]) {
                    out.print("<td style='text-align: center;'>" + obj_seguimientoT[5] + "</td>");
                } else {
                    out.print("<td style='text-align: center; background-color: #FF6363; color: #FFF;'>" + obj_seguimientoT[5] + "</td>");
                }
                if (Double.parseDouble(obj_seguimientoT[6].toString()) <= (Double) obj_parameter[5] && Double.parseDouble(obj_seguimientoT[6].toString()) >= (Double) obj_parameter[4]) {
                    out.print("<td style='text-align: center;'>" + obj_seguimientoT[6] + "</td>");
                } else {
                    out.print("<td style='text-align: center; background-color: #FF6363; color: #FFF;'>" + obj_seguimientoT[6] + "</td>");
                }
                if (Double.parseDouble(obj_seguimientoT[7].toString()) <= (Double) obj_parameter[14] && Double.parseDouble(obj_seguimientoT[7].toString()) >= (Double) obj_parameter[13]) {
                    out.print("<td style='text-align: center;'>" + obj_seguimientoT[7] + "</td>");
                } else {
                    out.print("<td style='text-align: center; background-color: #FF6363; color: #FFF;'>" + obj_seguimientoT[7] + "</td>");
                }
                if (Double.parseDouble(obj_seguimientoT[8].toString()) <= (Double) obj_parameter[17] && Double.parseDouble(obj_seguimientoT[8].toString()) >= (Double) obj_parameter[16]) {
                    out.print("<td style='text-align: center;'>" + obj_seguimientoT[8] + "</td>");
                } else {
                    out.print("<td style='text-align: center; background-color: #FF6363; color: #FFF;'>" + obj_seguimientoT[8] + "</td>");
                }
                out.print("</tr>");
                out.print("</table>");

//                // </editor-fold>
                // </editor-fold>
            } else if (Vregistro == 5) {
                //<editor-fold defaultstate="collapsed" desc="CUARENTENAS">
                int ver = Integer.parseInt(pageContext.getRequest().getAttribute("ver").toString());
                lst_cuarentena = jpa_cuarentena.ConsultaAprobarCuarentena(arg_cuarentenas);
                lst_seguimiento = jpa_dimensional.ConsultarSeguimiento(id_turno);
                if (filtro.equals("")) {
                    lst_turnos = jpa_turno.consultaTurnosSeguimiento(id_orden);
                } else {
                    lst_turnos = jpa_turno.consultaTurnosSeguimientoFiltro(id_orden, filtro);
                }
                lst_defecto = jpa_defecto.consultarDefectoTurno(id_turno);
                if (ver == 1) {
                    out.print("<div id='content_sin'>");
                } else if (!arg_cuarentenas.equals("[0]")) {
                    Object[] obj_turno = (Object[]) lst_turnos.get(0);
                    Object[] obj_cuarentena = (Object[]) lst_cuarentena.get(0);
                    out.print("<div id='content_sin'>");
                    out.print("<div class='sweet-local'  id='Ventana' tabindex='-1' style='opacity: 1.03; display:block;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='fld_detalle' style='width:40%; overflow-y: scroll; height:70%; position: absolute;top:25%; left:35%;text-align:left '>");
                    //<editor-fold defaultstate="collapsed"  desc="Formulario Aprobación.">
                    out.print("<form  method='post' action='Turno?opc=10'>");
                    out.print("<input type='hidden' name='txt_arg_cuarentena' id='txt_arg_cuarentena' value='" + arg_cuarentenas + "'/> ");
                    out.print("<input type='hidden' name='idO' value='" + id_orden + "'>");
                    out.print("<input type='hidden' name='cuarentena' value='" + obj_cuarentena[3] + "'>");
                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                    out.print("<span style='float:right;' class='fas fa-times fa-size_small' onclick='CerrarCuarentena(" + id_orden + ")'></span>");
                    out.print("<h3>Aprobación Cuarentena</h3>");
                    out.print("<table id='resultados' class='table' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<th align='center' style='background-color: #00838f; border-bottom: 2px solid; color: #FFF;' >CONSECUTIVO</th>");
                    out.print("<td align='center' style='background-color: #00838f; color: #FFF;' ><b style='color: #FFF;'>Lote de Ensamble</b></td>");
                    out.print("<th align='center' style='background-color: #00838f; border-bottom: 2px solid; color: #FFF;' >NUM. CUARENTENA</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_cuarentena.size(); i++) {
                        Object[] obj_cuarentenaA = (Object[]) lst_cuarentena.get(i);
                        out.print("<tr>");
                        out.print("<td> # " + obj_cuarentenaA[2] + "</td>");
                        out.print("<td><b style='color:000;'>" + obj_cuarentenaA[1] + "</b></td>");
                        out.print("<td>" + obj_cuarentenaA[3] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<br><br>");
                    out.print("<textarea rows='6' style='width:520px;' id='validateJust' name='txt_aprobacion' class='input_field' placeholder='Ingresar Aprobación Cuarentena'></textarea>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('validateJust');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<br><br>");
                    out.print("<input type='submit' value='Aprobar'>");
                    out.print("</form>");
                    out.print("</div>");
                    // </editor-fold>
                    out.print("</fieldset>");
                    out.print("<div class='cleaner'></div></div>");
                }
                if (!lst_seguimiento.isEmpty()) {
                    //<editor-fold defaultstate="collapsed" desc="LSITA POR DIMENSIONAL Y OPCION DE CAMBIO DE ESTADO">
                    lst_turno = jpa_turno.consultaTurnoId(id_turno);
                    Object[] obj_turno = (Object[]) lst_turno.get(0);
                    out.print("<a href='Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&ver=" + 0 + "&registro=" + 5 + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='22' height='22' title='Volver' /></a>");
                    out.print("<div id='tab-container'>");
                    out.print("<div class='tab-content'>");
                    out.print("<h1 class='tab'>Pieza(s)</h1>");
                    // <editor-fold defaultstate="collapsed"  desc="Consulta Pieza(s) Turno.">
                    Object[] obj_dimensional = (Object[]) lst_seguimiento.get(0);
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' style='width:100%;' id='resultados'>");
                    out.print("<tr>");
                    out.print("<td style='text-align: center; height: 30px;'><b>Estacion/Cavidad</b></td>");
                    out.print("<td style='text-align: center; height: 30px;'><b>ALTURA PORTAPISTÓN<br/>" + obj_dimensional[26] + "</b></td>");
                    out.print("<td style='text-align: center;'><b>DIÁMETRO EXTERIOR<br/>" + obj_dimensional[29] + "</b></td>");
                    out.print("<td style='text-align: center;'><b>LONGITUD A INTRODUCIR<br/>" + obj_dimensional[32] + "</b></td>");
                    out.print("<td style='text-align: center;'><b>DIAMETRO DE CONFORMADO<br/>" + obj_dimensional[35] + "</b></td>");
                    out.print("<td style='text-align: center;'><b>DIAMETRO MAXIMO DE CONEXIÓN<br/>" + obj_dimensional[38] + "</b></td>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_seguimiento.size(); i++) {
                        Object[] obj_srm = (Object[]) lst_seguimiento.get(i);
                        out.print("<tr>");
                        out.print("<td align='center'>" + obj_srm[2] + "/");
                        out.print("" + obj_srm[3] + "</td>");
                        if ((Double) obj_srm[5] <= (Double) obj_srm[34] && (Double) obj_srm[5] >= (Double) obj_srm[33]) {
                            out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + obj_srm[5] + "</td>");
                        } else {
                            out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #FF6363;'>" + obj_srm[5] + "</b></td>");
                        }
                        if ((Double) obj_srm[6] <= (Double) obj_srm[31] && (Double) obj_srm[6] >= (Double) obj_srm[30]) {
                            out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + obj_srm[6] + "</b></td>");
                        } else {
                            out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #FF6363;'>" + obj_srm[6] + "</b></td>");
                        }
                        if ((Double) obj_srm[7] <= (Double) obj_srm[28] && (Double) obj_srm[7] >= (Double) obj_srm[27]) {
                            out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + obj_srm[7] + "</b></td>");
                        } else {
                            out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #FF6363;'>" + obj_srm[7] + "</b></td>");
                        }
                        if ((Double) obj_srm[8] <= (Double) obj_srm[37] && (Double) obj_srm[8] >= (Double) obj_srm[36]) {
                            out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + obj_srm[8] + "</b></td>");
                        } else {
                            out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #FF6363;'>" + obj_srm[8] + "</b></td>");
                        }
                        if ((Double) obj_srm[9] <= (Double) obj_srm[40] && (Double) obj_srm[9] >= (Double) obj_srm[39]) {
                            out.print("<td align='center'><b style='color:#000;text-shadow: 0 0 5px #00cc00;'>" + obj_srm[9] + "</b></td>");
                        } else {
                            out.print("<td align='center' ><b style='color:#000;text-shadow: 0 0 5px #FF6363;'>" + obj_srm[9] + "</b></td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b>Fecha:</b><br />" + obj_srm[1] + "</td>");
                        if (obj_srm[10] == null) {
                            out.print("<td valign='top' colspan='5'><b>Descripcion: </b>N/A</td>");
                        } else {
                            out.print("<td valign='top' colspan='5'><b>Descripcion: </b>" + obj_srm[10] + "</td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='6'></td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 15);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
//                    // </editor-fold>
                    out.print("</div>");
                    out.print("<div class='tab-content'>");
                    out.print("<h1 class='tab'>Defecto(s)</h1>");
                    //<editor-fold defaultstate="collapsed" desc="consulta defectos turno">
                    out.print("<table class='table' style='width:100%;'");
                    for (int i = 0; i < lst_defecto.size(); i++) {
                        Object[] obj_dfc = (Object[]) lst_defecto.get(i);
                        out.print("<tr>");
                        out.print("<td style='text-align: center;'><b>" + obj_dfc[4] + "</b></td>");
                        out.print("<td style='text-align: center;'>");
                        if (obj_dfc[5] != null) {
                            out.print("" + obj_dfc[5] + "");
                            suma = suma + Integer.parseInt(obj_dfc[5].toString());
                        } else {
                            out.print("0");
                        }
                        out.print("</tr>");
                        if (i == lst_defecto.size() - 1) {
                            out.print("<tr>");
                            out.print("<td style='text-align: center;'><b>TOTAL RECHAZO</b></td>");
                            out.print("<td align='center'><b>" + suma + "</b></td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
//</editor-fold>
                    out.print("</div>");
                    if (obj_turno[51] != null) {
                        out.print("<div class='tab-content'>");
                        out.print("<h1 class='tab'>Justificacion</h1>");
                        //<editor-fold defaultstate="collapsed" desc="consulta justificacion Turno">
                        out.print("<table class='table' style='width:100%;'");
                        out.print("<tr>");
                        out.print("<td><b>FECHA:</b></td>");
                        out.print("<td><b>RESPONSABLE:</b></td>");
                        out.print("<td><b>DESCRIPCION</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td>" + obj_turno[51] + "</td>");
                        out.print("<td>" + obj_turno[24] + "</td>");
                        out.print("<td>" + obj_turno[52] + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        //</editor-fold>
                        out.print("</div>");
                    }
                    //</editor-fold>
                } else {
                    // <editor-fold defaultstate="collapsed"  desc="Consulta Turnos Cuarentenas">
                    if (filtro.equals("")) {
                        out.print("<a href='Orden?opc=1&idO=" + 0 + "&txt_ficha=&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='22' height='22' title='Volver' /></a>");
                    } else {
                        out.print("<a href='Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&ver=" + 0 + "&registro=" + 5 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='22' height='22' title='Volver' /></a>");
                    }
                    out.print("<br>");
                    out.print("<div style='float: right;'>");
                    out.print("<input type='text' name='txt_bus' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("</div>");
                    out.print("<br>");
                    out.print("<h3>Cuarentena Dimensional</h3>");
                    if (lst_turnos == null) {
                        out.print("<h3>No se encontraron resultados</h3>");
                    } else {
                        out.print("<form method='post' action='Turno?opc=1&idO=" + id_orden + "&idT=-1&ver=0&registro=5&txt_bus=' id='FormCuarentena'>");
                        if (id_rol == 3 || id_rol == 4 || id_rol == 5 || id_rol == 6) {
                        } else {
                            out.print("<div style='float:right;'><span class='fas fa-check fa-size_small' onclick='Cuarentena()'></span></div>");
                        }
                        out.print("<input type='hidden' name='txt_arg_cuarentena' id='txt_arg_cuarentena' /> ");
                        out.print("</form>");
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table id='resultados' class='table' style='width:100%;'>");
                        out.print("<tr>");
                        out.print("<td colspan='4'></td>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_turnos.size(); i++) {
                            Object[] obj_Turnos = (Object[]) lst_turnos.get(i);
                            id_turno = (Integer) obj_Turnos[0];
                            List lst_consultaC = jpa_cuarentena.ConsultaTurnoAprovado(id_turno);
                            lst_CantDimensional = jpa_dimensional.ConsultaCantidad(id_turno);
                            Object[] num = (Object[]) lst_CantDimensional.get(0);
                            out.print("<tr>");
                            if (id_rol == 3 || id_rol == 4 || id_rol == 5 || id_rol == 6) {
                                out.print("<th align='center'>" + obj_Turnos[27] + "</th>");
                            } else if (obj_Turnos[28].equals("aprobado") || obj_Turnos[28].equals("rechazado") || obj_Turnos[28].equals("seguimiento")) {
                                out.print("<th align='center'>" + obj_Turnos[27] + "</th>");
                            } else {
                                out.print("<th align='center'><input type='checkbox' name='Masivo' onclick='Masivo(this.value)' id='Masivo-" + obj_Turnos[0] + "' value='" + obj_Turnos[0] + "' checkend >    " + obj_Turnos[27] + "</th>");
                            }
                            if (lst_consultaC.isEmpty()) {
                                if (obj_Turnos[28].equals("aprobado")) {
                                    out.print("<th align='center' style='background-color: #5cb85c; color: #FFF;' colspan='4'>APROBADO</th>");
//                                    out.print("<th align='center' style='background-color: #5cb85c; color: #FFF;' colspan='4'>" + obj_Turnos[28] + "</th>");
                                } else if (obj_Turnos[28].equals("rechazado")) {
                                    out.print("<th align='center' style='background-color: #CA2704; color: #FFF;' colspan='4'>RECHAZADO</th>");
//                                    out.print("<th align='center' style='background-color: #d9534f; color: #FFF;' colspan='4'>" + obj_Turnos[28] + "</th>");
                                } else if (obj_Turnos[28].equals("seguimiento")) {
                                    out.print("<th align='center' style='background-color: #00B0FF; color: #FFF;' colspan='4'>SEGUIMIENTO</th>");
//                                    out.print("<th align='center' style='background-color: #00B0FF; color: #FFF;' colspan='4'>" + obj_Turnos[28] + "</th>");
                                } else if (id_rol == 2 || id_rol == 1) {
                                    out.print("<th align='center' style='background-color: #f58526;' colspan='4'><a style='color: #FFF;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + obj_Turnos[0] + "&ver=" + 0 + "&registro=" + 5 + "&txt_bus=" + filtro + "'>" + obj_Turnos[28] + "</a></th>");
                                } else {
                                    out.print("<th align='center' style='background-color: #f58526; color: #FFF;' colspan='4'>" + obj_Turnos[28] + "</th>");
                                }
                            } else if (obj_Turnos[28].equals("rechazado")) {
                                out.print("<th align='center' style='background-color: #CA2704; color: #FFF;' colspan='4'>RECHAZADO</th>");
//                                out.print("<th align='center' style='background-color: #CA2704; color: #FFF;' colspan='4'>" + obj_Turnos[28] + "</th>");
                            } else if (obj_Turnos[28].equals("seguimiento")) {
                                out.print("<th align='center' style='background-color: #00B0FF; color: #FFF;' colspan='4'>SEGUIMIENTO</th>");
//                                out.print("<th align='center' style='background-color: #00B0FF; color: #FFF;' colspan='4'>" + obj_Turnos[28] + "</th>");
                            } else if (obj_Turnos[28].equals("aprobado")) {
                                out.print("<th align='center' style='background-color: #5cb85c; color: #FFF;' colspan='4'>APROBADO</th>");
//                                out.print("<th align='center' style='background-color: #5cb85c; color: #FFF;' colspan='4'>" + obj_Turnos[28] + "</th>");
                            } else if (id_rol == 2 || id_rol == 1) {
                                out.print("<th align='center' style='background-color: #f58526;' colspan='4'><a style='color: #FFF;' href='Turno?opc=1&idO=" + id_orden + "&idT=" + obj_Turnos[0] + "&ver=" + 0 + "&registro=" + 5 + "&txt_bus=" + filtro + "'>" + obj_Turnos[28] + "</a></th>");
                            } else {
                                out.print("<th align='center' style='background-color: #f58526; color: #FFF;' colspan='4'>" + obj_Turnos[28] + "</th>");
                            }
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center' rowspan='2'>" + obj_Turnos[2] + "<br />TURNO " + obj_Turnos[3] + "</td>");
                            out.print("<td valign='top'><b>LOTE ENSAMBLE :</b><br/><b style='color:#211b1b;'>" + obj_Turnos[10] + "</b></td>");
                            out.print("<td valign='top'><b>MÁQUINA: </b>" + obj_Turnos[22] + "<br/>");
                            out.print("<b>DEFECTO(S): </b>");
                            lst_defecto = jpa_defecto.consultarDefectoTurno(id_turno);
                            int var = 0;
                            for (int k = 0; k < lst_defecto.size(); k++) {
                                Object[] obj = (Object[]) lst_defecto.get(k);
                                if (obj[5] != null) {
                                    var = Integer.parseInt(obj[5].toString());
                                    suma = suma + Integer.parseInt(obj[5].toString());
                                }
                            }
                            if (var != 0) {
                                out.print("" + suma + "<br/>");
                                suma = 0;
                            } else {
                                out.print("0<br/>");
                            }
                            if (lst_consultaC.isEmpty()) {
                                out.print("<b>PIEZA(S): </b> " + num[0] + "");
                            } else {
                                out.print("<b>PIEZA(S): </b> " + num[0] + "");
                            }
                            out.print("</td>");
                            out.print("<td valign='top'><b>LOTE BASE <br/><br/> C: </b>" + obj_Turnos[6] + "<br /><b>P: </b>" + obj_Turnos[7] + "</td>");
                            out.print("<td valign='top'><b>LOTE PISTÓN <br/><br/> C:  </b>" + obj_Turnos[8] + "<br /><b>P: </b>" + obj_Turnos[9] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td valign='top' colspan='2'><b>FECHA DE JUSTIFICACION POR SEGUIMIENTO:</b><br/>" + ((obj_Turnos[31] == null) ? "N/A" : obj_Turnos[31]) + "<br/></td>");
                            out.print("<td valign='top' colspan='2'><b>JUSTIFICACION POR SEGUIMIENTO:</b><br/>" + ((obj_Turnos[32] == null) ? "N/A" : obj_Turnos[32]) + "</td>");
                            out.print("</tr>");
                            // <editor-fold defaultstate="collapsed"  desc="Consulta Aprobación.">
                            List lst_consulta = jpa_cuarentena.ConsultaTurnoAprovado(id_turno);
                            if (!lst_consulta.isEmpty()) {
                                Object[] obj_apr = (Object[]) lst_consulta.get(0);
                                out.print("<tr>");
                                if ((Long) num[0] == 0) {
                                    out.print("<td align='center'><span style='color:#b7b7b7;' class='fas fa-exclamation fa-size_small'></span></td>");
//                                    out.print("<td align='center'><img src='Interfaz/Contenido/Iconos/Warning.png' width='22' height='22'></td>");
                                } else {
                                    out.print("<td align='center'><span onclick='ConsultarControlDCuarentena(" + id_orden + "," + obj_Turnos[0] + ")' class='fas fa-eye fa-size_small'></span></td>");
//                                    out.print("<td align='center'><a href='Turno?opc=1&idO=" + id_orden + "&idT=" + obj_Turnos[0] + "&ver=" + 1 + "&registro=" + 5 + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Ver.png' width='22' height='22'></a></td>");
                                }
                                out.print("<td valign='top'><b>NÚM CUARENTENA: </b><br/>" + obj_apr[5] + "</td>");
                                out.print("<td valign='top'><b>DESCRIPCIÓN: </b><br/>" + obj_apr[6] + "</td>");
                                out.print("<td valign='top'><b>RESPONSABLE: </b><br/>" + obj_apr[3] + " " + obj_apr[4] + "</td>");
                                out.print("<td valign='top'><b>FECHA APROBACIÓN: </b><br />" + obj_apr[1] + "</td>");
                                out.print("</tr>");
                            } else {
                                out.print("<tr>");
                                if ((Long) num[0] == 0) {
                                    out.print("<td align='center'><span style='color:#b7b7b7' class='fas fa-exclamation fa-size_small' title=''></span></td>");
//                                    out.print("<td align='center'><img src='Interfaz/Contenido/Iconos/Warning.png' width='22' height='22'></td>");
                                } else {
                                    out.print("<td align='center'><span onclick='ConsultarControlDCuarentena(" + id_orden + "," + obj_Turnos[0] + ")' class='fas fa-eye fa-size_small'></span></td>");
//                                    out.print("<td align='center'><span href='Turno?opc=1&idO=" + id_orden + "&idT=" + obj_Turnos[0] + "&ver=" + 1 + "&registro=" + 5 + "&txt_bus=" + filtro + "' class='fas fa-eye fa-size_small'></span></td>");
//                                    out.print("<td align='center'><a href='Turno?opc=1&idO=" + id_orden + "&idT=" + obj_Turnos[0] + "&ver=" + 1 + "&registro=" + 5 + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Ver.png' width='22' height='22'></a></td>");
                                }
                                out.print("<td valign='top'><b>Num. Cuarentena:</b><br/>N/A<br /></td>");
                                out.print("<td valign='top'><b>Descripcion: </b><br/>N/A</td>");
                                out.print("<td valign='top'><b>Responsable: </b><br/>N/A</td>");
                                out.print("<td valign='top'><b>FECHA APROBACIÓN: </b><br/>N/A</td>");
                            }
                            out.print("</tr>");
                            // </editor-fold>
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 60);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
//                    // </editor-fold>
                }
                out.print("<div class='cleaner'></div></div>");
                //</editor-fold>
            } else if (Vregistro == 6) {
                //<editor-fold defaultstate="collapsed" desc="SEGUIMIENTO PRUEBA FUNCIONAL">
                int pruebasF = Integer.parseInt(pageContext.getRequest().getAttribute("pruebasF").toString());
                int aprobarPF = Integer.parseInt(pageContext.getRequest().getAttribute("aprobarPF").toString());
                int id_pruebaF = Integer.parseInt(pageContext.getRequest().getAttribute("id_pruebasF").toString());
                String id_RPF = pageContext.getRequest().getAttribute("txt_reg_turno").toString();
                if (filtro.equals("")) {
                    lst_turnos = jpa_turno.ConsultaTurnosSeguimiento(id_orden);
                } else {
                    lst_turnos = jpa_turno.consultaTurnosFiltroSeguimiento(id_orden, filtro);
                }
                lst_pruebasFS = jpa_pruebaF.consultaPruebaIdOrdenSeguimiento(id_orden);
                out.print("<div style='float:left;'>"
                        + "<span class='fas fa-arrow-left fa-size_small' title='Volver' onclick='CerrarPFuncional1(" + id_orden + ")'></span></div>");
                out.print("<br><br>");
                out.print("<br><div style='float: right;'>");
                out.print("<form method='post' action='Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=" + 0 + "&registro=" + 6 + "' style='margin-bottom: 0px;'>");
                out.print("<input type='text' name='txt_bus' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'></form><br/>");
                if (id_rol == 1 || id_rol == 2 || id_rol == 3) {
                    out.print("<form method='post' action='Turno?opc=1&idO=" + id_orden + "&idT=0&Sr=0&registro=6&txt_bus=0&PrF=1' id='FormPFCo'>");
                    out.print("<span class='fas fa-weight fa-size_small' title='Prueba funcional' onclick='PruebaFuncionalCo();'  style='float:right;'>");
                    out.print("<input type='hidden' name='txt_reg_turno' id='txt_reg_turno' /> ");
                    out.print("</form>");
                }
                out.print("</div>");
//                out.print("<span class='fas fa-weight fa-size_small' onclick='pruebas_funcionalSeguimiento(" + id_orden + ")'  style='float:right;'></span></div>");
                out.print("<h3>Seguimiento Pruebas funcionales</h3>");
                out.print("<div id='content-sin'>");
                if (lst_turnos == null) {
                    out.print("<h3>No se encontraron resultados</h3>");
                } else {
                    if (id_rol == 1 || id_rol == 2 || id_rol == 3) {
                        if (pruebasF != 0) {
                            //<editor-fold defaultstate="collapsed" desc="PRUEBAS FUNCIONALES">
                            List lst_lotesSinPFS = jpa_orden.consultaLoteEnsambleConsecutivoSeguimiento(id_RPF);
                            out.print("<div class='overlay' tabindex='-1' id='bloq' style='z-index: 100;opacity: 1.06; display: block;'>");
                            out.print("<fieldset class='resalta' id='Pruebas_Funcionales_seguimiento' style='visibility: visible;width:55%;left:40%'>");
                            if (id_pruebaF == 0) {
                                //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                                out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' title='Cancelar' onclick='CerrarPFuncionalSeguimiento(" + id_orden + ")'></span></div>");
                                out.print("<h3>Pruebas funcionales en Seguimiento</h3>");
                                if (lst_lotesSinPFS != null) {
                                    Object[] obj_loteConSe = (Object[]) lst_lotesSinPFS.get(0);
                                    out.print("<form method='post' action='Turno?opc=20'>");
                                    out.print("<input type='hidden' name='txt_reg_turno' id='txt_reg_turno' value='" + id_RPF + "'/> ");
                                    out.print("<input type='hidden' value='" + id_orden + "' name='idO'>");
                                    out.print("<input type='hidden' value='" + obj_loteConSe[2] + "' name='txt_lotee'>");
                                    out.print("<input type='hidden' value='" + filtro + "' name='txt_bus'>");
                                    out.print("<table class='table' align='center' style='width:50%'>");
                                    out.print("<tr>");
//                            out.print("<td><b>NOTA: </b><b class='naranja'>La </b></td>");
                                    List lst_turnosPF = jpa_turno.consultaTurnosEstadoSeguimiento((Integer) obj_loteConSe[0]);
                                    out.print("<th>LOTE ENSAMBLE</th>");
                                    out.print("<th>CONSECUTIVO</th>");
                                    out.print("</tr>");
                                    for (int i = 0; i < lst_lotesSinPFS.size(); i++) {
                                        Object[] obj_loteConSeg = (Object[]) lst_lotesSinPFS.get(i);
                                        out.print("<tr>");
                                        out.print("<td valign='top'>" + obj_loteConSeg[2] + "</td>");
                                        for (int j = 0; j < lst_turnosPF.size(); j++) {
                                            Object[] obj_conTurn = (Object[]) lst_turnosPF.get(j);
                                            out.print("<td valign='top'>" + obj_conTurn[4] + "</td>");
                                        }
                                        out.print("</tr>");
                                    }
                                    out.print("</tr>");
                                    out.print("</table>");
                                    out.print("<br><div align='center'><input type='submit' value='Registrar'></div>");
                                    out.print("</form>");
                                    out.print("<hr>");
                                } else {
                                }
                                //</editor-fold>
//                                //<editor-fold defaultstate="collapsed" desc="REGISTRAR ANTIGUO">
//                                out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' title='Cancelar' onclick='CerrarPFuncionalSeguimiento(" + id_orden + ")'></span></div>");
//                                out.print("<h3>Pruebas funcionales de Seguimiento</h3>");
//                                out.print("<form method='post' action='Turno?opc=20'>");
//                                out.print("<input type='hidden' value='" + id_orden + "' name='idO'>");
//                                out.print("<input type='hidden' value='" + filtro + "' name='txt_bus'>");
//                                out.print("<table class='table' style='width:100%'>");
//                                out.print("<tr>");
//                                out.print("<td align='center'>");
//                                out.print("<b>Fecha:</b><br /><input type='text' value='' name='txt_fecha' id='datepicker2' placeholder='Fecha' autocomplete='off' style='margin-bottom:0px'>");
//                                out.print("<script type='text/javascript'>");
//                                out.print("var validation = new LiveValidation('datepicker2');");
//                                out.print("validation.add( Validate.Presence );");
//                                out.print("</script>");
//                                out.print("</td>");
//                                out.print("<td align='center'>");
//                                out.print("<b>Hora:</b><br /><input type='time' value='' name='txt_hora' id='horaPrF-id' style='margin-bottom:0px' required>");
//                                out.print("</td>");
//                                out.print("<td align='center'>");
//                                out.print("<b>Seleccione lote</b><br/>");
//                                out.print("<select name='slt_loteCon' id='lotePrF-id'>");
//                                out.print("<option value='' style='display:none;'>SELECCIONE LOTE</option>");
//                                if (lst_lotes != null) {
//                                    for (int j = 0; j < lst_lotes.size(); j++) {
//                                        Object[] obj_lotes = (Object[]) lst_lotes.get(j);
//                                        out.print("<option value='" + obj_lotes[1] + "'>" + obj_lotes[1] + "</option>");
//                                    }
//                                }
//                                out.print("</select>");
//                                out.print("<script type='text/javascript'>");
//                                out.print("var validation = new LiveValidation('lotePrF-id');");
//                                out.print("validation.add( Validate.Presence );");
//                                out.print("</script>");
//                                out.print("</td>");
//                                out.print("</tr>");
//                                out.print("<tr>");
//                                out.print("<td colspan='3' align='center'><input type='submit' value='Guardar'></td>");
//                                out.print("</tr>");
//                                out.print("</table>");
//                                out.print("</form>");
//                                //</editor-fold>
                            } else if (aprobarPF == 0) {
                                //<editor-fold defaultstate="collapsed" desc="resultado">
                                List lst_prueba = jpa_pruebaF.consultaPruebaIdSeguimiento(id_pruebaF);
                                Object[] obj_prueba = (Object[]) lst_prueba.get(0);
                                out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' title='Cancelar' onclick='CerrarPFuncionalSeguimiento(" + id_orden + ")'></span></div>");
                                out.print("<h3>Resultado Prueba Funcional <b>" + obj_prueba[2] + "</b></h3>");
                                out.print("<form method='post' action='Turno?opc=21'>");
                                out.print("<input type='hidden' value='" + id_orden + "' name='idO'>");
                                out.print("<input type='hidden' value='" + id_turno + "' name='idT'>");
                                out.print("<input type='hidden' value='" + filtro + "' name='txt_bus'>");
                                out.print("<input type='hidden' value='" + id_pruebaF + "' name='idPF'>");
                                out.print("<input type='hidden' value='" + obj_prueba[2] + "' name='txt_lote'>");
                                out.print("<input type='hidden' name='txt_reg_turno' id='txt_reg_turno' value='" + obj_prueba[13] + "'/> ");
                                out.print("<table class='table2' style='width:100%'>");
                                out.print("<tr>");
                                out.print("<td align='center'>");
                                out.print("<b>Fecha:</b><br /><input type='text' value='' name='txt_fecha' id='datepicker2' placeholder='Fecha' autocomplete='off' style='margin-bottom:0px'>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('datepicker2');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                out.print("</td>");
                                out.print("<td align='center'>");
                                out.print("<b>Hora:</b><br /><input type='time' value='' name='txt_hora' id='hora-id' style='margin-bottom:0px' required>");
                                out.print("</td>");
                                out.print("<td align='center'>");
                                out.print("<b>Resultado</b><br/>");
                                out.print("<select name='slt_resultado' id='resultadoPrF-id'>");
                                out.print("<option value='' style='display:none;'>SELECCIONE RESULTADO</option>");
                                out.print("<option value='1'>CUMPLE</option>");
                                out.print("<option value='2'>NO CUMPLE</option>");
                                out.print("</select>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('resultadoPrF-id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                out.print("</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td colspan='3' align='center'><input type='submit' value='Guardar'></td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("</form>");
                                //</editor-fold>
                            }
                            //<editor-fold defaultstate="collapsed" desc="CONSULTA PRUEBA FUNCIONAL">
                            if (lst_pruebasFS != null) {
                                out.print("<div id='NavPosicion2'></div>");
                                out.print("<table class='table' style='width:100%' id='resultadosPBF'>");
                                out.print("<tr>");
                                out.print("<th>Lote</th>");
                                out.print("<th>Fecha Inicio</th>");
                                out.print("<th>Usuario Inicio</th>");
                                out.print("<th>Resultado</th>");
                                out.print("<th>Fecha resultado</th>");
                                out.print("<th>Usuario resultado</th>");
                                out.print("</tr>");
                                for (int j = 0; j < lst_pruebasFS.size(); j++) {
                                    Object[] obj_pruebaF = (Object[]) lst_pruebasFS.get(j);
                                    out.print("<tr>");
                                    out.print("<td>" + obj_pruebaF[2] + "| " + obj_pruebaF[14] + "");
                                    out.print("<td>" + obj_pruebaF[3] + "</td>");
                                    out.print("<td>" + obj_pruebaF[4] + "</td>");
                                    if ((Integer) obj_pruebaF[5] == 0) {
                                        if (Integer.parseInt(obj_pruebaF[13].toString()) == 1) {
                                            out.print("<td colspan='3' align='center'><a href='Turno?opc=1&idO=" + id_orden + "&idT=0&Sr=0&registro=6&txt_bus=&PrF=1&idPF=" + obj_pruebaF[0] + "' style='text-decoration:none'><b class='naranja'>Ingresar resultado</b></a></td>");
                                        } else {
                                            out.print("<td colspan='3' align='center'><b class='naranja'>No se registrado el resultado</b></td>");
                                        }
                                    } else {
                                        out.print("<td align='center'>" + (((Integer) obj_pruebaF[5] == 1) ? "<b class='" + ((obj_pruebaF[8] != null) ? "naranja" : "verde") + "'>Cumple</b>" : ((id_rol == 1 || id_rol == 2) ? "<a href='Turno?opc=1&idO=" + id_orden + "&idT=0&Sr=0&registro=0&txt_bus=&PrF=1&idPF=" + obj_pruebaF[0] + "&aPF=1' style='text-decoration:none'><b class='rojo'>No Cumple</b></a>" : "<b class='rojo'>No Cumple</b>")) + "</td>");
                                        out.print("<td>" + obj_pruebaF[6] + "</td>");
                                        out.print("<td>" + obj_pruebaF[7] + "</td>");
                                    }
                                    out.print("</tr>");
                                }
                                out.print("</table>");
                                out.print("<script type='text/javascript'>");
                                out.print("var pager = new Pager2('resultadosPBF', 10);");
                                out.print("pager.init();");
                                out.print("pager.showPageNav('pager','NavPosicion2');");
                                out.print("pager.showPage(1);");
                                out.print("</script>");
                            }
                            //</editor-fold>
                            out.print("</fieldset>");
                            out.print("</div>");
                            //</editor-fold>
                        }
                    }
                    out.print("<div id='NavPosicion'></div>");
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA TURNO">
                    if (lst_turnos == null) {
                        out.print("<h3>No se encontraron resultados</h3>");
                    } else {
                        out.print("<div id='NavPosicion'></div>");
                        //<editor-fold defaultstate="collapsed" desc="TABLA TURNO">
                        out.print("<table class='table' id='resultados' style='width:100%;'>");
                        for (int i = 0; i < lst_turnos.size(); i++) {
                            Object[] obj_turno = (Object[]) lst_turnos.get(i);
                            lst_CantDimensionalT = jpa_DimensionalT.ConsultaCantidadTotalDimensional((Integer) obj_turno[0]);
                            String[] fechahora = obj_turno[1].toString().split(" ");
                            String hora = fechahora[1].toString();
                            out.print("<td colspan='11'></td>");
                            out.print("<tr>");
                            out.print("<td><center><div class='girarD'><b>" + obj_turno[25] + "</b>");
                            if ((Integer) obj_turno[19] == 1) {
                                out.print("<br><b style='color:green;'>resumido</b></center></td>");
                            } else {
                                out.print("<br><b class='naranja'>Sin resumir</b></center></td>");
                            }
                            out.print("<td><center><b><br>SEGUIMIENTO</b><br/><br/>");
                            if (obj_turno[33] == null) {
                                out.print("<input type='radio' name='Masivo' onclick='MasivoTurno(this.value)' id='MasivoTurno-" + obj_turno[0] + "' value='" + obj_turno[0] + "' > | ");
                            }
                            out.print("<span class='fas fa-eye fa-size_small' onclick='Ver(" + id_orden + "," + obj_turno[0] + ")'></span><br>");
                            out.print("<div class='estadoseguimiento'></div></center>");
                            out.print("<td valing='top' style='width:12%;'><b>LOTE ENSAMBLE: </b><br>" + obj_turno[10] + "");
                            List lst_turnosE = jpa_turno.consultaTurnosEstadoSeguimiento((Integer) obj_turno[0]);
                            Object[] obj_turnoR = (Object[]) lst_turnosE.get(0);
                            out.print("<br/><b>CONSECUTIVO: </b><br/>" + obj_turnoR[4] + "</td>");
                            out.print("<td valing='top' style='width:12%;'><b>TURNO:</b>" + obj_turno[3] + ""
                                    + "<br><b>FECHA: </b>" + obj_turno[2] + "<br /><b>HORA: </b>" + hora + "");
                            out.print("</td>");
                            out.print("<td valing='top'  style='width:12%;'><b>LOTE BASE: </b><br>");
                            out.print("<br><b>C: </b>" + obj_turno[6] + "<br /><b>P: </b>" + obj_turno[7] + "</td>");
                            out.print("<td valing='top'  style='width:12%;'><b>LOTE PISTON: </b><br>");
                            out.print("<br><b>C: </b>" + obj_turno[8] + "<br /><b>P: </b>" + obj_turno[9] + "</td>");
                            out.print("<td valing='top'  style='width:9%;'>");
                            out.print("<b>MÁQUINA: </b><br><br>" + obj_turno[22] + "");
                            out.print("<br><br><b>MOLDE: </b>");
                            if (obj_turno[26] != null) {
                                out.print("" + obj_turno[26] + "");
                            } else {
                                out.print("N/A");
                            }
                            out.print("</td>");
                            //<editor-fold defaultstate="collapsed" desc="PRUEBA FUNCIONAL DESCRIPCION">
                            if (obj_turno[32] != null) {
                                if ((Integer) obj_turno[33] != 0) {
                                    out.print("<div class='tooltip_templates'>");
                                    out.print("<span id='tooltip_content" + i + "'>");
                                    out.print("" + (((Integer) obj_turno[33] == 1) ? ((obj_turno[36] != null) ? "<b>Aprobado</b>" : "<b class='verde'>Cumple</b>") : "<b class='rojo'>No Cumple</b>") + " | <b>Fecha: </b>" + obj_turno[34] + " | <b>Responsable: </b>" + obj_turno[35] + "");
                                    if (obj_turno[36] != null) {
                                        out.print("<hr/><b>Justificacion: </b>" + obj_turno[36] + "<hr/>");
                                        out.print("" + (((Integer) obj_turno[37] == 1) ? "<b class='verde'>Cumple</b>" : "<b class='rojo'>No Cumple</b>") + " | <b>Fecha: </b>" + obj_turno[38] + " | <b>Responsable: </b>" + obj_turno[39] + "");
                                    }
                                    out.print("</span>");
                                    out.print("</div>");
                                }
                            }
                            //</editor-fold>
                            out.print("</td>");
                            out.print("<td valing='top' style='width: 12%;'><b>DIMENSIONAL: </b>");
                            lst_CantDimensional = jpa_dimensional.ConsultaCantidad((Integer) obj_turno[0]);
                            Object[] obj_cantidadD = (Object[]) lst_CantDimensional.get(0);
                            out.print("" + obj_cantidadD[0] + "");
                            out.print("<br><b>DEFECTO(S): </b>");
                            lst_CantDefecto = jpa_defecto.consultarDefectoTurno((Integer) obj_turno[0]);
                            int var = 0;
                            for (int k = 0; k < lst_CantDefecto.size(); k++) {
                                Object[] obj = (Object[]) lst_CantDefecto.get(k);
                                if (obj[5] != null) {
                                    var = Integer.parseInt(obj[5].toString());
                                    suma = suma + Integer.parseInt(obj[5].toString());
                                }
                            }
                            if (var != 0) {
                                out.print("" + suma + "");
                                suma = 0;
                            } else {
                                out.print("0");
                            }
                            out.print("<br><b>TOMAS: </b>");
                            for (int j = 0; j < lst_CantDimensionalT.size(); j++) {
                                Object[] num = (Object[]) lst_CantDimensionalT.get(j);
                                count = count + Integer.parseInt(num[1].toString());
                            }
                            out.print("" + count + "</td>");
                            count = 0;
                            out.print("</td>");
                            out.print("<td valing='top' style='style='width:12%;'><b>REPONSABLE: </b><br>" + obj_turno[24] + "</br>");
                            if (obj_turno[32] != null) {
                                out.print("<br><br><b class='tooltip' data-tooltip-content='#tooltip_content" + i + "'>Pru. Estaqueidad: </b><br>" + (((Integer) obj_turno[33] == 1) ? "<b class='" + ((obj_turno[36] != null) ? "naranja" : "verde") + "'>Cumple</b>" : (((Integer) obj_turno[33] == 0) ? "</br><b class='naranja'>No se ha registrado</b>" : "<b class='rojo'>No cumple</b>") + "</td>"));
                            } else {
                                out.print("<br><br><b>Pru. Estaqueidad: </b><br><b class='naranja'>No se ha registrado</b></b></td>");
                            }
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        //</editor-fold>

                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 40);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    //</editor-fold>
                }
                out.print("</div>");
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="MODULO TURNO">
                if (!filtro.equals("")) {
                    lst_turnos = jpa_turno.consultaTurnosFiltro(id_orden, filtro);
                } else {
                    lst_turnos = jpa_turno.consultaTurnos(id_orden);
                }
                int serial = Integer.parseInt(pageContext.getRequest().getAttribute("serial").toString());
                int pruebasF = Integer.parseInt(pageContext.getRequest().getAttribute("pruebasF").toString());
                int aprobarPF = Integer.parseInt(pageContext.getRequest().getAttribute("aprobarPF").toString());
                int id_pruebaF = Integer.parseInt(pageContext.getRequest().getAttribute("id_pruebasF").toString());
                int id_prbFll = Integer.parseInt(pageContext.getRequest().getAttribute("id_prbFll").toString());
                String id_RPF = pageContext.getRequest().getAttribute("txt_reg_turno").toString();
                List lst_orden = jpa_orden.consultaOrdenId(id_orden);
                Object[] obj_orden = (Object[]) lst_orden.get(0);
                lst_maquinas = jpa_maquina.consultaMaquinas();
                lst_calibradores = Jpa_metrologia.calibradores();
                List lst_lotes = jpa_orden.consultaLoteEnsamble(id_orden);
                lst_pruebasF = jpa_pruebaF.consultaPruebaIdOrden(id_orden);
                lst_prbFll = jpa_pruebaF.consultaPruebaFallidaIdOrden(id_orden);
                if (id_rol == 1 || id_rol == 2 || id_rol == 3) {
                    if (serial == 0 && id_turno != 0) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR TURNO">
                        lst_turno = jpa_turno.consultaTurnoId(id_turno);
                        Object[] obj_turno = (Object[]) lst_turno.get(0);
                        out.print("<div class='overlay' tabindex='-1' id='Convecion3' style='z-index: 100;opacity: 1.06; display: block;'>");
                        out.print("<fieldset class='resalta' id='registro_turno' style='overflow:hidden;width:55%;height:60%;left:40%'>");
                        out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' title='Cancelar' onclick='CerrarPFuncional1(" + id_orden + ")'></span></div>");
                        out.print("<h3>Modificar turno</h3>");
                        out.print("<table class='table' id='resultados' style='width:100%;'>");
                        out.print("<form method='post' action='Turno?opc=3' onsubmit='registroT();'>");
                        out.print("<input type='hidden' name='idO' value='" + id_orden + "' />");
                        out.print("<input type='hidden' name='idT' value='" + id_turno + "' />");
                        out.print("<input type='hidden' name='slt_registro' value='" + obj_orden[8] + "' />");
                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "' />");
                        out.print("<input type='hidden' name='slt_registro' value='" + obj_turno[25] + "' />");
                        out.print("<tr><td><b>Fecha turno:</b><br/>");
                        out.print("<input type='text' name='txt_fecha' id='datepicker' value='" + obj_turno[2] + "' placeholder='Fecha' autocomplete='off'><br/>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('datepicker');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script></td>");
                        out.print("<td><b>Turno:</b><br/>");
                        out.print("<select name='slt_turno' id='turno-id'>");
                        out.print("<option value='" + obj_turno[3] + "' style='display:none;'>Turno " + obj_turno[3] + "</option>");
                        out.print("<option value='1'>Turno 1</option>");
                        out.print("<option value='2'>Turno 2</option>");
                        out.print("<option value='3'>Turno 3</option>");
                        out.print("<option value='1/12'>Turno 1 12hr</option>");
                        out.print("<option value='2/12'>Turno 2 12hr</option>");
                        out.print("</select>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('turno-id');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script><br /></td>");
                        out.print("<td><b>Responsable:</b><br/>");
                        out.print("<input type='text' value='" + Usuario + "' readonly='true'></td>");
                        out.print("<tr><td><b>Lote ensamble:</b><br/>");
                        out.print("<input type='text' name='txt_loteEnsamble' id='loteEnsamble-id' value='" + obj_turno[10] + "' placeholder='Lote Ensamble'><br/>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('loteEnsamble-id');");
                        out.print("validation.add( Validate.LoteEnsamble );");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script></td>");
                        out.print("<td><b>Lote Base C:</b><br/>");
                        out.print("<input type='text' name='txt_lotebasec' id='loteBaseC-id' value='" + obj_turno[6] + "' placeholder='Lote Base C'>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('loteBaseC-id');");
                        out.print("validation.add( Validate.LoteCompuesto );");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script></td>");
                        out.print("<td><b>Lote Base P:</b><br/>");
                        out.print("<input type='text' name='txt_lotebasep' id='loteBaseP-id' value='" + obj_turno[7] + "' placeholder='Lote Base P'><br/>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('loteBaseP-id');");
                        out.print("validation.add( Validate.LoteProducto );");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script></td></tr>");
                        if (obj_orden[8].toString().equals("R-GC-116")) {
                            out.print("<tr><td><b>Molde:</b><br/>");
                            out.print("<input type='text' name='txt_molde' id='molde-id' value='" + obj_turno[26] + "' placeholder='Molde'><br/>");
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('molde-id');");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script></td>");
                        }
                        out.print("<td><b>Lote Piston C:</b></br>");
                        out.print("<input type='text' name='txt_lotepistonc' id='lotePistonC-id' value='" + obj_turno[8] + "' placeholder='Lote Piston C'><br/>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('lotePistonC-id');");
                        out.print("validation.add( Validate.LoteCompuesto );");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script></td>");
                        out.print("<td><b>Lote Piston P:</b></br>");
                        out.print("<input type='text' name='txt_lotepistonp' id='lotePistonP-id' value='" + obj_turno[9] + "' placeholder='Lote Piston P'><br/>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('lotePistonP-id');");
                        out.print("validation.add( Validate.LoteProducto );");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script></td></tr>");
                        out.print("<tr><td><b>Maquina:</b><br/>");
                        out.print("<select name='idM' id='maquina-id' >");
                        out.print("<option value='" + obj_turno[21] + "' style='display:none;'>" + obj_turno[22] + "</option>");
                        for (int i = 0; i < lst_maquinas.size(); i++) {
                            Object[] obj_maquinas = (Object[]) lst_maquinas.get(i);
                            if ((Integer) obj_maquinas[4] == 1) {
                                out.print("<option value='" + obj_maquinas[0] + "'>" + obj_maquinas[2].toString().toUpperCase() + "</option>");
                            }
                        }
                        out.print("</select></td></tr>");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('maquina-id');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script></td>");
                        out.print("</table>");
                        out.print("<center><input type='submit' id='btsubmit' value='Guardar'>");
                        out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "        </div></center>");
                        out.print("</form>");
                        out.print("</fieldset></div>");
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR TURNO">
                        List lst_Ultturno = (List) pageContext.getRequest().getAttribute("LstUltTurno");
                        if (lst_Ultturno == null) {
                            //<editor-fold defaultstate="collapsed" desc="nuevo turno">
                            if (obj_orden[4].equals("abierto")) {
                                out.print("<div class='overlay' tabindex='-1' id='Convecion2' style='z-index: 100;opacity: 1.06; display: none;'>");
                                out.print("<fieldset class='resalta' id='registro_turno'  style=' overflow-y:hidden; overflow-x:hidden; width:55%; height:83%; top:37%;left:40%'>");
                                out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' title='Cancelar' onclick='CerrarPFuncional1(" + id_orden + ")'></span></div>");
                                out.print("<table class='table' id='resultados' style='width:100%;'>");
                                if (lst_prbFll != null) {
                                    out.print("<h3>Opciones de turnos registrados</h3>");
                                    out.print("<td><b>Turno prueba fallida</b>");
                                    out.print("<form method='post' action='Turno?opc=11'>");
                                    out.print("<input type='hidden' name='idO' value='" + id_orden + "' />");
                                    out.print("<br><br><b>Seleccione lote</b><br/>");
                                    out.print("<select name='slt_loteFall' id='loteFall-id' onchange='this.form.submit()'>");
                                    out.print("<option value='' style='display:none;'>SELECCIONE LOTE</option>");
                                    for (int j = 0; j < lst_prbFll.size(); j++) {
                                        Object[] obj_prbFll = (Object[]) lst_prbFll.get(j);
                                        out.print("<option value='" + obj_prbFll[3] + "//" + obj_prbFll[0] + "'>" + obj_prbFll[3] + "-&nbsp;Inicio:&nbsp;" + obj_prbFll[5] + "-&nbsp;Fin:&nbsp;" + obj_prbFll[6] + "</option>");
                                    }
                                    out.print("</select>");
                                    out.print("</form></td>");
                                }
                                if (lst_lotes != null) {
                                    out.print("<td><b>Turno consecutivo</b>");
                                    out.print("<form method='post' action='Turno?opc=11' name='fomrCons'>");
                                    out.print("<input type='hidden' name='idO' value='" + id_orden + "' />");
                                    out.print("<input type='hidden' name='slt_loteFall' value='' />");
                                    out.print("<br><br><b>Seleccione lote</b><br/>");
                                    out.print("<select name='slt_loteCon' id='loteCon-id' onchange='this.form.submit()'>");
                                    out.print("<option value='' style='display:none;'>SELECCIONE LOTE</option>");
                                    for (int j = 0; j < lst_lotes.size(); j++) {
                                        Object[] obj_lotes = (Object[]) lst_lotes.get(j);
                                        out.print("<option value='" + obj_lotes[1] + "'>" + obj_lotes[1] + "</option>");
                                    }
                                    out.print("</select>");
                                    out.print("</form></td>");
                                }
                            }
                            out.print("<form method='post' action='Turno?opc=2' onsubmit='registroT();'>");
                            out.print("<input type='hidden' name='idO' value='" + id_orden + "' />");
                            out.print("<input type='hidden' name='slt_registro' value='" + obj_orden[8] + "' />");
                            out.print("</table><br>");
                            if (obj_orden[4].equals("abierto")) {
                                out.print("<table class='table' id='resultados' style='width:100%;'>");
                                out.print("<h3>Nuevo turno</h3>");
                                out.print("<tr><td><b>Fecha turno:</b><br/>");
                                out.print("<input type='text' name='txt_fecha' id='datepicker' placeholder='Fecha' autocomplete='off'><br/>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('datepicker');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td>");
                                out.print("<td><b>Turno:</b><br/>");
                                out.print("<select name='slt_turno' id='turno-id'>");
                                out.print("<option value='' style='display:none;'>SELECCIONE TURNO</option>");
                                out.print("<option value='1'>Turno 1</option>");
                                out.print("<option value='2'>Turno 2</option>");
                                out.print("<option value='3'>Turno 3</option>");
                                out.print("<option value='1/12'>Turno 1 12hr</option>");
                                out.print("<option value='2/12'>Turno 2 12hr</option>");
                                out.print("</select>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('turno-id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script><br /></td>");
                                out.print("<td><b>Responsable:</b><br/>");
                                out.print("<input type='text' value='" + Usuario + "' readonly='true'></td></tr>");
                                out.print("<tr><td><b>Orden de producción:</b><br/>");
                                out.print("<input type='text' value='" + obj_orden[2] + "' readonly='true'></td>");
                                out.print("<td><b>Ficha técnica:</b><br/>");
                                out.print("<input type='text' value='" + obj_orden[6] + "' readonly='true'></td>");
                                out.print("<td><b>Producto:</b><br/>");
                                out.print("<input type='text' value='" + obj_orden[7] + "' readonly='true'></td></tr>");
                                out.print("<tr><td><b>Lote ensamble:</b><br/>");
                                out.print("<input type='text' name='txt_loteEnsamble' id='loteEnsamble-id' placeholder='Lote Ensamble' onkeyup='NoEspacios(this.value,this);' onchange='javascript:this.value=this.value.toUpperCase();'>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('loteEnsamble-id');");
                                out.print("validation.add( Validate.LoteEnsamble );");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td>");
                                out.print("<td><b>Lote Base C:</b><br/>");
                                out.print("<input type='text' name='txt_lotebasec' id='loteBaseC-id' placeholder='Lote Base C' onkeyup='NoEspacios(this.value,this);' onchange='javascript:this.value=this.value.toUpperCase();'>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('loteBaseC-id');");
                                out.print("validation.add( Validate.LoteCompuesto );");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td>");
                                out.print("<td><b>Lote Base P:</b><br/>");
                                out.print("<input type='text' name='txt_lotebasep' id='loteBaseP-id' placeholder='Lote Base P' onkeyup='NoEspacios(this.value,this);' onchange='javascript:this.value=this.value.toUpperCase();'>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('loteBaseP-id');");
                                out.print("validation.add( Validate.LoteProducto );");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td></tr>");
                                if (obj_orden[8].toString().equals("R-GC-116")) {
                                    out.print("<tr><td><b>Molde:</b><br/>");
                                    out.print("<input type='text' name='txt_molde' id='molde-id' placeholder='Molde'>");
                                    out.print("<script type='text/javascript'>");
                                    out.print("var validation = new LiveValidation('molde-id');");
                                    out.print("validation.add( Validate.Presence );");
                                    out.print("</script></td>");
                                }
                                out.print("<td><b>Lote Piston C:</b><br/>");
                                out.print("<input type='text' name='txt_lotepistonc' id='lotePistonC-id' placeholder='Lote Piston C' onkeyup='NoEspacios(this.value,this);' onchange='javascript:this.value=this.value.toUpperCase();'>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('lotePistonC-id');");
                                out.print("validation.add( Validate.LoteCompuesto );");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td>");
                                out.print("<td><b>Lote Piston P:</b><br/>");
                                out.print("<input type='text' name='txt_lotepistonp' id='lotePistonP-id' placeholder='Lote Piston P' onkeyup='NoEspacios(this.value,this);' onchange='javascript:this.value=this.value.toUpperCase();'>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('lotePistonP-id');");
                                out.print("validation.add( Validate.LoteProducto );");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td></tr>");
                                out.print("<td><b>Maquina:</b><br/>");
                                out.print("<select name='idM' id='maquina-id' >");
                                out.print("<option value='' style='display:none;'>SELECCIONE MÁQUINA</option>");
                                for (int i = 0; i < lst_maquinas.size(); i++) {
                                    Object[] obj_maquinas = (Object[]) lst_maquinas.get(i);
                                    if ((Integer) obj_maquinas[4] == 1) {
                                        out.print("<option value='" + obj_maquinas[0] + "'>" + obj_maquinas[2].toString().toUpperCase() + "</option>");
                                    }
                                }
                                out.print("</select><br/><br/>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('maquina-id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td>");
                                out.print("<td><b>Aplica Despeje:</b><br/>");
                                out.print("SI<input type='radio' id='depeje-id' name='rdb_despeje' value='1'>&nbsp;&nbsp;");
                                out.print("NO<input type='radio' id='depeje-id' name='rdb_despeje' value='0' checked><br /><br /></td></tr>");
                                out.print("</table>");
                                out.print("<center><input type='submit' id='btsubmit' value='Guardar'>");
                                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                        + "          <div></div>\n"
                                        + "          <div></div>\n"
                                        + "          <div></div>\n"
                                        + "        </div></center>");
                            }
                            out.print("</form>");
                            out.print("</fieldset>");
                            out.print("</div>");
                            //</editor-fold>
                        } else {
                            Object[] obj_Ultturno = (Object[]) lst_Ultturno.get(0);
                            //<editor-fold defaultstate="collapsed" desc="consecutivo">
                            if (obj_orden[4].equals("abierto")) {
                                out.print("<div class='overlay' tabindex='-1' id='Convecion2' style='z-index: 100;opacity: 1.06; display: block;'>");
                                out.print("<fieldset class='resalta' id='registro_turno' style=' overflow-y:hidden; overflow-x:hidden; width:55%; height:83%; top:37%;left:40%'>");
                                out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' title='Cancelar' onclick='CerrarPFuncional1(" + id_orden + ")'></span></div>");
                                out.print("<table class='table' id='resultados' style='width:100%;'>");
                                out.print("<h3>Opciones de turnos registrados</h3>");
                                if (lst_prbFll != null) {
                                    out.print("<form method='post' action='Turno?opc=11'>");
                                    out.print("<td><b>Turno prueba fallida</b>");
                                    out.print("<input type='hidden' name='idO' value='" + id_orden + "' />");
                                    out.print("<br><br><b>Seleccione lote</b><br/>");
                                    out.print("<select name='slt_loteFall' id='loteFall-id' onchange='this.form.submit()'>");
                                    out.print("<option value='' style='display:none;'>SELECCIONE LOTE</option>");
                                    for (int j = 0; j < lst_prbFll.size(); j++) {
                                        Object[] obj_prbFll = (Object[]) lst_prbFll.get(j);
                                        out.print("<option value='" + obj_prbFll[3] + "//" + obj_prbFll[0] + "'>" + obj_prbFll[3] + "-&nbsp;Inicio:&nbsp;" + obj_prbFll[5] + "-&nbsp;Fin:&nbsp;" + obj_prbFll[6] + "</option>");
                                    }
                                    out.print("</select>");
                                    out.print("</form></td>");
                                }
                                if (lst_lotes != null) {
                                    out.print("<td><b>Turno consecutivo</b>");
                                    out.print("<form method='post' action='Turno?opc=11' name='fomrCons'>");
                                    out.print("<input type='hidden' name='idO' value='" + id_orden + "' />");
                                    out.print("<input type='hidden' name='slt_loteFall' value='' />");
                                    out.print("<br><b>Seleccione lote</b><br/>");
                                    out.print("<select name='slt_loteCon' id='loteCon-id' onchange='this.form.submit()'>");
                                    out.print("<option value='' style='display:none;'>SELECCIONE LOTE</option>");
                                    for (int j = 0; j < lst_lotes.size(); j++) {
                                        Object[] obj_lotes = (Object[]) lst_lotes.get(j);
                                        out.print("<option value='" + obj_lotes[1] + "'>" + obj_lotes[1] + "</option>");
                                    }
                                    out.print("</select>");
                                    out.print("</form>");
                                    out.print("</td>");
                                }
                            }
                            Object[] obj_UltTurno = (Object[]) lst_Ultturno.get(0);
                            out.print("<form method='post' action='Turno?opc=2' onsubmit='registroT();'>");
                            out.print("<input type='hidden' name='idO' value='" + id_orden + "' />");
                            out.print("<input type='hidden' name='slt_registro' value='" + obj_orden[8] + "' />");
                            out.print("<input type='hidden' name='txt_prbFll' value='" + id_prbFll + "' />");
                            out.print("</table>");
                            if (obj_orden[4].equals("abierto")) {
                                out.print("<table class='table' id='resultados' style='width:100%;'>");
                                out.print("<br><h3>Nuevo turno</h3>");
                                out.print("<tr><td><b>Fecha turno:</b><br/>");
                                out.print("<input type='text' name='txt_fecha' id='datepicker' placeholder='Fecha' autocomplete='off'>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('datepicker');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td>");
                                out.print("<td><b>Turno:</b><br/>");
                                out.print("<select name='slt_turno' id='turno-id'>");
                                out.print("<option value='' style='display:none;'>SELECCIONE TURNO</option>");
                                out.print("<option value='1'>Turno 1</option>");
                                out.print("<option value='2'>Turno 2</option>");
                                out.print("<option value='3'>Turno 3</option>");
                                out.print("<option value='1/12'>Turno 1 12hr</option>");
                                out.print("<option value='2/12'>Turno 2 12hr</option>");
                                out.print("</select>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('turno-id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script><br /></td>");
                                out.print("<td><b>Responsable:</b><br/>");
                                out.print("<input type='text' value='" + Usuario + "' readonly='true'></td>");
                                out.print("<tr><td><b>Orden de producción:</b><br/>");
                                out.print("<input type='text' value='" + obj_orden[2] + "' readonly='true'></td>");
                                out.print("<td><b>Ficha técnica:</b><br/>");
                                out.print("<input type='text' value='" + obj_orden[6] + "' readonly='true'></td>");
                                out.print("<td><b>Producto:</b><br/>");
                                out.print("<input type='text' value='" + obj_orden[7] + "' readonly='true'></td</tr>");
                                out.print("<tr><td><b>Lote ensamble " + ((id_prbFll != 0) ? "FALL" : "") + ":</b><br/>");
                                out.print("<input type='text' name='txt_loteEnsamble' id='loteEnsamble-id' value='" + obj_UltTurno[8] + " 'placeholder='Lote Ensamble'  onkeyup='NoEspacios(this.value,this);' onchange='javascript:this.value=this.value.toUpperCase();' " + ((id_prbFll != 0) ? "readonly" : "") + ">");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('loteEnsamble-id');");
                                out.print("validation.add( Validate.LoteEnsamble );");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td>");
                                out.print("<td><b>Lote Base C:</b><br/>");
                                out.print("<input type='text' name='txt_lotebasec' id='loteBaseC-id' value='" + obj_UltTurno[4] + "' placeholder='Lote Base C' onkeyup='NoEspacios(this.value,this);' onchange='javascript:this.value=this.value.toUpperCase();'>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('loteBaseC-id');");
                                out.print("validation.add( Validate.LoteCompuesto );");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td>");
                                out.print("<td><b>Lote Base P:</b><br/>");
                                out.print("<input type='text' name='txt_lotebasep' id='loteBaseP-id' value='" + obj_UltTurno[5] + "' placeholder='Lote Base P' onkeyup='NoEspacios(this.value,this);' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('loteBaseP-id');");
                                out.print("validation.add( Validate.LoteProducto );");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td></tr>");
                                out.print("<tr><td><b>Molde:</b><br/>");
                                out.print("<input type='text' name='txt_molde' id='molde-id' value='" + obj_UltTurno[15] + "' placeholder='Molde'>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('molde-id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td>");
                                out.print("<td><b>Lote Piston C:</b><br/>");
                                out.print("<input type='text' name='txt_lotepistonc' id='lotePistonC-id' value='" + obj_UltTurno[6] + "' placeholder='Lote Piston C' onkeyup='NoEspacios(this.value,this);' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('lotePistonC-id');");
                                out.print("validation.add( Validate.LoteCompuesto );");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td>");
                                out.print("<td><b>Lote Piston P:</b><br/>");
                                out.print("<input type='text' name='txt_lotepistonp' id='lotePistonP-id' value='" + obj_UltTurno[7] + "' placeholder='Lote Piston P' onkeyup='NoEspacios(this.value,this);' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('lotePistonP-id');");
                                out.print("validation.add( Validate.LoteProducto );");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td></tr>");
                                if (id_prbFll != 0) {
                                    out.print("<b class='naranja'>Usted a escogido una prueba fallida, por lo tanto no se puede cambiar el lote de ensamble solo se puede generar un nuevo consecutivo. </b><br><br>");
                                }
                                out.print("<tr><td><b>Maquina:</b><br/>");
                                out.print("<select name='idM' id='maquina-id' >");
                                out.print("<option value='' style='display:none;'>SELECCIONE MÁQUINA</option>");
                                for (int i = 0; i < lst_maquinas.size(); i++) {
                                    Object[] obj_maquinas = (Object[]) lst_maquinas.get(i);
                                    if ((Integer) obj_maquinas[4] == 1) {
                                        out.print("<option value='" + obj_maquinas[0] + "'>" + obj_maquinas[2].toString().toUpperCase() + "</option>");
                                    }
                                }
                                out.print("</select><br/><br/>");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('maquina-id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script></td>");
                                out.print("<td><b>Aplica Despeje:</b><br/>");
                                out.print("SI<input type='radio' id='depeje-id' name='rdb_despeje' value='1'>&nbsp;&nbsp;");
                                out.print("NO<input type='radio' id='depeje-id' name='rdb_despeje' value='0' checked><br /></td>");
                                out.print("</table>");
                                out.print("<center><input type='submit' id='btsubmit' value='Guardar'>");
                                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                        + "          <div></div>\n"
                                        + "          <div></div>\n"
                                        + "          <div></div>\n"
                                        + "        </div>");
                            }
                            out.print("</form>");
                            out.print("</fieldset>");
                            out.print("</div>");
                            //</editor-fold>
                        }
                        //</editor-fold>
                    }
                }
                out.print("<div class='cleaner'></div>");
                out.print("<div id='sin_content'>");
                if (serial != 0) {
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA REGISTRO DE SERIALES">
                    lst_turno = jpa_turno.consultaTurnoId(id_turno);
                    Object[] obj_turno = (Object[]) lst_turno.get(0);
                    out.print("<div class='overlay' tabindex='-1' id='Convecion5' style='z-index: 100;opacity: 1.06; display: block;'>");
                    out.print("<fieldset class='resalta' id='registro_turno' style='width:55%;height:auto;left:40%'>");
                    out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' title='Cancelar' onclick='Cerrar(" + id_orden + ")'></span></div>");
                    out.print("<h3>Seriales</h3>");
                    out.print("<div style='float:right;'><input name='Txt_filtro' type='text' onkeyup='FiltrarS()' id='Txt_filtro' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'></div><br/>");
                    out.print("<div id='NavPosicion2'></div>");
                    out.print("<table class='table' id='seriales' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<th colspan='2'>Serial</th>");
                    out.print("<th>Instrumento</th>");
                    out.print("<th colspan='2'>Fecha Inspeccion/Verificacion</th>");
                    out.print("<th colspan='2'>Fecha Verificacion/Calibracion</th>");
                    out.print("</tr>");

                    for (int p = 0; p < lst_calibradores.size(); p++) {
                        String[] arg_seriales = lst_calibradores.toString().replace("[", "").replace("]", "").split("////");

                        for (int i = 0; i < arg_seriales.length; i++) {
                            Object[] obj_calibradores = arg_seriales[p].toString().split("---");
                            String[] TipoV = obj_calibradores[13].toString().split("-");
                            if (obj_calibradores[11].toString().equals("0")) {
                                out.print("<tr class='rojo'>");
                                out.print("<td align='center'><input type='checkbox' value=" + "[" + obj_calibradores[3] + "/" + obj_calibradores[14] + "/" + obj_calibradores[15] + "]" + " onclick='seleccionarS(this.value,this);' disabled></td>");
                            } else {
                                out.print("<tr>");
                                if (obj_turno[20] != null) {
                                    if (obj_turno[20].toString().contains(obj_calibradores[3].toString())) {
                                        out.print("<td align='center'><input type='checkbox' value=" + "[" + obj_calibradores[3] + "/" + obj_calibradores[14] + "/" + obj_calibradores[15] + "]" + " onclick='seleccionarS(this.value,this);' checked></td>");
                                    } else {
                                        out.print("<td align='center'><input type='checkbox' value=" + "[" + obj_calibradores[3] + "/" + obj_calibradores[14] + "/" + obj_calibradores[15] + "]" + " onclick='seleccionarS(this.value,this);'></td>");
                                    }
                                } else {
                                    out.print("<td align='center'><input type='checkbox' value=" + "[" + obj_calibradores[3] + "/" + obj_calibradores[14] + "/" + obj_calibradores[15] + "]" + " onclick='seleccionarS(this.value,this);'></td>");
                                }
                            }
                            out.print("<td align='center'>" + obj_calibradores[3] + "</td>");
                            out.print("<td align='center'>" + obj_calibradores[1] + "</td>");
                            if (obj_calibradores[14].toString().equals("N-A")) {
                                out.print("<td align='center' colspan='2' style='background-color:#eee;'>" + obj_calibradores[14] + "</td>");
                            } else {
                                out.print("<td align='center'>Ult. " + TipoV[0] + "<br />" + obj_calibradores[4] + "</td>");
                                out.print("<td align='center'>Prox. " + TipoV[0] + "<br />" + obj_calibradores[6] + "</td>");
                            }
                            if (obj_calibradores[15].toString().equals("N-A")) {
                                out.print("<td align='center' colspan='2' style='background-color:#eee;'>" + obj_calibradores[15] + "</td>");
                            } else {
                                out.print("<td align='center'>Ult. " + TipoV[1] + "<br />" + obj_calibradores[7] + "</td>");
                                out.print("<td align='center'>Prox. " + TipoV[1] + "<br />" + obj_calibradores[9] + "</td>");
                            }
                            out.print("</tr>");
                            i = arg_seriales.length;
                        }
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager2 = new Pager3('seriales', 10);");
                    out.print("pager2.init();");
                    out.print("pager2.showPageNav('pager2','NavPosicion2');");
                    out.print("pager2.showPage(1);");
                    out.print("</script>");
                    out.print("<form method='post' action='Turno?opc=4'>");
                    out.print("<input type='hidden' value='" + id_turno + "' name='idT'>");
                    out.print("<input type='hidden' value='" + id_orden + "' name='idO'>");
                    out.print("<input type='hidden' value='" + filtro + "' name='txt_bus'>");
                    if (obj_turno[20] != null) {
                        String ser = "";
                        String[] serialesT = obj_turno[20].toString().split("/");
                        String[] serialesFU = obj_turno[29].toString().split("/");
                        String[] serialesFP = obj_turno[30].toString().split("/");
                        for (int i = 0; i < serialesT.length; i++) {
                            if (i == 0) {
                                ser = "[" + serialesT[i] + "/" + serialesFU[i] + "/" + serialesFP[i] + "]";
                            } else {
                                ser = ser + "[" + serialesT[i] + "/" + serialesFU[i] + "/" + serialesFP[i] + "]";
                            }
                        }
                        out.print("<input type='hidden' value='" + ser + "' name='txt_seriales' id='seriales-id'>");
                    } else {
                        out.print("<input type='hidden' value='' name='txt_seriales' id='seriales-id'>");
                    }
                    out.print("<input type='submit' value='Guardar'>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                    //</editor-fold>
                }
                if (id_rol == 1 || id_rol == 2 || id_rol == 3) {
                    if (pruebasF != 0) {
                        //<editor-fold defaultstate="collapsed" desc="PRUEBAS FUNCIONALES">
                        out.print("<div class='overlay' tabindex='-1' id='bloq' style='z-index: 100;opacity: 1.06; display: block;'>");
                        out.print("<fieldset class='resalta' id='Pruebas_Funcionales' style='visibility: visible;width:55%;left:40%'>");
                        if (id_pruebaF == 0) {
                            //<editor-fold defaultstate="collapsed" desc="registrar">
                            List lst_lotesSinPF = jpa_orden.consultaLoteEnsambleConsecutivo(id_RPF);
                            out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' title='Cancelar' onclick='CerrarPFuncional1(" + id_orden + ")'></span></div>");
                            out.print("<h3>Pruebas funcionales</h3>");
                            if (lst_lotesSinPF != null) {
                                Object[] obj_loteCon = (Object[]) lst_lotesSinPF.get(0);
                                out.print("<form method='post' action='Turno?opc=12'>");
                                out.print("<input type='hidden' name='txt_reg_turno' id='txt_reg_turno' value='" + id_RPF + "'/> ");
                                out.print("<input type='hidden' value='" + id_orden + "' name='idO'>");
                                out.print("<input type='hidden' value='" + obj_loteCon[2] + "' name='txt_lotee'>");
                                out.print("<input type='hidden' value='" + filtro + "' name='txt_bus'>");
                                out.print("<table class='table' align='center' style='width:50%'>");
                                out.print("<tr>");
//                            out.print("<td><b>NOTA: </b><b class='naranja'>La </b></td>");
                                out.print("<th>LOTE ENSAMBLE</th>");
                                out.print("<th>CONSECUTIVO</th>");
                                out.print("</tr>");
                                for (int i = 0; i < lst_lotesSinPF.size(); i++) {
                                    Object[] obj_loteConc = (Object[]) lst_lotesSinPF.get(i);
                                    out.print("<tr>");
                                    out.print("<td valign='top'>" + obj_loteConc[2] + "</td>");
                                    out.print("<td valign='top'>" + obj_loteConc[3] + "</td>");
                                    out.print("</tr>");
                                }
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("<br><div align='center'><input type='submit' value='Registrar'></div>");
                                out.print("</form>");
                                out.print("<hr>");
                            } else {
                            }
                            //</editor-fold>
//                            //<editor-fold defaultstate="collapsed" desc="registrar antigua">
//                            out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' title='Cancelar' onclick='CerrarPFuncional1(" + id_orden + ")'></span></div>");
//                            out.print("<h3>Pruebas funcionales | <a onclick='mostrarEmergente(6)'><b>NOTA</b></a></h3>");
//                            out.print("<form method='post' action='Turno?opc=12'>");
//                            out.print("<input type='hidden' value='" + id_orden + "' name='idO'>");
//                            out.print("<input type='hidden' value='" + filtro + "' name='txt_bus'>");
//                            out.print("<table class='table2' style='width:100%'>");
//                            out.print("<tr>");
//                            out.print("<td align='center'>");
//                            out.print("<b>Fecha:</b><br /><input type='text' value='' name='txt_fecha' id='datepicker2' placeholder='Fecha' autocomplete='off' style='margin-bottom:0px'>");
//                            out.print("<script type='text/javascript'>");
//                            out.print("var validation = new LiveValidation('datepicker2');");
//                            out.print("validation.add( Validate.Presence );");
//                            out.print("</script>");
//                            out.print("</td>");
//                            out.print("<td align='center'>");
//                            out.print("<b>Hora:</b><br /><input type='time' value='' name='txt_hora' id='horaPrF-id' style='margin-bottom:0px' required>");
//                            out.print("</td>");
//                            out.print("<td align='center'>");
//                            out.print("<b>Seleccione lote</b><br/>");
//                            out.print("<select name='slt_loteCon' id='lotePrF-id'>");
//                            out.print("<option value='' style='display:none;'>SELECCIONE LOTE</option>");
//                            if (lst_lotes != null) {
//                                for (int j = 0; j < lst_lotes.size(); j++) {
//                                    Object[] obj_lotes = (Object[]) lst_lotes.get(j);
//                                    out.print("<option value='" + obj_lotes[1] + "'>" + obj_lotes[1] + "</option>");
//                                }
//                            }
//                            out.print("</select>");
//                            out.print("<script type='text/javascript'>");
//                            out.print("var validation = new LiveValidation('lotePrF-id');");
//                            out.print("validation.add( Validate.Presence );");
//                            out.print("</script>");
//                            out.print("</td>");
//                            out.print("</tr>");
//                            out.print("<tr>");
//                            out.print("<td colspan='3' align='center'><input type='submit' value='Guardar'></td>");
//                            out.print("</tr>");
//                            out.print("</table>");
//                            out.print("</form>");
//                            //</editor-fold>
                        } else if (aprobarPF == 0) {
                            //<editor-fold defaultstate="collapsed" desc="resultado">
                            List lst_prueba = jpa_pruebaF.consultaPruebaId(id_pruebaF);
                            Object[] obj_prueba = (Object[]) lst_prueba.get(0);
                            out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' title='Cancelar' onclick='CerrarPFuncional(" + id_orden + ")'></span></div>");
                            //out.print("<div style='float:right;'><a href='Turno?opc=1&idO=" + id_orden + "&idT=0&Sr=0&registro=0&txt_bus=&PrF=1'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22' height='22' title='Cancelar'></a></div>");
                            out.print("<h3>Resultado Prueba Funcional <b>" + obj_prueba[2] + "</b></h3>");
                            out.print("<form method='post' action='Turno?opc=13'>");
                            out.print("<input type='hidden' value='" + id_orden + "' name='idO'>");
                            out.print("<input type='hidden' value='" + filtro + "' name='txt_bus'>");
                            out.print("<input type='hidden' value='" + id_pruebaF + "' name='idPF'>");
                            out.print("<input type='hidden' value='" + obj_prueba[2] + "' name='txt_lote'>");
                            out.print("<table class='table2' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<td align='center'>");
                            out.print("<b>Fecha:</b><br /><input type='text' value='' name='txt_fecha' id='end' placeholder='Fecha' autocomplete='off' style='margin-bottom:0px'>");
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('end');");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script>");
                            out.print("</td>");
                            out.print("<td align='center'>");
                            out.print("<b>Hora:</b><br /><input type='time' value='' name='txt_hora' id='hora-id' style='margin-bottom:0px' required>");
                            out.print("</td>");
                            out.print("<td align='center'>");
                            out.print("<b>Resultado</b><br/>");
                            out.print("<select name='slt_resultado' id='resultadoPrF-id'>");
                            out.print("<option value='' style='display:none;'>SELECCIONE RESULTADO</option>");
                            out.print("<option value='1'>CUMPLE</option>");
                            out.print("<option value='2'>NO CUMPLE</option>");
                            out.print("</select>");
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('resultadoPrF-id');");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script>");
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='3' align='center'><input type='submit' value='Guardar'></td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("</form>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="aprobar">
                            List lst_prueba = jpa_pruebaF.consultaPruebaId(id_pruebaF);
                            Object[] obj_prueba = (Object[]) lst_prueba.get(0);
                            out.print("<div style='float:right;'><a href='Turno?opc=1&idO=" + id_orden + "&idT=0&Sr=0&registro=0&txt_bus=&PrF=1'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22' height='22' title='Cancelar'></a></div>");
                            out.print("<h3>Aprobar Prueba Funcional <b>" + obj_prueba[2] + "</b></h3>");
                            out.print("<form method='post' action='Turno?opc=14'>");
                            out.print("<input type='hidden' value='" + id_orden + "' name='idO'>");
                            out.print("<input type='hidden' value='" + filtro + "' name='txt_bus'>");
                            out.print("<input type='hidden' value='" + id_pruebaF + "' name='idPF'>");
                            out.print("<input type='hidden' value='" + obj_prueba[2] + "' name='txt_lote'>");
                            out.print("<input type='hidden' value='1' name='slt_resultado'>");
                            out.print("<table class='table2' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<td align='center'>");
                            out.print("<b>Fecha:</b><br /><input type='text' value='' name='txt_fecha' id='datepicker2' placeholder='Fecha' autocomplete='off' style='margin-bottom:0px'>");
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('datepicker2');");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script>");
                            out.print("</td>");
                            out.print("<td align='center'>");
                            out.print("<b>Hora:</b><br /><input type='time' value='' name='txt_hora' id='hora-id' style='margin-bottom:0px' required>");
                            out.print("</td>");
                            out.print("<td align='center' rowspan='2'>");
                            out.print("<b>Resultado</b><br/>");
                            out.print("<textarea name='txt_justificacion' id='justificacion-id' placeholder='Justificacion' style='width:300px;height: 60;'></textarea>");
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('justificacion-id');");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script>");
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='2' align='center'><input type='submit' value='Guardar'></td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("</form>");
                            //</editor-fold>
                        }
                        //<editor-fold defaultstate="collapsed" desc="NOTA">
                        out.print("<div class='overlay' tabindex='-1' id='Convecion6' style='z-index: 100;opacity: 1.06; display: none;'>");
                        out.print("<fieldset class='resalta' id='registro_turno' style='visibility: visible;width:30%;height:125;left:55%'>");
                        out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' title='Cancelar' onclick='Nota(" + id_orden + ");'></span></div>");
                        out.print("<h3>NOTA:</h3>");
                        out.print("Para realizar el registro de la prueba de estaqueidad hay que tener encuenta lo siguiente:");
                        out.print("</br> <b>1°</b>: Revisar la fecha del registro del turno.");
                        out.print("</br> <b>2°</b>: Verificar en el registro(tros) que la parte <b>Prueba Estaqueidad</b> aparezca con puntos, esto define que tiene una prueba registra y pendiente por resultado.");
                        out.print("</fieldset>");
                        out.print("</div>");
                        //</editor-fold>
                        if (lst_pruebasF != null) {
                            out.print("<div id='NavPosicion4'></div>");
                            out.print("<table class='table' style='width:100%' id='resultadosPBF'>");
                            out.print("<tr>");
                            out.print("<th>Lote | CC</th>");
                            out.print("<th>Fecha Inicio</th>");
                            out.print("<th>Usuario Inicio</th>");
                            out.print("<th>Resultado</th>");
                            out.print("<th>Fecha resultado</th>");
                            out.print("<th>Usuario resultado</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_pruebasF.size(); i++) {
                                Object[] obj_pruebaF = (Object[]) lst_pruebasF.get(i);
                                out.print("<tr>");
//                                out.print("<td>" + obj_pruebaF[2] + "|<b style='color:black;'> " + obj_pruebaF[14] + "</b></td>");
                                out.print("<td>" + obj_pruebaF[2] + "| " + obj_pruebaF[14] + "</td>");
                                out.print("<td>" + obj_pruebaF[3] + "</td>");
                                out.print("<td>" + obj_pruebaF[4] + "</td>");
                                if ((Integer) obj_pruebaF[5] == 0) {
                                    if (Integer.parseInt(obj_pruebaF[13].toString()) == 1) {
                                        out.print("<td colspan='3' align='center'><a href='Turno?opc=1&idO=" + id_orden + "&idT=0&Sr=0&registro=0&txt_bus=&PrF=1&idPF=" + obj_pruebaF[0] + "' style='text-decoration:none'><b class='naranja'>Ingresar resultado</b></a></td>");
                                    } else {
                                        out.print("<td colspan='3' align='center'><b class='naranja'>No se registrado el resultado</b></td>");
                                    }
                                } else {
                                    out.print("<td align='center'>" + (((Integer) obj_pruebaF[5] == 1) ? "<b class='" + ((obj_pruebaF[8] != null) ? "naranja" : "verde") + "'>Cumple</b>" : ((id_rol == 1 || id_rol == 2) ? "<b class='rojo'>No Cumple</b>" : "<b class='rojo'>No Cumple</b>")) + "</td>");
                                    out.print("<td>" + obj_pruebaF[6] + "</td>");
                                    out.print("<td>" + obj_pruebaF[7] + "</td>");
                                }
                                out.print("</tr>");
                            }
                            out.print("</table>");
                            out.print("<script type='text/javascript'>");
                            out.print("var pager2 = new Pager2('resultadosPBF', 10);");
                            out.print("pager2.init();");
                            out.print("pager2.showPageNav('pager2','NavPosicion4');");
                            out.print("pager2.showPage(1);");
                            out.print("</script>");
                        }
                        out.print("</fieldset>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                }
                //<editor-fold defaultstate="collapsed" desc="CONSULTA TURNO">
                out.print("<br><div style='float: right;'>");
                out.print("<form method='post' action='Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=" + 0 + "&registro=" + 0 + "' style='margin-bottom: 0px;'>");
                out.print("<input type='text' name='txt_bus' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("</form>");
                if (id_rol == 1 || id_rol == 2 || id_rol == 3) {
                    out.print("<form method='post' action='Turno?opc=1&idO=" + id_orden + "&idT=0&Sr=0&registro=0&txt_bus=0&PrF=1' id='FormPFCo'>");
                    out.print("<span class='fas fa-weight fa-size_small' title='Prueba funcional' onclick='PruebaFuncionalCo();'  style='float:right;'>");
                    out.print("<input type='hidden' name='txt_reg_turno' id='txt_reg_turno' /> ");
                    out.print("</form>");
                }
                out.print("</div>");
                if (!filtro.equals("")) {
                    out.print("<a href='Turno?opc=1&idO=" + id_orden + "&idT=" + 0 + "&Sr=" + 0 + "&registro=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='22' height='22' title='Volver' /></a>");
                } else {
                    out.print("<a href='Orden?opc=1&idO=" + 0 + "&txt_ficha=&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='22' height='22' title='Volver' /></a>");
                }
                out.print("<br><h3>Turnos |  <b><a onclick='mostrarEmergente(4)'>Conveciones</a></b></h3>");
                if (id_rol == 6 || id_rol == 5) {
                } else {
                    out.print("<div style='float: left;'><span class='fas fa-plus fa-size_small' style='float:left;' title='Registrar turno' onclick='mostrarEmergente(2)'></span></div><br><br>");
                }
                //<editor-fold defaultstate="collapsed" desc="CONVENCIONES">
                out.print("<div class='overlay' tabindex='-1' id='Convecion4' style='z-index: 100;opacity: 1.06; display: none;'>");
                out.print("<fieldset class='resalta' id='registro_turno' style='visibility: visible;width:55%;left:40%'>");
                out.print("<div style='float:right;'><span class='fas fa-times fa-size_small' title='Cancelar' onclick='CerrarPFuncional1(" + id_orden + ")'></span></div>");
                out.print("<h3>Convenciones</h3>");
                out.print("<table class='table' style='width:100%'>");
                out.print("<tr><th>Tipo</th>");
                out.print("<th>Descripción</th></tr>");
                out.print("<tr>");
                out.print("<td align='center'><b>Aprobado</b><div class='estadoaprobado'></div></td>");
                out.print("<td>Cuando un turno se encuentra en estado de <b style='color:#5cb85c;'>aprobado</b> significa que el control dimensional y la pruebas realizadas estan aprobadas.</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center'><b>Cuarentena</b><div class='estadocuarentena'></div></td>");
                out.print("<td>Cuando el turno se encuentre en estado de <b style='color:#f58526;'>cuarentena</b> significa que presenta algun defecto de forma manual por usuario, por diferencia en el control dimesional, por defecto visual o por prueba de estaquiedad en seguimiento con resultado de no cumple.</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center'><b>Seguimiento</b><div class='estadoseguimiento'></div></td>");
                out.print("<td>Cuando el turno se encuentre en estado de <b style='color:#00B0FF;'>seguimiento</b> significa que se realizo una prueba funcional y el resultado es <b class='rojo'>NO CUMPLE</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center'><b>Rechazado</b><div class='estadorechazado'></div></td>");
                out.print("<td>Cuando el turno se encuentre en estado de <b class='rojo'>rechazado</b> significa que no cumplio con los requisitos y las pruebas realziadas.</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</fieldset>");
                out.print("</div>");
                //</editor-fold>
                if (lst_turnos == null) {
                    out.print("<h3>No se encontraron resultados</h3>");
                } else {
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados2' style='width:100%;'>");
                    //<editor-fold defaultstate="collapsed" desc="TABLA TURNO">
                    for (int i = 0; i < lst_turnos.size(); i++) {
                        Object[] obj_turno = (Object[]) lst_turnos.get(i);
                        lst_CantDimensionalT = jpa_DimensionalT.ConsultaCantidadTotalDimensional((Integer) obj_turno[0]);
                        String[] fechahora = obj_turno[1].toString().split(" ");
                        String hora = fechahora[1].toString();
                        out.print("<td colspan='11'></td>");
                        out.print("<tr>");
                        if (obj_turno[28].equals("aprobado")) {
                            //<editor-fold defaultstate="collapsed" desc="APROBADO">
                            out.print("<td><center><div class='girarD'><b>" + obj_turno[25] + "</b>");
                            if ((Integer) obj_turno[19] == 1) {
                                out.print("<br><b style='color:green;'>resumido</b></center></td>");
                            } else {
                                out.print("<br><b class='naranja'>Sin resumir</b></center></td>");
                            }
                            if (id_rol == 6) {
                                out.print("<td><center><b><br>APROBADO</b><br/><br/>"
                                        + "<div class='estadoaprobado'></div></center>");
                            } else {
                                out.print("<td><center><b><br>APROBADO</b><br/><br/>");
                                if (obj_turno[33] == null) {
                                    out.print("<input type='checkbox' name='Masivo' onclick='MasivoTurno(this.value)' id='MasivoTurno-" + obj_turno[0] + "' value='" + obj_turno[0] + "' > | ");
                                }
                                out.print("<span class='fas fa-eye fa-size_small' title='Ver Control Dimensional' onclick='Ver(" + id_orden + "," + obj_turno[0] + ")'></span><br>"
                                        + "<div class='estadoaprobado'></div></center>");
                            }
                            //</editor-fold>
                        } else if (obj_turno[28].equals("rechazado")) {
                            //<editor-fold defaultstate="collapsed" desc="RECHAZADO">
                            out.print("<td><center><div class='girarD'><b>" + obj_turno[25] + "</b>");
                            if ((Integer) obj_turno[19] == 1) {
                                out.print("<br><b style='color:green;'>resumido</b></center></td>");
                            } else {
                                out.print("<br><b class='naranja'>Sin resumir</b></center></td>");
                            }
                            out.print("<td><center><b><br>RECHAZADO</b><br/><br/>"
                                    + "<span class='fas fa-eye fa-size_small' title='Ver Control Dimensional' onclick='Ver(" + id_orden + "," + obj_turno[0] + ")'></span><br>"
                                    + "<div class='estadorechazado'></div></center>");
                            //</editor-fold>
                        } else if (obj_turno[28].equals("seguimiento")) {
                            //<editor-fold defaultstate="collapsed" desc="SEGUIMIENTO">
                            out.print("<td><center><div class='girarD'><b>" + obj_turno[25] + "</b>");
                            if ((Integer) obj_turno[19] == 1) {
                                out.print("<br><b style='color:green;'>resumido</b></center></td>");
                            } else {
                                out.print("<br><b class='naranja'>Sin resumir</b></center></td>");
                            }
                            out.print("<td><center><b><br>SEGUIMIENTO</b><br/><br/>"
                                    + "<span class='fas fa-eye fa-size_small' title='Ver Control Dimensional' onclick='Ver(" + id_orden + "," + obj_turno[0] + ")'></span><br>"
                                    + "<div class='estadoseguimiento'></div></center>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="CUARENTENA">
                            out.print("<td><center><div class='girarD'><b>" + obj_turno[25] + "</b>");
                            if ((Integer) obj_turno[19] == 1) {
                                out.print("<br><b style='color:green;'>resumido</b></center></td>");
                            } else {
                                out.print("<br><b class='naranja'>Sin resumir</b></center></td>");
                            }
                            out.print("<td  colspan='1'><center><b class='naranja'>CUARENTENA</b><br/><br/>");
                            if (obj_turno[33] == null) {
                                out.print("<input type='checkbox' name='Masivo' onclick='MasivoTurno(this.value)' id='MasivoTurno-" + obj_turno[0] + "' value='" + obj_turno[0] + "' > | ");
                            }
                            out.print("<span class='fas fa-eye fa-size_small' title='Ver Control Dimensional' onclick='Ver(" + id_orden + "," + obj_turno[0] + ")'></span>");
                            out.print("<div class='estadocuarentena'></div></center>");
                            //</editor-fold>
                        }
                        out.print("<td valign='top' style='width:12%;'><b>LOTE ENSAMBLE: </b><br><b style='color:#000;'>" + obj_turno[10] + "</b>"
                                + "<br/><br/><b>CONSECUTIVO: </b><br/>" + obj_turno[27] + "</br>");
                        if (obj_turno[40] != null) {
                            if ((Integer) obj_turno[41] == 0) {
                                out.print("<hr><b class='verde'>LIBERADO | </b>"
                                        + "<span class='fas fa-file fa-size_small'  onclick='registroDespeje(" + obj_turno[40] + ")'></span>");
                            } else {
                                out.print("<hr><b class='naranja'>SIN LIBERAR | </b>"
                                        + "<span class='far fa-file fa-size_small'  onclick='registroDespeje(" + obj_turno[40] + ")'></span>");
                            }
                        }
                        if (obj_turno[28].equals("aprobado") || obj_turno[28].equals("rechazado") || obj_turno[28].equals("seguimiento")) {
                        } else {
                            out.print("<hr><b>Num Cuarentena:</b><br><b class='naranja'>" + obj_turno[28] + "</b>");
                        }
                        out.print("</td>");
                        out.print("<td valign='top' style='width:12%;'><b>TURNO:</b>" + obj_turno[3] + ""
                                + "<br><b>FECHA: </b>" + obj_turno[2] + "<br/><b>HORA: </b>" + hora + "");
                        out.print("</td>");
                        out.print("<td valign='top' style='width:12%;'><b>LOTE BASE: </b><br>");
                        out.print("<br><b>C: </b>" + obj_turno[6] + "<br /><b>P: </b>" + obj_turno[7] + "</td>");
                        out.print("<td valign='top'  style='width:12%;'><b>LOTE PISTON: </b><br>");
                        out.print("<br><b>C: </b>" + obj_turno[8] + "<br /><b>P: </b>" + obj_turno[9] + "</td>");
                        out.print("<td valign='top'  style='width:9%;'>");
                        out.print("<b>MÁQUINA: </b><br>" + obj_turno[22] + "");
                        out.print("<br><br><b>MOLDE: </b>");
                        if (obj_turno[26] != null) {
                            out.print("" + obj_turno[26] + "");
                        } else {
                            out.print("N/A");
                        }
                        out.print("</td>");
                        out.print("<td valign='top'  style='width:9%;'>");
                        if (obj_turno[20] == null) {
                            out.print("<b>SERIAL: </b><br/><br>Sin serial");
                        } else {
                            String[] Separador = obj_turno[20].toString().split("/");
                            out.print("<b>SERIAL: </b><br>");
                            for (int j = 0; j < Separador.length; j++) {
                                out.print("" + Separador[j] + "<br>");
                            }
                        }
                        out.print("</td>");
                        //<editor-fold defaultstate="collapsed" desc="PRUEBA FUNCIONAL DESCRIPCION">
                        if (obj_turno[32] != null) {
                            if ((Integer) obj_turno[33] != 0) {
                                out.print("<div class='tooltip_templates'>");
                                out.print("<span id='tooltip_content" + i + "'>");
                                out.print("" + (((Integer) obj_turno[33] == 1) ? ((obj_turno[36] != null) ? "<b>Aprobado</b>" : "<b class='verde'>Cumple</b>") : "<b class='rojo'>No Cumple</b>") + " | <b>Fecha: </b>" + obj_turno[34] + " | <b>Responsable: </b>" + obj_turno[35] + "");
                                if (obj_turno[36] != null) {
                                    out.print("<hr/><b>Justificacion: </b>" + obj_turno[36] + "<hr/>");
                                    out.print("" + (((Integer) obj_turno[37] == 1) ? "<b class='verde'>Cumple</b>" : "<b class='rojo'>No Cumple</b>") + " | <b>Fecha: </b>" + obj_turno[38] + " | <b>Responsable: </b>" + obj_turno[39] + "");
                                }
                                out.print("</span>");
                                out.print("</div>");
                            }
                        }
                        //</editor-fold>
                        out.print("</td>");
                        out.print("<td valign='top' style='width: 12%;'><b>DIMENSIONAL: </b>");
                        lst_CantDimensional = jpa_dimensional.ConsultaCantidad((Integer) obj_turno[0]);
                        Object[] obj_cantidadD = (Object[]) lst_CantDimensional.get(0);
                        out.print("" + obj_cantidadD[0] + "");
                        out.print("<br><b>DEFECTO(S): </b>");
                        lst_CantDefecto = jpa_defecto.consultarDefectoTurno((Integer) obj_turno[0]);
                        int var = 0;
                        for (int k = 0; k < lst_CantDefecto.size(); k++) {
                            Object[] obj = (Object[]) lst_CantDefecto.get(k);
                            if (obj[5] != null) {
                                var = Integer.parseInt(obj[5].toString());
                                suma = suma + Integer.parseInt(obj[5].toString());
                            }
                        }
                        if (var != 0) {
                            out.print("" + suma + "");
                            suma = 0;
                        } else {
                            out.print("0");
                        }
                        out.print("<br><b>TOMAS: </b>");
                        for (int j = 0; j < lst_CantDimensionalT.size(); j++) {
                            Object[] num = (Object[]) lst_CantDimensionalT.get(j);
                            count = count + Integer.parseInt(num[1].toString());
                        }
                        out.print("" + count + "</td>");
                        out.print("</td>");
                        out.print("<td valign='top' style='width:12%;'>");
                        out.print("<b>REPONSABLE: </b><br>" + obj_turno[24] + "");
                        if (Integer.parseInt(obj_turno[0].toString()) <= 58284) {
                            out.print("<br><br><b>Pru. Estaqueidad: </b><br><b class='verde'>Cumple</b></b></td>");
                        } else if (obj_turno[32] != null) {
                            out.print("<br><br><b class='tooltip' data-tooltip-content='#tooltip_content" + i + "'>Pru. Estaqueidad: </b><br>" + (((Integer) obj_turno[33] == 1) ? "<b class='" + ((obj_turno[36] != null) ? "naranja" : "verde") + "'>Cumple</b>" : (((Integer) obj_turno[33] == 0) ? "</br><b class='naranja'>No se ha registrado</b>" : "<b class='rojo'>No cumple</b>") + "</td>"));
                        } else {
                            out.print("<br><br><b>Pru. Estaqueidad: </b><br><b class='naranja'>No se ha registrado</b></b></td>");
                        }
                        //<editor-fold defaultstate="collapsed" desc="OPCIONES">
                        if (obj_turno[31].equals("abierto")) {
                            if (obj_turno[18].equals("abierto")) {
                                out.print("<td align='center' >");
                                if (obj_turno[28].equals("rechazado")) {
                                    if (id_rol == 5 || id_rol == 6) {
                                    } else {
                                        out.print("<span class='far fa-copy fa-size_small' onclick='Tomas(" + id_orden + "," + obj_turno[0] + ")'></span>");
                                    }
                                } else if (obj_turno[28].equals("aprobado") || obj_turno[28].equals(obj_turno[28])) {
                                    if (id_rol == 1 || id_rol == 2 || id_rol == 3) {
                                        out.print("<span class='fas fa-ruler fa-size_small' title='Consultar Seriales' onclick='Seriales(" + id_orden + "," + obj_turno[0] + ")' ></span>");
                                        out.print("<hr /><span class='fas fa-pencil-alt fa-size_small' title='Editar turno' onclick='EditarTurno(" + id_orden + "," + obj_turno[0] + ")'></span>");
                                        if (count == 0) {
                                            out.print("<hr /><span class='far fa-copy fa-size_small' title='Tomas' onclick='SinRegistro()'></span>");
                                            if (obj_turno[28].equals("seguimiento")) {
                                                out.print("<hr /><span class='fas fa-search fa-size_small' title='Seguimiento' onclick='Seguimiento(" + id_orden + ")'></span>");
                                            } else {
                                                out.print("<hr /><span class='fas fa-search fa-size_small' title='Seguimiento' style='color:#b1b1b1'></span>");
                                            }
                                        } else {
                                            out.print("<hr /><span class='far fa-copy fa-size_small' title='Tomas' onclick='Tomas(" + id_orden + "," + obj_turno[0] + ")'></span>");
                                            if (obj_turno[28].equals("seguimiento")) {
                                                out.print("<hr /><span class='fas fa-search fa-size_small' title='Seguimiento' onclick='Seguimiento(" + id_orden + ")'></span>");
                                            } else {
                                                out.print("<hr /><span class='fas fa-search fa-size_small' title='Seguimiento' style='color:#b1b1b1'></span>");
                                            }
                                        }
                                    }
                                }
                            } else if (id_rol == 6 || id_rol == 5) {
                                out.print("<td align='center' >");
                            } else {
                                out.print("<td align='center' ><span  class='far fa-copy fa-size_small' title='Tomas' onclick='Tomas(" + id_orden + "," + obj_turno[0] + ")'></span>");
                            }
                        } else {
                            out.print("<td align='center'><span  class='far fa-copy fa-size_small' title='Tomas' onclick='Tomas(" + id_orden + "," + obj_turno[0] + ")'></span>");
                        }
                        count = 0;
                        if ((Integer) obj_turno[17] == 1) {
                            if (obj_turno[18].equals("abierto")) {
                                if (id_rol == 1 || id_rol == 2 || id_rol == 3 || id_rol == 4) {
                                    out.print("<hr /><span href='#' class='fas fa-lock-open fa-size_small' title='Turno abierto' onclick='Turno_Abierto(" + id_orden + "," + obj_turno[0] + ")'></span>");
                                    out.print("</td>");
                                } else {
                                    out.print("<hr /><span href='#' class='fas fa-lock-open fa-size_small' title='Turno abierto'></td>");
                                }
                            } else if (id_rol == 2 || id_rol == 1) {
                                out.print("<hr /><span class='fas fa-lock fa-size_small' title='Turno cerrado' onclick='Turno_Cerrado(" + id_orden + "," + obj_turno[0] + ")'></span>");
                                if (id_rol == 2 || id_rol == 1 || id_rol == 3) {
                                    out.print("<hr /><span class='fas fa-search fa-size_small' title='Seguimiento' onclick='Seguimiento(" + id_orden + ")'></span>");
                                    out.print("</td>");
                                }
                            } else if (true) {
                                out.print("<hr /><span class='fas fa-lock fa-size_small'style='color:#b1b1b1;' title='Turno cerrado '></span></td>");
                            } else {
                                out.print("<hr /><span class='fas fa-lock fa-size_small' title='Turno cerrado'></span></td>");
                            }
                        } else {
                            lst_CantDimensionalT = jpa_DimensionalT.ConsultaCantidadTotalDimensional((Integer) obj_turno[0]);
                            for (int j = 0; j < lst_CantDimensionalT.size(); j++) {
                                Object[] num = (Object[]) lst_CantDimensionalT.get(j);
                                count = count + Integer.parseInt(num[1].toString());
                            }
                            if (count == 72 || count == 96) {
                                out.print("<hr /><span class='fas fa-check-circle fa-size_small' title='Aprobar turno' onclick='Aprobar(" + id_orden + "," + obj_turno[0] + ")' ></span>");
                            } else if (id_rol == 6 || id_rol == 5) {
                                out.print("<span class='fas fa-lock-open fa-size_small' title='Cerrar turno' style='color:#b1b1b1'></span>");
                            } else {
                                out.print("<hr /><span class='fas fa-lock-open fa-size_small' title='Cerrar turno' style='color:#b1b1b1'></span>");
                            }
                            count = 0;
                        }
                        //</editor-fold>
                        out.print("</tr>");
                    }
                    //</editor-fold>
                    out.print("<input type='hidden' name='Txt_ids' id='Txt_ids'>");
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados2',20);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                }
                //</editor-fold>
                out.print("</div><div class='cleaner'></div>");
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_turnos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_turnos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
