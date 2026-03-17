package Tags;

import Controladores.CabeceraEtdJpaController;
import Controladores.ElectrodoJpaController;
import Controladores.MovimientosJpaController;
import Controladores.SolicitudJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_verificacion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String nombre = sesion.getAttribute("Nombre").toString();
        CabeceraEtdJpaController jpa_verificacion = new CabeceraEtdJpaController();
        ElectrodoJpaController jpa_electrodo = new ElectrodoJpaController();
        SolicitudJpaController jpa_solicitud = new SolicitudJpaController();
        MovimientosJpaController jpa_movimientos = new MovimientosJpaController();
        List lst_verificaciones = null;
        List lst_verificacion = null;
        List lst_itemsVfc = null;
        List lst_calificacion = null;
        List lst_solicitud = null;
        List lst_movimientos = null;
        List lst_electrodo = null;
        List lst_verAnio = null;
        int id_pieza = 0, id_verificacion = 0, id_plano = 0, anio = 0, val = 0;
        String numSolicitud = "";
        try {
            try {
                id_pieza = Integer.parseInt(pageContext.getRequest().getAttribute("id_pieza").toString());
            } catch (NumberFormatException e) {
                id_pieza = 0;
            }
            if (id_pieza == 0) {
                try {
                    id_verificacion = Integer.parseInt(pageContext.getRequest().getAttribute("id_verificacion").toString());
                } catch (NumberFormatException e) {
                    id_verificacion = 0;
                }
                try {
                    anio = Integer.parseInt(pageContext.getRequest().getAttribute("anio").toString());
                } catch (NumberFormatException e) {
                    anio = 0;
                }
                try {
                    val = Integer.parseInt(pageContext.getRequest().getAttribute("val").toString());
                } catch (NumberFormatException e) {
                    val = 0;
                }
                if (id_verificacion == 0) {
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA LISTA VERIFICACION">
                    out.print("<section class='section'>");
                    out.print("<div class='section-header'>");
                    out.print("<h1>Modulo Verificación</h1>");
                    out.print("</div>");
                    out.print("<div class='section-body'>");
                    out.print("<div class='row'>");
                    out.print("<div class='col-12'>");
                    out.print("<div class='card'>");
                    out.print("<div class='card-header'>");
                    out.print("<div class='DivControl' >");
                    out.print("<div><h4>Listado de Verificación</h4></div>");
                    out.print("<div>");
                    out.print("<form action='Verificacion?opc=1' onsubmit='checkSubmit();' method='post' name='formAnio' id='formAnio'>");
                    lst_verAnio = jpa_verificacion.consultarVerificacionesAnio();
                    out.print("<select class='form-control' name='anio' required id='anio-id' style='border-radius: 4px;' onchange='Javascript:document.formAnio.submit();'>");
                    if (lst_verAnio != null) {
                        if (anio > 0) {
                            out.print("<option value='" + anio + "'>" + anio + "</option>");
                        }
                        for (int i = 0; i < lst_verAnio.size(); i++) {
                            Object[] obj_anio = (Object[]) lst_verAnio.get(i);
                            if (anio != Integer.parseInt(obj_anio[1].toString())) {
                                out.print("<option value='" + obj_anio[1] + "'>" + obj_anio[1] + "</option>");
                            }
                        }
                        out.print("<option value='0'>TODOS</option>");
                        out.print("</select>");
                    } else {
                        out.print("<option>" + anio + "</option>");
                        out.print("</select>");
                    }
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='card-body'>");
                    lst_verificaciones = jpa_verificacion.consultarVerificaciones(val, anio);
                    out.print("<div class='table-responsive'>");
                    out.print("<table class='table table-bordered table-hover CustomTable' id='table-1'>");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th style='text-align: center; height:25px;'>Fecha</th>");
                    out.print("<th style='text-align: center; height:25px;'>Solicitud</th>");
                    out.print("<th style='text-align: center; height:25px;'>Responsable verificación</th>");
                    out.print("<th style='text-align: center; height:25px;'>Electrodo</th>");
                    out.print("<th style='text-align: center; height:25px;'>Línea | Máquina</th>");
                    out.print("<th style='text-align: center; height:25px;'>Ver</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    if (lst_verificaciones != null) {
                        out.print("<tbody>");
                        for (int i = 0; i < lst_verificaciones.size(); i++) {
                            Object[] obj_verificacion = (Object[]) lst_verificaciones.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_verificacion[2] + "</td>");
                            out.print("<td>" + obj_verificacion[3] + "</td>");
                            out.print("<td>" + obj_verificacion[4] + "</td>");
                            out.print("<td>" + obj_verificacion[6] + "</td>");
                            out.print("<td>" + obj_verificacion[8] + "</td>");
                            lst_solicitud = jpa_solicitud.consultaSolicitudNumero(obj_verificacion[3].toString());
                            if (lst_solicitud != null || lst_solicitud.size() > 0) {
                                Object[] obj_solicitud = (Object[]) lst_solicitud.get(0);
                                if (Integer.parseInt(obj_solicitud[2].toString()) == 100) {
                                    out.print("<td style='text-align:center;'><a href='Verificacion?opc=1&idV=" + obj_verificacion[0] + "&anio=" + anio + "' style='color:white;' class='btn btn-red btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Ver detalle'><i class='fas fa-eye'></i></a></td>");
                                } else {
                                    out.print("<td style='text-align:center;'><a href='Verificacion?opc=1&idV=" + obj_verificacion[0] + "&anio=" + anio + "' style='color:white;' class='btn btn-info btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Pendiente verificación'><i class='fas fa-folder-plus'></i></a></td>");
                                }
                            } else {
                                out.print("<td><center><img src='Interfaz/Contenido/Iconos/Warning.png' alt='Logo' width='20' height='20' /></center></td>");
                            }
                            out.print("</tr>");
                        }
                        out.print("</tbody>");
                    } else {
                        out.print("<tbody><tr><td style='text-align:center;' colspan='6'>No existe verificaciones registradas en el año</td></tr></tbody>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</section>");
                    //</editor-fold>    
                } else {
                    //<editor-fold defaultstate="collapsed" desc="R-MTF-013 VERIFICACIONES">
                    lst_calificacion = jpa_verificacion.consultarCalificacionVerificacionId(id_verificacion);
                    lst_verificacion = jpa_verificacion.consultarVerificacionId(id_verificacion);
                    out.print("<section class='section'>");
                    out.print("<div class='section-header'>");
                    out.print("<div style='display:flex;align-items:center'>"
                            + "<div class='mr-2'><a class=\"btn btn-white btn-icon btn-sm\" data-toggle=\"tooltip\" href='Verificacion?opc=1&anio=" + anio + "' data-placement=\"top\" title=\"\" data-original-title=\"Volver\"><i class=\"fas fa-arrow-left\"></i></a></div>"
                            + "<div><h1>Modulo R-MTF-013</h1></div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='section-body'>");
                    out.print("<div class='row'>");
                    out.print("<div class='col-12'>");
                    out.print("<div class='card'>");

                    out.print("<div class='card-body'>");
                    if (lst_calificacion != null || lst_verificacion != null) {
                        Object[] obj_verificacion = (Object[]) lst_verificacion.get(0);
                        out.print("<table style='width:100%'>");
                        out.print("<thead>");
                        out.print("<tr>");
                        out.print("<tr><td colspan='12' style='background-color:#979595;height:22px !important;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:28%' align='center' colspan='2'><img src='Interfaz/Contenido/Imagen/Logo.png' style='width: 211px; height: 72px' alt=''></td>");
                        out.print("<td colspan='2' style='width:40%;'><h6 style='text-align: center;'>REGISTRO DE VERIFICACIÓN ELECTRODO</h6></td>");
                        out.print("<td style='width:13%' align='center'><b>CODIGO</b><br /><b style='color:black'>R-MTF-013</b></td>");
                        out.print("<td style='width:13%' colspan='2' align='center'><b>VERSION</b><br /><b style='color:black'>4</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><b class='clssB PdgTd'>Fecha: </b></td>");
                        out.print("<td>" + obj_verificacion[2] + "</td>");
                        out.print("<td><b class='clssB PdgTd'>Numero Electrodo: </b></td>");
                        out.print("<td>" + obj_verificacion[7] + "</td>");
                        out.print("<td colspan ='2' style ='text-align:center;'><b class='clssB'>Referencia: </b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><b class='clssB PdgTd'>Solicitud: </b></td>");
                        out.print("<td>" + obj_verificacion[3] + "</td>");
                        out.print("<td><b class='clssB PdgTd'>Verificado por: </b></td>");
                        out.print("<td>" + obj_verificacion[4] + "</td>");
                        out.print("<td colspan='2' style ='text-align:center;'>" + obj_verificacion[10] + "</td>");
                        out.print("</tr>");
                        out.print("</thead>");
                        out.print("</table>");
                        out.print("<table class='table-hover' style='width:100%'>");
                        out.print("<thead>");
                        out.print("<tr>");
                        out.print("<th style='width:40%' class='th1Verificacion'>Decripción</th>");
                        out.print("<th style='width:30%' class='th1Verificacion'>Medida Standard</th>");
                        out.print("<th class='th1Verificacion'>Cumple</th>");
                        out.print("<th class='th1Verificacion'>Aplica</th>");
                        out.print("<th class='th1Verificacion'>Observaciones</th>");
                        out.print("</tr>");
                        out.print("</thead>");
                        out.print("<tbody>");
                        if (lst_calificacion != null) {
                            for (int i = 0; i < lst_calificacion.size(); i++) {
                                Object[] obj_calificacion = (Object[]) lst_calificacion.get(i);
                                out.print("<tr>");
                                out.print("<td class='PdgTd'>" + obj_calificacion[2] + "</td>");
                                out.print("<td class='PdgTd'>" + obj_calificacion[3] + "</td>");
                                out.print("<td align=center >" + obj_calificacion[4] + "</td>");
                                out.print("<td align=center >" + obj_calificacion[5] + "</td>");
                                out.print("<td align=center>" + obj_calificacion[6] + "</td>");
                                out.print("</tr>");
                            }
                        }
                        out.print("<tr>");
                        lst_movimientos = jpa_movimientos.consultaMovimientoSolicitud(obj_verificacion[3].toString(), obj_verificacion[7].toString());
                        if (lst_movimientos != null) {
                            Object[] obj_movimiento = (Object[]) lst_movimientos.get(0);
                            out.print("<td class='PdgTd'><b class='clssB'> Nota: </b><b style='color:black'> Solo electrodos nuevos o maquinados</b></td>");
                            out.print("<td class='PdgTd'><b style='color:black'>" + obj_movimiento[10] + "</b></td>");
                            out.print("<td class='PdgTd' colspan='3'><b class='clssB'>Entregado por:</b><b style='color:black'> " + obj_movimiento[14] + "</b></td>");
                        } else {
                            out.print("<td colspan='5' style='text-align:center;'>No existe información de responsable</td>");
                        }
                        out.print("</tr>");
                        out.print("</tbody>");
                        out.print("<tr><td colspan='13'><span style='    font-size: 12px;\n"
                            + "    font-style: italic;\n"
                            + "    margin-left: 8px;'>La informacion personal en este documento sera tratada y protegida de acuerdo con nuestras politicas de proteccion de datos personales. </span></td></tr>");
                        out.print("</table>");
                        out.print("</div>");
                    }

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</section>");
                    //</editor-fold>
                }
            } else {
                //<editor-fold defaultstate="collapsed" desc="R-MTF-013 VERIFICACIONES">
                try {
                    id_plano = Integer.parseInt(pageContext.getRequest().getAttribute("id_plano").toString());
                } catch (NumberFormatException e) {
                    id_plano = 0;
                }
                try {
                    numSolicitud = pageContext.getRequest().getAttribute("numero_solicitud").toString();
                } catch (Exception e) {
                    numSolicitud = "";
                }
                lst_electrodo = jpa_electrodo.consultaElectrodoId(id_pieza);
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Modulo Verificación</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<table style='width:100%'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<tr><td colspan='12' style='background-color:#979595;height:22px !important;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='width:28%' align='center' colspan='2'><img src='Interfaz/Contenido/Imagen/Logo.png' style='width: 211px; height: 72px' alt=''></td>");
                out.print("<td colspan='2' style='width:40%;'><h6 style='text-align: center;'>REGISTRO DE VERIFICACIÓN ELECTRODO</h6></td>");
                out.print("<td style='width:13%' align='center'><b>CODIGO</b><br /><b style='color:black'>R-MTF-013</b></td>");
                out.print("<td style='width:13%' colspan='2' align='center'><b>VERSION</b><br /><b style='color:black'>4</b></td>");
                out.print("</tr>");
                if (lst_electrodo != null) {
                    Object[] obj_pieza = (Object[]) lst_electrodo.get(0);
                out.print("<form action='Verificacion?opc=3' method='post' onsubmit='document.forms['form1']['guardar'].disabled = true;' name='form1' id='form1'>");
                    out.print("<input type='hidden' name='txt_numS' value='" + numSolicitud + "'>");
                    out.print("<input type='hidden' name='pieza' value='" + obj_pieza[2] + "'>");
                    out.print("<input type='hidden' name='idPz' value='" + id_pieza + "'>");
                    out.print("<tr>");
                    out.print("<td><b class='clssB PdgTd'>Fecha: </b></td>");
                    out.print("<td><input type='date' style='height:28px;' class='form-control' name='txt_fecha' id='Fecha-id' value='' required placeholder='Tipo' onchange='javascript:this.value=this.value.toUpperCase();' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Fecha'></td>");
                    out.print("<td><b class='clssB PdgTd'>Numero Electrodo: </b></td>");
                    out.print("<td>" + obj_pieza[2] + "</td>");
                    out.print("<td colspan ='2' style ='text-align:center;'><b class='clssB'>Referencia: </b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><b class='clssB PdgTd'>Solicitud: </b></td>");
                    out.print("<td>" + numSolicitud + "</td>");
                    out.print("<td><b class='clssB PdgTd'>Verificado por: </b></td>");
                    out.print("<td>" + nombre + "</td>");
                    out.print("<td colspan='2' style ='text-align:center;'>" + obj_pieza[5] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th style='width:40%' colspan='2' class='th1Verificacion'>Decripción</th>");
                    out.print("<th style='width:30%' class='th1Verificacion'>Medida Standard</th>");
                    out.print("<th class='th1Verificacion'>Cumple</th>");
                    out.print("<th class='th1Verificacion'>Aplica</th>");
                    out.print("<th class='th1Verificacion'>Observaciones</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    lst_itemsVfc = jpa_verificacion.consultarItemsVerificacionIdPlano(id_plano);
                    if (lst_itemsVfc != null) {
                        out.print("<tbody>");
                        for (int i = 0; i < lst_itemsVfc.size(); i++) {
                            Object[] obj_items = (Object[]) lst_itemsVfc.get(i);
                            out.print("<tr>");
                            out.print("<td class='PdgTd' colspan='2'>" + obj_items[3] + "</td>");
                            out.print("<td class='PdgTd'>" + obj_items[4] + "</td>");
                            out.print("<td>"
                                    + "<select class='form-control' name='txtcumple_" + i + "'  id='select-id' required>");
                            if (obj_items[5].toString().toUpperCase().equals("SI")) {
                                out.print("<option value='SI'>SI</option>");
                                out.print("<option value='NO'>NO</option>");
                            } else {
                                out.print("<option value='NO'>NO</option>");
                                out.print("<option value='SIR'>SI</option>");

                            }
                            out.print("</select>");
                            out.print("<td>"
                                    + "<select class='form-control' name='txtaplica_" + i + "'  id='select-id' required>");
                            if (obj_items[6].toString().toUpperCase().equals("SI")) {
                                out.print("<option value='SI'>SI</option>");
                                out.print("<option value='NO'>NO</option>");
                            } else {
                                out.print("<option value='NO'>NO</option>");
                                out.print("<option value='SIR'>SI</option>");

                            }
                            out.print("</select>");
                            out.print("<td><textarea  style='height:44px!important' class='form-control' name='txtobservaciones_" + i + "' placeholder='Observaciones' onchange='javascript:this.value=this.value.toUpperCase();'></textarea></td>");
                            out.print("</tr>");
                        }
                        out.print("<tr><td colspan='5'><span style='    font-size: 12px;\n"
                            + "    font-style: italic;\n"
                            + "    margin-left: 8px;'>La informacion personal en este documento sera tratada y protegida de acuerdo con nuestras politicas de proteccion de datos personales. </span></td></tr>");
                        out.print("</tbody>");
                    }
                    out.print("</table>");
                    out.print("<div class='mt-2' style='width: 100%; text-align:center;'>");
                    out.print("<button class='btn btn-red btn-lg'>Guardar</button>");
                    out.print("</form>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            }
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(Tag_verificacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
