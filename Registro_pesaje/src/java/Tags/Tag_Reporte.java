package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import Controladores.OrdenJpaController;
import Controladores.RegistroDetalleJpaController;
import Controladores.TiempoDescontableJpaController;
import Controladores.RegistroJpaController;
import java.text.DecimalFormat;
import java.util.List;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.DefectoJpaController;

public class Tag_Reporte extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        OrdenJpaController OrdenJpa = new OrdenJpaController();
        RegistroDetalleJpaController RegistroDetalleJpa = new RegistroDetalleJpaController();
        TiempoDescontableJpaController TiempoJpa = new TiempoDescontableJpaController();
        DefectoJpaController DefectoJpa = new DefectoJpaController();
        RegistroJpaController RegistroJpa = new RegistroJpaController();
        String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
        List lst_reporte = null;
        List lst_registroDell = null;
        List lst_tiempo = null;
        List lst_cont_tiempo = null;
        List lst_cont_defectos = null;
        List lst_total_tiempo = null;
        List lst_defectos = null;
        List lst_cont_defecto = null;
        List lst_orden = null;
        List lst_ordenes = null;
        List lst_registro = null;
        List lst_cuarentenaOrden = null;
        int id_orden = 0, id_registro = 0;
        double tiempo_peso = 0;
        double total_final = 0;
        int total_finalV2 = 0;
        int total = 0;
        int calculo = 0;
        int var = 1;
        int var2 = 1;
        int t_defecto = 0, estado = 0, variable = 0, temp = 0, turno = 0;
        String tara = "";
        float peso1 = 0, peso2 = 0, peso3 = 0, peso4 = 0, peso5 = 0, peso6 = 0, peso7 = 0, peso8 = 0, total_pesaje = 0;
        DecimalFormat decimal = new DecimalFormat("####.##");
        try {
            try {
                id_orden = Integer.parseInt(pageContext.getRequest().getAttribute("id_orden").toString());
            } catch (Exception e) {
                id_orden = 0;
            }
            try {
                id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("id_registro").toString());
            } catch (Exception e) {
                id_registro = 0;
            }
            try {
                variable = Integer.parseInt(pageContext.getRequest().getAttribute("variable").toString());
            } catch (Exception e) {
                variable = 0;
            }
            try {
                turno = Integer.parseInt(pageContext.getRequest().getAttribute("turno").toString());
            } catch (Exception e) {
                turno = 0;
            }
            lst_tiempo = TiempoJpa.ConsultarTiempoDescontable();
            lst_defectos = DefectoJpa.ConsultarDefectos();
            lst_registroDell = RegistroDetalleJpa.ConsultarRegistroDetalle_id(id_registro);
            lst_reporte = OrdenJpa.ReporteConsultaGeneral(id_orden, id_registro, turno);
            out.print("<div class='page-wrapper'>");
            out.print("<div class='page-breadcrumb bg-white'>");
            //<editor-fold defaultstate="collapsed" desc="CABECERA DE PAGINA">
            out.print("<div class='row align-items-center'>");
            out.print("<div class='col-lg-3 col-md-4 col-sm-4 col-xs-12'>");
            out.print("<h4 class='page-title'>Reporte</h4>");
            out.print("</div>");
            out.print("<div class='col-lg-9 col-sm-8 col-md-8 col-xs-12'>");
            out.print("<div class='d-md-flex' style='height: 33px;'>");
            out.print("<ol class='breadcrumb ms-auto'>");
            out.print("<li>");
            out.print("</li>");
            out.print("</ol>");
            if (lst_reporte != null || lst_tiempo != null || lst_defectos != null || lst_registroDell != null) {
                out.print("<a href='Registro_detalle?opc=1&id_registro=" + id_registro + "&id_orden=" + id_orden + "' "
                        + "class='btn btn-info d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white' style='color: #fff;background: #f33155; margin-right: 5px; height:33px; border: 1px solid #f33155;'>Detalle "
                        + "<i class='fas fa-star'></i></a>");
            }
            out.print("<a href='Reporte?opc=1&var=1' "
                    + "class='btn btn-info d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white' style='color: #fff;background: #469ee9; margin-right: 5px; height:33px; border: 1px solid #469ee9;'>Filtro "
                    + "<i class='fas fa-search'></i></a>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            if (variable == 1) {
                //<editor-fold defaultstate="collapsed" desc="FILTRO DE BUSQUEDA">
                lst_ordenes = OrdenJpa.ConsultarOrden();
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_filtro'>");

                out.print("<div style='display:flex;justify-content: space-between;'>");
                out.print("<div><h4>Filtro Reporte</h4></div>");
                out.print("<div><button class='btn_clsRg' onclick='mostrarConvencion(1)'><i class=\"fas fa-times\"></i></button></div>");
                out.print("</div>");
                out.print("<form id='myForm' action='Reporte?opc=1&var=" + ((id_orden == 0) ? "1" : "0") + "' method='post'>");
                if (id_orden != 0) {
                    lst_orden = OrdenJpa.ConsultarOrdenId(id_orden);
                    Object[] obj_orden = (Object[]) lst_orden.get(0);
                    out.print("<b>Orden Producción</b>");
                    out.print("<input name='id_orden' id='id_orden' value='" + obj_orden[0] + "' type='hidden'> ");
                    out.print("<input type='text' class='form-control' name='orden' id='orden' placeholder='Orden producción' readonly='false' value='" + obj_orden[1] + "'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('orden');val1.add(Validate.Presence);</script>");
                    out.print("<b>Registro</b>");
                    lst_registro = RegistroJpa.ConsultarRegistroFiltro(id_orden);
                    if (lst_registro != null && lst_registro.size() > 0) {
                        out.print("");
                        out.print("<select class='form-control' name='id_registro' id='id_registro' placeholder='Seleccionar Registro' onchange=\"this.form.submit()\">");
                        out.print("<option data-icon='glyphicon glyphicon-eye-open' data-subtext=\"petrification\" value='0'>Selecccione Registro</option>");
                        for (int i = 0; i < lst_registro.size(); i++) {
                            Object[] obj_registro = (Object[]) lst_registro.get(i);
//                            out.print("<option  data-content=\"<i class='fa fa-address-book-o' aria-hidden='true'></i>Option1\"  value='" + obj_registro[0] + "'>" + obj_registro[5] + " - " + obj_registro[6] + "</option>");
                            out.print("<option  " + ((Integer.parseInt(obj_registro[9].toString()) == 0) ? "style='color:#02992a;font-weight: bold;'" : "style='color:#001348;font-weight: bold;'") + " value='" + obj_registro[0] + "'>"
                                    + "" + obj_registro[5] + " - " + obj_registro[6] + " - Turno " + obj_registro[21] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('id_registro');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    } else {
                        out.print("<div style='display:flex;'>");
                        out.print("<div style='width:88%'><input type='text' class='form-control' readonly='false' value='No existes registros'></div>"
                                + "<div style='width:12%'><a href='Reporte?opc=1&var=1'><button style='width:100%;height:100%;border:1px solid #c1c1c1;color:#469ee9;' type=\"button\" title=\"Buscar nuevamente\"><i class='fas fa-search'></i></button></a></div>");
                        out.print("</div>");
                    }
                } else {
                    out.print("<b>Orden Producción</b>");
                    if (lst_ordenes != null) {
                        out.print("<select class='form-control'   name='id_orden' id='id_orden' placeholder='Seleccionar Orden' onchange=\"this.form.submit()\">");
                        out.print("<option value='0'>Selecccione Orden</option>");
                        for (int i = 0; i < lst_ordenes.size(); i++) {
                            Object[] obj_ordenes = (Object[]) lst_ordenes.get(i);
                            out.print("<option  value='" + obj_ordenes[0] + "'>" + obj_ordenes[1] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('id_orden');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    } else {
                        out.print("<div style='display:flex;'>");
                        out.print("<div style='width:88%'><input type='text' class='form-control' readonly='false' value='No existes Ordenes'></div>"
                                + "<div style='width:12%'><a href='Reporte?opc=1&var=1'><button style='width:100%;height:100%;border:1px solid #c1c1c1;color:#469ee9;' type=\"button\" title=\"Buscar nuevamente\"><i class='fas fa-search'></i></button></a></div>");
                        out.print("</div>");
                    }
                }
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            out.print("<div class='container-fluid'>");
            out.print("<div class='row'>");
            out.print("<div class='col-sm-12'>");
            out.print("<div class='white-box'>");
            if (lst_reporte != null || lst_tiempo != null || lst_defectos != null || lst_registroDell != null) {
                Object[] obj_reporte = (Object[]) lst_reporte.get(0);
                tara = obj_reporte[15].toString();
                //<editor-fold defaultstate="collapsed" desc="CABECERA DE CONTENIDO">
                out.print("<div style='border: 1px solid black;border-radius: 6px;'>");
                out.print("<div class='table-responsive'>");
                out.print("<table id='reporte' class='table table-bordered' >");
                out.print("<tr style='text-align:center;background:#d5d5d5;border-top-left-radius: 6px;border-top-right-radius: 6px'>"
                        + "<td style='padding:0; font-size: 13px;' colspan='4'><b style='color:black;'>COPIA NO CONTROLADA</b></td></tr>");
                out.print("<tr><td style='text-align:center;background:#04589f;padding: 7px;color:white' colspan='4'><b>OP " + obj_reporte[4] + "</b></td></tr>");
                out.print("<tr><td style='text-align:center;background:#469ee9;padding: 7px;color:white' colspan='4'><b>" + obj_reporte[5] + "</b> - " + obj_reporte[6] + "</td></tr>");
                out.print("<tr>");
                out.print("<td style='text-align:center'><b>Fecha Día</b><br> " + obj_reporte[9] + "</td>");
                out.print("<td style='text-align:center'><b>Plan</b><br> " + obj_reporte[34] + "</td>");
                out.print("<td style='text-align:center'><b>Centro de Costo</b><br> " + obj_reporte[32] + "</td>");
                out.print("<td style='text-align:center'><b>Lote</b><br> " + obj_reporte[10] + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='text-align:center'><b>Maquina</b><br> " + obj_reporte[13] + "</td>");
                out.print("<td style='text-align:center'><b>Molde</b><br> " + obj_reporte[14] + "</td>");
                out.print("<td style='text-align:center'><b>Peso x Unidades (Tara) </b><br> " + ((obj_reporte[15].toString().contains("///")) ? obj_reporte[15].toString().split("///")[0] + "-" + obj_reporte[15].toString().split("///")[1] : "") + " un</td>");
                out.print("<td style='text-align:center'><b>Estiba</b><br> " + obj_reporte[11] + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='text-align:center'><b>Cant. Programada</b><br> " + obj_reporte[7] + "</td>");
                out.print("<td style='text-align:center'><b>Peso Programado</b><br> " + obj_reporte[8] + "</td>");
                out.print("<td style='text-align:center'><b>Unidades x Empaque</b><br>" + obj_reporte[36] + " un</td>");
                out.print("<td style='text-align:center'><b>Cant. Revisada</b><br> " + ((obj_reporte[30] == null) ? "0" : obj_reporte[30]) + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='text-align:center'><b>Recipiente</b><br> " + obj_reporte[16] + "</td>");
                out.print("<td style='text-align:center'><b>Bolsa</b><br> " + obj_reporte[18] + "</td>");
                out.print("<td style='text-align:center'><b>Observaciones</b><br> " + obj_reporte[12] + "</td>");
                out.print("<td style='text-align:center'><b>Coordinador(a)</b><br> " + obj_reporte[37] + "</b></td>");
                estado = Integer.parseInt(obj_reporte[33].toString());
                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DEFECTOS">
                out.print("<div class='table-responsive'>");
                out.print("<table id='reporte' class='table table-bordered'>");
                out.print("<tr style='text-align:center;background:#d5d5d5;'>"
                        + "<td style='padding:0; font-size: 13px;' colspan='8' ><b style='color:black;'>DEFECTOS</b></td></tr>");
                out.print("<tbody>");
                out.print("<tr style='text-align:center;background: #469ee9;color:white'><td style='padding: 7px' colspan='8'><b>PRODUCTO NO CONFORME</td></tr>");
                lst_cuarentenaOrden = RegistroDetalleJpa.ConsultarCuarentenasXOrderTurno(id_orden, id_registro, turno);
                if (lst_cuarentenaOrden != null) {
                    if (!lst_cuarentenaOrden.isEmpty()) {
                        Object[] obj_regDeta = (Object[]) lst_cuarentenaOrden.get(0);
                        if (obj_regDeta[5] != null) {
                            String[] defecto = obj_regDeta[5].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                            for (int j = 0; j < defecto.length; j++) {
                                if (j == 0) {
                                    out.print("<tr style='font-size:13px; text-align:center;'>");
                                } else if (j == 4) {
                                    out.print("<tr style='font-size:13px; text-align:center;'>");
                                } else if (j == 8) {
                                    out.print("<tr style='font-size:13px; text-align:center;'>");
                                } else if (j == 12) {
                                    out.print("<tr style='font-size:13px; text-align:center;'>");
                                } else if (j == 16) {
                                    out.print("<tr style='font-size:13px; text-align:center;'>");
                                } else if (j == 20) {
                                    out.print("<tr style='font-size:13px; text-align:center;'>");
                                }
                                lst_cont_defectos = OrdenJpa.TotalDefectoReporte(id_orden, var, id_registro, turno);
                                if (lst_cont_defectos != null) {
                                    Object[] obj_resul_defectos = (Object[]) lst_cont_defectos.get(0);
                                    out.print("<td><b>" + obj_resul_defectos[2] + "</b></td>");
                                    out.print("<td style='text-align:center'>" + obj_resul_defectos[1] + "</td>");
                                } else {
                                    out.print("<td>Fallo en taer defectos</td>");
                                }
                                if (j == 3) {
                                    out.print("</tr>");
                                } else if (j == 7) {
                                    out.print("</tr>");
                                } else if (j == 11) {
                                    out.print("</tr>");
                                } else if (j == 15) {
                                    out.print("</tr>");
                                } else if (j == 19) {
                                    out.print("</tr>");
                                } else if (j == 23) {
                                    out.print("</tr>");
                                }
                                var++;
                            }
                            out.print("<tr style='font-size:13px'><td style='text-align:center;' colspan='8'><b>TOTAL PNC:   </b>");
                            for (int j = 0; j < defecto.length; j++) {
                                lst_cont_defecto = OrdenJpa.ContadorTotalDefecto(id_orden, var2, id_registro, turno);
                                Object[] obj_defecto = (Object[]) lst_cont_defecto.get(0);
                                t_defecto = t_defecto + Integer.parseInt(obj_defecto[1].toString());
                                var2++;
                                if (j == (defecto.length - 1)) {
                                    out.print(t_defecto);
                                }
                            }
                            out.print("</td></tr>");
                        } else {
                            out.print("<tr><td style='text-align:center;' colspan='8'>No existe defectos registrados</td></tr>");
                        }
                    } else {
                        out.print("<tr><td style='text-align:center;' colspan='8'>No existe defectos registrados</td></tr>");
                    }
                } else {
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
                //</editor-fold>
                out.print("<div class='table-responsive'>");
                out.print("<table id='reporte' class='table table-bordered' >");
                //<editor-fold defaultstate="collapsed" desc="HEADER TABLA">
                int tamanioCol = 12 + lst_tiempo.size();
                out.print("<thead><tr style='text-align:center;background:#d5d5d5;'>"
                        + "<td style='padding:0; font-size: 13px;' colspan='" + tamanioCol + "' ><b style='color:black;'>TURNOS</b></td></tr></thead>");
                out.print("<thead  style='vertical-align: bottom;background: #469ee9;border: 1px solid #0000003d'>");
                out.print("<tr align='center' style='vertical-align: middle'>");
                out.print("<th style='' class='border-top-0' rowspan='2'><b>Turno</b></th>");
                out.print("<th class='border-top-0' rowspan='2'><b>Operaria</b></th>");
                out.print("<th class='border-top-0' colspan='8'><b>Hora (Und)</b></th>");
                out.print("<th class='border-top-0' rowspan='2'><b>Cant.<br>Total</b></th>");
                out.print("<th class='border-top-0' colspan='" + lst_tiempo.size() + "'><b>Tiempo (Min)</b></th>");
                out.print("<th class='border-top-0' rowspan='2'><b>Total <br/> Tiempo</b></th>");
                out.print("</tr>");
                out.print("<tr align='center' style='vertical-align: middle'>");
                out.print("<th class='border-top-0'>1</th>");
                out.print("<th class='border-top-0'>2</th>");
                out.print("<th class='border-top-0'>3</th>");
                out.print("<th class='border-top-0'>4</th>");
                out.print("<th class='border-top-0'>5</th>");
                out.print("<th class='border-top-0'>6</th>");
                out.print("<th class='border-top-0'>7</th>");
                out.print("<th class='border-top-0'>8</th>");
                for (int i = 0; i < lst_tiempo.size(); i++) {
                    Object[] obj_tiempo = (Object[]) lst_tiempo.get(i);
                    out.print("<th class='border-top-0'>" + obj_tiempo[1].toString().replace(" ", "<br/>") + "</th>");
                }
                out.print("</tr>");
                out.print("</thead>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONTENIDO TABLA">
                out.print("<tbody>");
                if (lst_registroDell != null && lst_registroDell.size() > 0) {
                    for (int i = 0; i < lst_registroDell.size(); i++) {
                        Object[] obj_regDetll = (Object[]) lst_registroDell.get(i);
                        out.print("<tr align='center'>");
                        //<editor-fold defaultstate="collapsed" desc="CONTENIDO EN CICLO">
                        out.print("<td>" + obj_regDetll[2] + "</td>");
                        String Des_arg = obj_regDetll[3].toString().replace("[", "").replace("]", "");
                        out.print("<td><p class='tooltip9'><span>" + Des_arg.split(" - ")[1] + "</span>");
                        out.print("<span class='tooltiptext'>" + Des_arg.split(" - ")[0] + "</span></p></td>");
//                    peso1 = Math.abs(Integer.parseInt(obj_regDetll[6].toString()));
                        peso1 = Float.parseFloat(obj_regDetll[45].toString());
                        peso2 = Float.parseFloat(obj_regDetll[46].toString());
                        peso3 = Float.parseFloat(obj_regDetll[47].toString());
                        peso4 = Float.parseFloat(obj_regDetll[48].toString());
                        peso5 = Float.parseFloat(obj_regDetll[49].toString());
                        peso6 = Float.parseFloat(obj_regDetll[50].toString());
                        peso7 = Float.parseFloat(obj_regDetll[51].toString());
                        peso8 = Float.parseFloat(obj_regDetll[52].toString());
                        total_pesaje = peso1 + peso2 + peso3 + peso4 + peso5 + peso6 + peso7 + peso8;
                        out.print("<td>" + peso1 + "</td>");
                        out.print("<td>" + peso2 + "</td>");
                        out.print("<td>" + peso3 + "</td>");
                        out.print("<td>" + peso4 + "</td>");
                        out.print("<td>" + peso5 + "</td>");
                        out.print("<td>" + peso6 + "</td>");
                        out.print("<td>" + peso7 + "</td>");
                        out.print("<td>" + peso8 + "</td>");
                        out.print("<td><b>" + total_pesaje + "</b></td>");
                        if (obj_regDetll[34] != null) {
                            //<editor-fold defaultstate="collapsed" desc="CONTENIDO TIEMPO - ESTRUCTURA POR ARRAY []">
                            int k = 0;
                            String[] tiempo = obj_regDetll[34].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                            for (k = 0; k < tiempo.length; k++) {
                                out.print("<td>");
                                String[] Des_tiempo = tiempo[k].toString().split("/");
                                for (int j = k; j < lst_tiempo.size(); j++) {
                                    Object[] obj_tiempo = (Object[]) lst_tiempo.get(j);
                                    if (obj_tiempo[1].toString().contains(Des_tiempo[1].toString())) {
                                        out.print("" + Des_tiempo[0] + "");
                                        j = lst_tiempo.size();
                                    }
                                }
                                out.print(" </td>");
                            }
                            if (lst_tiempo.size() > tiempo.length) {
                                calculo = lst_tiempo.size() - tiempo.length;
                                for (int j = 0; j < calculo; j++) {
                                    out.print("<td>0</td>");
                                }
                            }
                            //</editor-fold>
                        } else {
                            for (int j = 0; j < lst_tiempo.size(); j++) {
                                out.print("<td>0</td>");
                            }
                        }
                        out.print("<td><b>");
                        if (obj_regDetll[34] != null) {
                            //TIEMPO DESCONTABLE TOTAL
                            String[] tiempo = obj_regDetll[34].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                            for (int j = 0; j < tiempo.length; j++) {
                                String[] Des_tiempo = tiempo[j].toString().split("/");
                                total = total + Integer.parseInt(Des_tiempo[0].toString());
                            }
                            tiempo_peso = Integer.parseInt(obj_regDetll[43].toString());
                            //CALCULO TIEMPO DESCONTABLE - TIEMPO TOTAL DE CADA PESO
                            total_final = tiempo_peso - total;
                            if (total_final <= 60) {
                                out.println(decimal.format(total_final) + " min");
                            } else {
                                int minutos = Double.valueOf(Math.floor(total_final)).intValue();
                                int cal_horas = Double.valueOf((total_final / 60)).intValue();
                                int cal_min = (minutos % 60);
//                                out.println(decimal.format(total_final) + " h");
//                                out.println(cal_horas + ":" + cal_min);
                                out.println(cal_horas + "h " + cal_min + "m");
                            }
                            total = 0;
                        } else {
                            //CALCULO SIN TIEMPO DESCONTABLE          
                            total_finalV2 = (Integer.parseInt(obj_regDetll[43].toString()));
                            if (total_finalV2 <= 60) {
                                out.println(decimal.format(total_finalV2) + " min");
                            } else {
                                int min = Double.valueOf(Math.floor(total_finalV2)).intValue();
                                int cal_hora = Double.valueOf((total_finalV2 / 60)).intValue();
                                int cal_min = (min % 60);
                                out.println(cal_hora + "h " + cal_min + "m");
                            }

                        }
                        out.print("</b></td>");
                        //</editor-fold>
                        out.print("</tr>");
                    }
                } else {
                    int conteo_cols = 12 + lst_tiempo.size();
                    out.print("<tr><td style='text-align:center;' colspan='" + conteo_cols + "'>No existe registros</td></tr>");
                }
                if (obj_reporte[20] != null) {
                    out.print("<tr align='center'>");
                    //<editor-fold defaultstate="collapsed" desc="TOTALES">
                    out.print("<td colspan='2' style='border-top-width: medium;' ><b style='color:#469ee9' >Totales</b></td>");
                    //<editor-fold defaultstate="collapsed" desc="TOTALES POR PESO HORA A HORA">
                    float TotalH1 = Float.parseFloat(obj_reporte[22].toString());
                    float TotalH2 = Float.parseFloat(obj_reporte[23].toString());
                    float TotalH3 = Float.parseFloat(obj_reporte[24].toString());
                    float TotalH4 = Float.parseFloat(obj_reporte[25].toString());
                    float TotalH5 = Float.parseFloat(obj_reporte[26].toString());
                    float TotalH6 = Float.parseFloat(obj_reporte[27].toString());
                    float TotalH7 = Float.parseFloat(obj_reporte[28].toString());
                    float TotalH8 = Float.parseFloat(obj_reporte[29].toString());
                    float TotalPesaje = Float.parseFloat(obj_reporte[30].toString());
                    out.print("<td >" + TotalH1 + "</td>");
                    out.print("<td >" + TotalH2 + "</td>");
                    out.print("<td >" + TotalH3 + "</td>");
                    out.print("<td >" + TotalH4 + "</td>");
                    out.print("<td >" + TotalH5 + "</td>");
                    out.print("<td >" + TotalH6 + "</td>");
                    out.print("<td >" + TotalH7 + "</td>");
                    out.print("<td >" + TotalH8 + "</td>");
                    out.print("<td ><b>" + TotalPesaje + "</b></td>");
                    //</editor-fold>
                    if (lst_registroDell != null) {
                        for (int i = 0; i < lst_registroDell.size(); i++) {
                            //<editor-fold defaultstate="collapsed" desc="TOTALES TIEMPO DESCONTABLE">
                            Object[] obj_regDetll = (Object[]) lst_registroDell.get(i);
                            if (obj_regDetll[34] != null) {
                                String[] tiempo = obj_regDetll[34].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                                var = 1;
                                for (int l = 0; l < tiempo.length; l++) {
                                    out.print("<td >");
                                    lst_cont_tiempo = OrdenJpa.ContadorTotalTiempo(id_orden, id_registro, var);
                                    Object[] obj_resul = (Object[]) lst_cont_tiempo.get(0);
                                    out.print(obj_resul[1]);
                                    out.print("</td>");
                                    var++;
                                    i = lst_registroDell.size();
                                }
                                if (lst_tiempo.size() > tiempo.length) {
                                    calculo = lst_tiempo.size() - tiempo.length;
                                    for (int j = 0; j < calculo; j++) {
                                        out.print("<td ><b>0</b></td>");
                                    }
                                }
                            } else {
                                for (int j = 0; j < lst_tiempo.size(); j++) {
                                    out.print("<td>0</td>");
                                }
                                i = lst_registroDell.size();
                            }
                            //</editor-fold>
                        }
                        out.print("<td><b>");
                        Object[] obj_regDetll = (Object[]) lst_registroDell.get(0);
                        if (obj_regDetll[34] != null) {
                            //<editor-fold defaultstate="collapsed" desc="TOTALES CALCULO TIEMPO HORA A HORA - TIEMPO DESCONTABLE">
                            String[] tiempo = obj_regDetll[34].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                            lst_total_tiempo = OrdenJpa.TotalTiempo(id_orden, id_registro, tiempo.length);
                            Object[] obj_total = (Object[]) lst_total_tiempo.get(0);
                            String tiempo_total = String.valueOf(Integer.parseInt(obj_total[1].toString()));
                            int t_peso_total = Integer.parseInt(obj_reporte[31].toString());
                            total_final = t_peso_total - Double.parseDouble(tiempo_total);
                            if (total_final >= 60) {
                                int minutos = Double.valueOf(Math.floor(total_final)).intValue();
                                int cal_horas = Double.valueOf((total_final / 60)).intValue();
                                int cal_min = (minutos % 60);
//                                out.println(decimal.format(total_final) + " h");
//                                out.println(cal_horas + ":" + cal_min);
                                out.println(cal_horas + "h " + cal_min + "m");
                            } else {
                                out.println(decimal.format(total_final) + " min");
                            }
                            //</editor-fold>
                        } else {
                            out.print("0 h");
                        }
                        out.print("</b></td>");
                    }
                    //</editor-fold>
                    out.print("</tr>");
                }
                out.print("</tbody>");
                //</editor-fold>
                out.print("</table>");
                out.print("</div>");
                //<editor-fold defaultstate="collapsed" desc="RESPONSABLES">
                out.print("<div class='table-responsive'>");
                out.print("<table id='reporte' class='table table-bordered'>");
                out.print("<thead><tr style='text-align:center;background:#d5d5d5;'>"
                        + "<td style='padding:0; font-size: 13px;' colspan='15' ><b style='color:black;'>RESPONSABLES</b></td></tr></thead>");

                out.print("<tr style='font-size:14px; border: 1px solid #0000003d;text-align:center;background: #469ee9;color:white'>");
                out.print("<td style='padding:7px; max-width: 40px;' class='border-top-0' rowspan='2' colspan='2'><b>TURNO</td>");
//                out.print("<td style='padding:7px' class='border-top-0' rowspan='2' colspan='2'><b>GRUPO</td>");
                out.print("<td style='padding:7px; width: 350px;' class='border-top-0' rowspan='2' colspan='1'><b>ENCARGADA</td>");
                out.print("<td style='padding:7px' class='border-top-0' style='padding: 7px' colspan='7'><b>OBSERVACIÓN</td></tr>");

                out.print("<tr style='font-size:14px; border: 1px solid #0000003d;text-align:center;background: #469ee9;color:white'>");
                out.print("<td style='padding:7px' style='padding: 7px;' colspan='1'><b>HORA</td>");
                out.print("<td style='padding:7px' style='padding: 7px;' colspan='1'><b>ASUNTO</td>");
                out.print("<td style='padding:7px' style='padding: 7px' colspan='5'><b>JUSTIFICACIÓN</td>");
                out.print("</tr>");
                if (lst_registroDell != null && lst_registroDell.size() > 0) {
                    for (int i = 0; i < lst_registroDell.size(); i++) {
                        Object[] obj_regResp = (Object[]) lst_registroDell.get(i);
                        out.print("<tr style='font-size:14px;'>");
                        if (obj_regResp[56] != null) {
                            String[] Arg_observacion = obj_regResp[56].toString().replace("][", "===").replace("[", "").replace("]", "").replace("//", "").split("--");
                            int Cant_obs = 0;
                            Cant_obs = Arg_observacion.length + 1;
                            out.print("<td class='border-top-0' rowspan='" + Cant_obs + "' colspan='2' style='text-align:center;'>" + obj_regResp[2] + "</td>");
                            out.print("<td class='border-top-0' rowspan='" + Cant_obs + "' colspan='1' style='text-align:center;'><b>" + obj_regResp[3].toString().replace("[", "").replace("]", "").split(" - ")[0] + "</b></td>");
//                            out.print("<td class='border-top-0' rowspan='" + Cant_obs + "' colspan='2' style='text-align:center;'>" + obj_regResp[55] + "</td></tr>");
                            for (int j = 0; j < Arg_observacion.length; j++) {
                                out.print("<tr>");
                                String[] Des_definitivo = Arg_observacion[j].split("===");
                                for (int k = 0; k < Des_definitivo.length; k++) {
                                    out.print("<td  style='padding: 2px;text-align:center;' colspan='" + ((k == 2) ? "5" : "1") + "'>" + Des_definitivo[k] + "</td>");
                                }
                                out.print("</tr>");
                            }
                        } else {
//                            out.print("<td class='border-top-0' colspan='2' style='text-align:center;'>" + obj_regResp[2] + "</td>");
//                            out.print("<td class='border-top-0' colspan='1' style='text-align:center;'><b>" + obj_regResp[3].toString().replace("[", "").replace("]", "").split(" - ")[0] + "</b></td>");
////                            out.print("<td class='border-top-0' colspan='2' style='text-align:center;'>" + obj_regResp[55] + "</td>");
//                            out.print("<td colspan='7' style='text-align:center;'>Sin Observación</td></tr>");
                        }
                    }
                } else {
                    out.print("<tr style='font-size:16px;'>");
                    out.print("<td class='border-top-0' colspan='15' style='text-align:center;'>No existe registros</td>");
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");
                out.print("</div> <!-- Fin -->");
            } else {
                out.print("<h1>No existe consulta</h1>");
            }
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='cleaner'></div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
        } catch (Exception ex) {
            Logger.getLogger(Tag_Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
