package Tags;

import Controladores.DefectoJpaController;
import Controladores.MovimientosJpaController;
import Controladores.SeguimientoJpaController;
import Controladores.SolicitudJpaController;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_seguimiento extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol = sesion.getAttribute("Rol").toString();
        String usuario = sesion.getAttribute("Nombre").toString();
        SolicitudJpaController jpa_solicitud = new SolicitudJpaController();
        SeguimientoJpaController jpa_seguimiento = new SeguimientoJpaController();
        MovimientosJpaController jpa_movimiento = new MovimientosJpaController();
        DefectoJpaController jpa_defectos = new DefectoJpaController();
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = formato.format(fechaActual);
        Date fecha = new Date();
        List lst_solicitud = null;
        List lst_movimientos = null;
        List lst_seguimientos = null;
        int id_solicitud = 0, var = 0;
        try {
            id_solicitud = Integer.parseInt(pageContext.getRequest().getAttribute("id_solicitud").toString());
        } catch (NumberFormatException e) {
            id_solicitud = 0;
        }
        try {
            var = Integer.parseInt(pageContext.getRequest().getAttribute("var").toString());
        } catch (NumberFormatException e) {
            var = 0;
        }
        try {
            lst_movimientos = jpa_movimiento.consultaMovimientos(id_solicitud);
            lst_seguimientos = jpa_seguimiento.consultaSeguimientos(id_solicitud);
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<div style='display:flex;align-items:center'>"
                    + "<div class='mr-2'><a class=\"btn btn-white btn-icon btn-sm\" data-toggle=\"tooltip\" href='Solicitud?opc=1&estado=1&btn_filter=" + var + "' data-placement=\"top\" title=\"\" data-original-title=\"Volver\"><i class=\"fas fa-arrow-left\"></i></a></div>"
                    + "<div><h1>Modulo Seguimiento</h1></div></div>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            if (var == 0) {
                //<editor-fold defaultstate="collapsed" desc="SOLICITUD - HERRAMENTAL">
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<h4>Listado de seguimiento(s)</h4>");
                lst_solicitud = jpa_solicitud.consultaSolicitudId(id_solicitud);
                if (lst_solicitud != null || lst_solicitud.size() > 0) {
                    Object[] obj_solicitud = (Object[]) lst_solicitud.get(0);
                    if (rol.equals("COORD.PR") || rol.equals("ADMIN")) {
//                        if (Integer.parseInt(obj_solicitud[10].toString()) < 100) {
//                            out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Registrar\"><i class='fas fa-plus'></i></button>");
//                        }
                        if (obj_solicitud[25] != null) {
                            if (Integer.parseInt(obj_solicitud[10].toString()) < 100 && !obj_solicitud[25].equals("Entrega")) {
                                out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Registrar\"><i class='fas fa-plus'></i></button>");
                            }
                        }else{
                            if (Integer.parseInt(obj_solicitud[10].toString()) < 100) {
                                out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Registrar\"><i class='fas fa-plus'></i></button>");
                            }
                        }
                    }
                    out.print("</div>");
                    if (rol.equals("COORD.PR") || rol.equals("ADMIN")) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR SEGUIMIENTO - MOVIMIENTOS">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                        out.print("<div class='cont_reg'>");

                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h4>Registrar Seguimiento</h4>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<form action='Seguimiento?opc=2' method='post' class='needs-validation' novalidate=''>");
                        out.print("<input type='hidden' name='idS' value='" + id_solicitud + "'>");
                        out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
                        out.print("<div class='col-8'>");
                        out.print("<input type='date' class='form-control' name='txt_fecha' id='txt_fecha' placeholder='Fecha' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' value='" + fechaFormateada + "' title='Fecha'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col-8'>");
                        out.print("<select class='form-control' name='slc_tipo'  required style='margin-top: 12px;margin-bottom: 12px;'>");
                        out.print("<option selected disabled value=''>Seleccione tipo</option>");
                        out.print("<option value='Avance'>Avance</option>");
                        out.print("<option value='Entrega'>Entrega</option>");
                        out.print("<option value='Cancelado'>Cancelado</option>");
                        out.print("</select>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        String arg_piezas = obj_solicitud[7].toString();
                        StringTokenizer token = new StringTokenizer(arg_piezas, "-");
                        int piezas = token.countTokens();
                        String[] datos = new String[piezas];
                        int ida = 0;
                        out.print("<div class='col-8'>");
                        out.print("<select class='form-control' name='slc_pieza' style='margin-top: 12px;margin-bottom: 12px;' required>");
                        out.print("<option selected disabled value=''>Seleccione pieza</option>");
                        while (token.hasMoreTokens()) {
                            String str = token.nextToken();
                            datos[ida] = str;
                            out.print("<option>" + datos[ida] + "</option>");
                            ida++;
                        }
                        out.print("</select>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div style='margin:auto' class='col-11' data-toggle='tooltip' data-placement='top' title='Descripción'>");
                        out.print("<textarea name='txt_descripcion' class='form-control' style='height:105px;' required placeholder='Descripción'></textarea>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='mt-2' style='width: 100%; text-align:center;'>");
                        out.print("<button class='btn btn-red btn-lg'>Registrar</button>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    out.print("<div class='card-body'>");
                    if (rol.equals("COORD.PR") || rol.equals("ADMIN")) {
                        out.print("<div class='divSpaceEvenly mb-4'>");
                        out.print("<div><a href='Seguimiento?opc=1&idS=" + id_solicitud + "&var=0' class='btn btn-rojo' data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Módulo Seguimiento\"><i class=\"fas fa-user-friends\"></i></a></div>");
                        out.print("<div><a href='Solicitud?opc=6&idS=" + id_solicitud + "&var=0' class='btn btn-outline-secondary' data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Módulo R-PM-001\"><i class=\"fas fa-wrench\"></i></a></div>");
                        out.print("</div>");
                    }
                    //<editor-fold defaultstate="collapsed" desc="TABLA MOVIMIENTO CABECERA">
                    out.print("<div class='LegDiv'># Solicitud: " + obj_solicitud[3] + "</div>");
                    out.print("<div class='StyleDiv3'>");
                    out.print("<div class='DivFlex'>");
                    out.print("<div class='StyleDiv2' style='font-weight: bold;'>Reportante:</div>"
                            + "<div class='StyleDiv2'>Prioridad:</div>"
                            + "<div class='StyleDiv2'>Ficha:</div>"
                            + "<div class='StyleDiv2'>Plano:</div>");
                    out.print("</div>");

                    out.print("<div class='DivFlex2'>");
                    out.print("<div class='StyleDiv4'>" + obj_solicitud[13] + " " + obj_solicitud[14] + "</div>"
                            + "<div class='StyleDiv4'>" + obj_solicitud[4] + "</div>"
                            + "<div class='StyleDiv4'>" + obj_solicitud[5] + "</div>"
                            + "<div class='StyleDiv4'>" + obj_solicitud[8] + "</div>");

                    out.print("</div>");

                    out.print("<div>");
                    out.print("<div  class='DivFlex'>");
                    out.print("<div class='StyleDiv2'>Cantidad:</div>"
                            + "<div class='StyleDiv2'>Estado:</div>"
                            + "<div class='StyleDiv2'>Pieza:</div>"
                            + "<div class='StyleDiv2'>Descripción:</div>");
                    out.print("</div>");

                    out.print("<div class='DivFlex2' style='margin-bottom:6px;'>");
                    out.print("<div class='StyleDiv4'>" + obj_solicitud[6] + "</div>"
                            + "<div class='StyleDiv4'>" + (Integer.parseInt(obj_solicitud[10].toString()) == 100 ? "<a style=\"color:white;\" class=\"btn btn-info btn-icon btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Estado Cerrado\"><i class=\"fas fa-lock\"></i></a>" : "<a style=\"color:white;\" class=\"btn btn-info btn-icon btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Estado Pendiente\"><i class=\"fas fa-lock-open\"></i></a>") + "</div>"
                            + "<div class='StyleDiv4'>" + obj_solicitud[7] + "</div>"
                            + "<div class='StyleDiv4'>" + obj_solicitud[9] + "</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    out.print("<div  class='mt-4'>");
                    out.print("<table class='table table-bordered'>");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th class='th1Seguimiento' style='border-radius: 5px 0px 0px 0px;' colspan='4'>Avance | Entrega</th>");
                    out.print("<th class='th1Seguimiento' style='border-radius: 0px 5px 0px 0px;' colspan='4'>Aprobado | Devolución</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th style='text-align: center; height:25px;'>Fecha</th>");
                    out.print("<th style='text-align: center; height:25px;'>Pieza</th>");
                    out.print("<th style='text-align: center; height:25px;'>Observación</th>");
                    out.print("<th style='text-align: center; height:25px;'>Responsable</th>");
                    out.print("<th style='text-align: center; height:25px;'>Fecha</th>");
                    out.print("<th style='text-align: center; height:25px;'>Observación</th>");
                    out.print("<th style='text-align: center; height:25px;'>Defecto</th>");
                    out.print("<th style='text-align: center; height:25px;'>Responsable</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    if (lst_movimientos != null) {
                        for (int i = 0; i < lst_movimientos.size(); i++) {
                            //<editor-fold defaultstate="collapsed" desc="CONTENIDO MOVIMIENTO">
                            out.print("<tr>");
                            Object[] obj_movimientos = (Object[]) lst_movimientos.get(i);
                            out.print("<td class='td_stl'>" + obj_movimientos[2] + "</td>");
                            out.print("<td class='td_stl'>" + obj_movimientos[3] + "</td>");
                            out.print("<td class='td_stl'>" + obj_movimientos[5] + "</td>");
                            out.print("<td class='td_stl'>" + obj_movimientos[6] + "</td>");
                            if (obj_movimientos[8] != null) {
                                String est = obj_movimientos[13].toString();
                                out.print("<td class='td_stl " + (est.equals("devolucion") ? "EstadoDV" : "EstadoAP") + "'>" + obj_movimientos[8] + "</td>");
                                out.print("<td class='td_stl " + (est.equals("devolucion") ? "EstadoDV" : "EstadoAP") + "'>" + obj_movimientos[10] + "</td>");
                                out.print("<td class='td_stl " + (est.equals("devolucion") ? "EstadoDV" : "EstadoAP") + "'>" + (obj_movimientos[9].toString().equals("N/A") ? "N/A" : "<b style='color:red'>" + obj_movimientos[9] + "</b>") + "</td>");
                                out.print("<td class='td_stl " + (est.equals("devolucion") ? "EstadoDV" : "EstadoAP") + "'>" + obj_movimientos[11] + "</td>");
                            } else {
                                if (rol.equals(obj_solicitud[15])) {
                                    if (obj_movimientos[4].equals("Entrega")) {
                                        if (obj_movimientos[13] == null) {
                                            out.print("<td   class='td_stl' colspan='4'>"
                                                    + "<div style='display:flex;justify-content: space-evenly;'>"
                                                    + "<div><a style=\"color: white; height: 25px;width: 30px;\" onclick='mostrarModalAP(" + i + ")' class=\"btn btn-success btn-icon btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Aprobar\"><i style='font-size: 17px !important;' class=\"fas fa-check\"></i></a></div>"
                                                    + "<div><a style=\"color: white; height: 25px;width: 30px;\" onclick='mostrarModalDV(" + i + ")' class=\"btn btn-danger btn-icon btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Devolver\"><i style='font-size: 17px !important;' class=\"fas fa-times\"></i></a></div>"
                                                    + "</div>"
                                                    + "</td>");
                                        }
                                    } else if (obj_movimientos[4].equals("Cancelado")) {
                                        out.print("<td class='td_stlAD' colspan='4'><div data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"" + obj_movimientos[4] + "\"><i style='font-size: 19px;color: #000000;' class=\"fas fa-exclamation-triangle\"></i></div></td>");
                                    } else {
                                        out.print("<td class='td_stlAD' colspan='4'><div data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"" + obj_movimientos[4] + "\"><i style='font-size: 19px;color: #275bbe;' class=\"fas fa-tasks\"></i></div></td>");
                                    }
                                } else {
                                    out.print("<td class='td_stl' style='background-color:#ccc' colspan='4'></td>");
                                }
                            }
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="APROBAR/FINALIZAR MOVIMIENTO Y SEGUIMIENTO">
                            out.print("<div class='sweet-local' tabindex='-1' id='ModalA" + i + "' style='opacity: 1.03; display:none;'>");
                            out.print("<div class='cont_movimiento'>");

                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h4>Aprobar movimiento</h4>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarModalAP(" + i + ")' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div class='cont_form_user'>");
                            out.print("<div class='col-lg-11 col-md-6'><p>Pieza: <b style='color:#6e1c17'>" + obj_movimientos[3] + "</b></p></div>");
                            out.print("<form action='Seguimiento?opc=3' method='post' class='needs-validation' novalidate=''>");
                            out.print("<input type='hidden' name='idS' value='" + id_solicitud + "'>");
                            out.print("<input type='hidden' name='idM' value='" + obj_movimientos[0] + "'>");
                            if (obj_solicitud[24] != null) {
                                out.print("<input type='hidden' name='idP' value='" + obj_solicitud[24] + "'>");
                            }
                            out.print("<div class='col-lg-11 col-md-6'>");
                            out.print("<input type='date' class='form-control' name='txt_fechaF' id='txt_fechaF' placeholder='Fecha' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' value='" + fechaFormateada + "' title='Fecha'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-11 col-md-6'>");
                            out.print("<input type='text' class='form-control' name='txt_encargado' id='txt_encargado' placeholder='Encargado' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' value='" + usuario + "' title='Encargado' style='background:#d9d9d9' readonly='false'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            if (obj_solicitud[19].equals("Electrodo")) {
                                out.print("<div style='margin:12px;' class='col-lg-11 col-md-6' data-toggle='tooltip' data-placement='top' title='Descripción'>");
                                out.print("<textarea name='txt_descripcionF' class='form-control' style='height:105px;' required placeholder='Descripción'>SEGÚN R-PM-013 FICHA N° " + obj_solicitud[5] + "</textarea>");
                                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                                out.print("</div>");
                            } else {
                                out.print("<div style=margin:12px;' class='col-lg-11 col-md-6' data-toggle='tooltip' data-placement='top' title='Descripción'>");
                                out.print("<textarea name='txt_descripcionF' class='form-control' style='height:105px;' required placeholder='Agregue descripcion para finalizar'></textarea>");
                                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                                out.print("</div>");
                            }
                            out.print("<div class='mt-2' style='width: 100%; text-align:center;'>");
                            out.print("<button class='btn btn-red btn-lg'>Finalizar</button>");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="DEVOLVER SEGUIMIENTO">
                            out.print("<div class='sweet-local' tabindex='-1' id='ModalD" + i + "' style='opacity: 1.03; display:none;'>");
                            out.print("<div class='cont_movimiento'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h4>Devolver movimiento</h4>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarModalDV(" + i + ")' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div class='cont_form_user'>");
                            out.print("<div class='col-lg-11 col-md-6'><p>Pieza: <b style='color:#6e1c17'>" + obj_movimientos[3] + "</b></p></div>");
                            out.print("<form action='Seguimiento?opc=4' method='post' class='needs-validation' novalidate=''>");
                            out.print("<input type='hidden' name='idS' value='" + id_solicitud + "'>");
                            out.print("<input type='hidden' name='idM' value='" + obj_movimientos[0] + "'>");
                            out.print("<div class='col-lg-11 col-md-6'>");
                            out.print("<input type='date' class='form-control' name='txt_fecha' id='txt_fecha' placeholder='Fecha' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' value='" + fechaFormateada + "' title='Fecha'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-11 col-md-6'>");
                            out.print("<select class='form-control' name='slc_defecto' style='margin-top: 12px;margin-bottom: 12px;' required>");
                            out.print("<option value='0'>Seleccione Defecto</option>");
                            List lst_defectos = jpa_defectos.consultaDefectos();
                            if (lst_defectos != null) {
                                for (int j = 0; j < lst_defectos.size(); j++) {
                                    Object[] obj_defectos = (Object[]) lst_defectos.get(j);
                                    out.print("<option value='" + obj_defectos[0] + "'>" + obj_defectos[1] + "</opction>");
                                }
                            }
                            out.print("</select>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("<div style=margin:12px;' class='col-lg-11 col-md-6' data-toggle='tooltip' data-placement='top' title='Descripción'>");
                            out.print("<textarea name='txt_descripcion' class='form-control' style='height:105px;' required placeholder='Agregue descripcion para finalizar' required></textarea>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("<div class='mt-2' style='width: 100%; text-align:center;'>");
                            out.print("<button class='btn btn-red btn-lg'>Devolver</button>");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
//                            //</editor-fold>
                        }
                    } else {
                        out.print("<td colspan='8' style='text-align:center;'>No existen movimientos registrados</td>");
                    }
                    out.print("</tr>");
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="MODULO FICHA TECNICA">
                List lst_solicitud_ficha = jpa_solicitud.consultaSolicitudIdFichaT(id_solicitud);
                if (lst_solicitud_ficha != null) {
                    Object[] obj_solicitud = (Object[]) lst_solicitud_ficha.get(0);
                    List lst_pendientes = jpa_solicitud.consultaPendientesHerramental(Integer.parseInt(obj_solicitud[20].toString()));
                    Object[] obj_pendiente = (Object[]) lst_pendientes.get(0);
                    out.print("<div class='card-header' style='justify-content: space-between;'>");
                    out.print("<h4>Listado de seguimiento(s)</h4>");
                    if (rol.equals("COORD.PR") || rol.equals("ADMIN")) {
                        if (Integer.parseInt(obj_solicitud[10].toString()) < 100) {
                            out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                        }
                    }
                    out.print("</div>");
                    if (rol.equals("COORD.PR") || rol.equals("ADMIN")) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR SEGUIMIENTO - MOVIMIENTOS">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                        out.print("<div class='cont_reg'>");

                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h4>Registrar Seguimiento</h4>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<form action='Seguimiento?opc=2' method='post' class='needs-validation' novalidate=''>");
                        out.print("<input type='hidden' name='idS' value='" + id_solicitud + "'>");
                        out.print("<input type='hidden' name='var' value='1'>");
                        out.print("<div class='col-lg-6 col-md-6' style='display: flex;'>");
                        out.print("<div class='col-12'>");
                        out.print("<input type='date' class='form-control' name='txt_fecha' id='txt_fecha' placeholder='Fecha' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' value='" + fechaFormateada + "' title='Fecha'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col-12'>");
                        out.print("<select class='form-control' name='slc_tipo'  required style='margin-top: 12px;margin-bottom: 12px;'>");
                        out.print("<option selected disabled value=''>Seleccione tipo</option>");
                        out.print("<option value='Avance'>Avance</option>");
                        out.print("<option value='Entrega'>Entrega</option>");
                        out.print("<option value='Cancelado'>Cancelado</option>");
                        out.print("</select>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div style='margin:auto' class='col-11' data-toggle='tooltip' data-placement='top' title='Descripción'>");
                        out.print("<textarea name='txt_descripcion' class='form-control' style='height:105px;' required placeholder='Descripción'></textarea>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='mt-2' style='width: 100%; text-align:center;'>");
                        out.print("<button class='btn btn-red btn-lg'>Registrar</button>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="TABLA MOVIMIENTO CABECERA">
                    out.print("<div class='card-body'>");
                    out.print("<div class='LegDiv'># Solicitud: " + obj_solicitud[3] + "</div>");
                    out.print("<div class='StyleDiv3'>");
                    out.print("<div class='DivFlex'>");
                    out.print("<div class='StyleDiv2' style='font-weight: bold;'>Reportante:</div>"
                            + "<div class='StyleDiv2'>Prioridad:</div>"
                            + "<div class='StyleDiv2'>Ficha Tecnica:</div>");
                    out.print("</div>");

                    out.print("<div class='DivFlex2'>");
                    out.print("<div class='StyleDiv4'>" + obj_pendiente[6] + "</div>"
                            + "<div class='StyleDiv4'>" + obj_solicitud[4] + "</div>"
                            + "<div class='StyleDiv4'>" + obj_pendiente[2] + "</div>");
                    out.print("</div>");

                    out.print("<div>");
                    if (obj_solicitud[7].toString().contains("<hr />")) {
                        out.print("<div  class='DivFlex'>"
                                + "<div class='StyleDiv2'>Descripción:</div>"
                                + "<div class='StyleDiv2'>Sugerencia:</div>"
                                + "<div class='StyleDiv2'>Estado</div>");
                        out.print("</div>");
                    } else {
                        out.print("<div  class='DivFlex'>"
                                + "<div class='StyleDiv2'>Descripción:</div>"
                                + "<div class='StyleDiv2'>Estado</div>");
                        out.print("</div>");
                    }
                    out.print("<div class='DivFlex2' style='margin-bottom:6px;'>");
                    if (obj_solicitud[7].toString().contains("<hr />")) {
                        String[] Desc = obj_solicitud[7].toString().replace("<p>", "").replaceAll("</p>", "").replace("<strong>Causas: </strong>", "").replace("<strong>Sugerencias: </strong>", "").split("<hr />");
                        out.print("<div class='StyleDiv4'>" + Desc[0] + "</div>"
                                + "<div class='StyleDiv4'>" + Desc[1] + "</div>"
                                + "<div class='StyleDiv4'>" + (Integer.parseInt(obj_solicitud[8].toString()) == 100 ? "<a style=\"color:white;\" class=\"btn btn-info btn-icon btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Estado Cerrado\"><i class=\"fas fa-lock\"></i></a>" : "<a style=\"color:white;\" class=\"btn btn-info btn-icon btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Estado Pendiente\"><i class=\"fas fa-lock-open\"></i></a>") + "</div>");
                    } else {
                        out.print("<div class='StyleDiv4'>" + obj_solicitud[7] + "</div>"
                                + "<div class='StyleDiv4'>" + (Integer.parseInt(obj_solicitud[8].toString()) == 100 ? "<a style=\"color:white;\" class=\"btn btn-info btn-icon btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Estado Cerrado\"><i class=\"fas fa-lock\"></i></a>" : "<a style=\"color:white;\" class=\"btn btn-info btn-icon btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Estado Pendiente\"><i class=\"fas fa-lock-open\"></i></a>") + "</div>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    out.print("<div  class='mt-4'>");
                    out.print("<table class='table table-bordered'>");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th class='th1Seguimiento' style='border-radius: 5px 0px 0px 0px;' colspan='3'>Avance | Entrega</th>");
                    out.print("<th class='th1Seguimiento' style='border-radius: 0px 5px 0px 0px;' colspan='4'>Aprobado | Devolución</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th style='text-align: center; height:25px;'>Fecha</th>");
                    out.print("<th style='text-align: center; height:25px;'>Observación</th>");
                    out.print("<th style='text-align: center; height:25px;'>Responsable</th>");
                    out.print("<th style='text-align: center; height:25px;'>Fecha</th>");
                    out.print("<th style='text-align: center; height:25px;'>Observación</th>");
                    out.print("<th style='text-align: center; height:25px;'>Defecto</th>");
                    out.print("<th style='text-align: center; height:25px;'>Responsable</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    if (lst_movimientos != null) {
                        for (int i = 0; i < lst_movimientos.size(); i++) {
                            //<editor-fold defaultstate="collapsed" desc="CONTENIDO MOVIMIENTO">
                            out.print("<tr>");
                            Object[] obj_movimientos = (Object[]) lst_movimientos.get(i);
                            out.print("<td class='td_stl'>" + obj_movimientos[2] + "</td>");
                            out.print("<td class='td_stl'>" + obj_movimientos[5] + "</td>");
                            out.print("<td class='td_stl'>" + obj_movimientos[6] + "</td>");
                            if (obj_movimientos[8] != null) {
                                String est = obj_movimientos[13].toString();
                                out.print("<td class='td_stl " + (est.equals("devolucion") ? "EstadoDV" : "EstadoAP") + "'>" + obj_movimientos[8] + "</td>");
                                out.print("<td class='td_stl " + (est.equals("devolucion") ? "EstadoDV" : "EstadoAP") + "'>" + obj_movimientos[10] + "</td>");
                                out.print("<td class='td_stl " + (est.equals("devolucion") ? "EstadoDV" : "EstadoAP") + "'>" + (obj_movimientos[9].toString().equals("N/A") ? "N/A" : "<b style='color:red'>" + obj_movimientos[9] + "</b>") + "</td>");
                                out.print("<td class='td_stl " + (est.equals("devolucion") ? "EstadoDV" : "EstadoAP") + "'>" + obj_movimientos[11] + "</td>");
                            } else {
                                if (rol.equals(obj_solicitud[13])) {
                                    if (obj_movimientos[4].equals("Entrega")) {
                                        if (obj_movimientos[13] == null) {
                                            out.print("<td   class='td_stl' colspan='4'>"
                                                    + "<div style='display:flex;justify-content: space-evenly;'>"
                                                    + "<div><a style=\"color: white; height: 25px;width: 30px;\" onclick='mostrarModalAP(" + i + ")' class=\"btn btn-success btn-icon btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Aprobar\"><i style='font-size: 17px !important;' class=\"fas fa-check\"></i></a></div>"
                                                    + "<div><a style=\"color: white; height: 25px;width: 30px;\" onclick='mostrarModalDV(" + i + ")' class=\"btn btn-danger btn-icon btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Devolver\"><i style='font-size: 17px !important;' class=\"fas fa-times\"></i></a></div>"
                                                    + "</div>"
                                                    + "</td>");
                                        }
                                    } else {
                                        out.print("<td class='td_stlAD' colspan='4'><div data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"" + obj_movimientos[4] + "\"><i style='font-size: 19px;color: #275bbe;' class=\"fas fa-tasks\"></i></div></td>");
                                    }
                                } else {
                                    out.print("<td class='td_stl' style='background-color:#ccc' colspan='4'></td>");
                                }
                            }
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="APROBAR/FINALIZAR MOVIMIENTO Y SEGUIMIENTO">
                            out.print("<div class='sweet-local' tabindex='-1' id='ModalA" + i + "' style='opacity: 1.03; display:none;'>");
                            out.print("<div class='cont_movimiento'>");

                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h4>Aprobar movimiento</h4>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarModalAP(" + i + ")' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div class='cont_form_user'>");
                            out.print("<form action='Seguimiento?opc=3' method='post' class='needs-validation' novalidate=''>");
                            out.print("<input type='hidden' name='idS' value='" + id_solicitud + "'>");
                            out.print("<input type='hidden' name='idM' value='" + obj_movimientos[0] + "'>");
                            out.print("<input type='hidden' name='var' value='1'>");
                            if (obj_solicitud[20] != null) {
                                out.print("<input type='hidden' name='idP' value='" + obj_solicitud[20] + "'>");
                            }
                            out.print("<div class='col-lg-11 col-md-6'>");
                            out.print("<input type='date' class='form-control' name='txt_fechaF' id='txt_fechaF' placeholder='Fecha' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' value='" + fechaFormateada + "' title='Fecha'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-11 col-md-6'>");
                            out.print("<input type='text' class='form-control' name='txt_encargado' id='txt_encargado' placeholder='Encargado' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' value='" + usuario + "' title='Encargado' style='background:#d9d9d9' readonly='false'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("<div style=margin:12px;' class='col-lg-11 col-md-6' data-toggle='tooltip' data-placement='top' title='Descripción'>");
                            out.print("<textarea name='txt_descripcionF' class='form-control' style='height:105px;' required placeholder='Agregue descripcion para finalizar'></textarea>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("<div class='mt-2' style='width: 100%; text-align:center;'>");
                            out.print("<button class='btn btn-red btn-lg'>Finalizar</button>");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="DEVOLVER SEGUIMIENTO">
                            out.print("<div class='sweet-local' tabindex='-1' id='ModalD" + i + "' style='opacity: 1.03; display:none;'>");
                            out.print("<div class='cont_movimiento'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h4>Devolver movimiento</h4>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarModalDV(" + i + ")' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div class='cont_form_user'>");
                            out.print("<form action='Seguimiento?opc=4' method='post' class='needs-validation' novalidate=''>");
                            out.print("<input type='hidden' name='idS' value='" + id_solicitud + "'>");
                            out.print("<input type='hidden' name='idM' value='" + obj_movimientos[0] + "'>");
                            out.print("<input type='hidden' name='var' value='1'>");
                            out.print("<div class='col-lg-11 col-md-6'>");
                            out.print("<input type='date' class='form-control' name='txt_fecha' id='txt_fecha' placeholder='Fecha' required='' autocomplete='off' data-toggle='tooltip' data-placemente='top' value='" + fechaFormateada + "' title='Fecha'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-11 col-md-6'>");
                            out.print("<select class='form-control' name='slc_defecto' style='margin-top: 12px;margin-bottom: 12px;' required>");
                            out.print("<option value='0'>Seleccione Defecto</option>");
                            List lst_defectos = jpa_defectos.consultaDefectos();
                            if (lst_defectos != null) {
                                for (int j = 0; j < lst_defectos.size(); j++) {
                                    Object[] obj_defectos = (Object[]) lst_defectos.get(j);
                                    out.print("<option value='" + obj_defectos[0] + "'>" + obj_defectos[1] + "</opction>");
                                }
                            }
                            out.print("</select>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("<div style=margin:12px;' class='col-lg-11 col-md-6' data-toggle='tooltip' data-placement='top' title='Descripción'>");
                            out.print("<textarea name='txt_descripcion' class='form-control' style='height:105px;' required placeholder='Agregue descripcion para finalizar' required></textarea>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                            out.print("<div class='mt-2' style='width: 100%; text-align:center;'>");
                            out.print("<button class='btn btn-red btn-lg'>Devolver</button>");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
//                            //</editor-fold>
                        }
                    } else {
                        out.print("<td colspan='8' style='text-align:center;'>No existen movimientos registrados</td>");
                    }
                    out.print("</tr>");
                    out.print("</tbody>");
                    out.print("</table>");
                }
                //</editor-fold>
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_seguimiento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
