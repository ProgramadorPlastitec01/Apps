package Tags;

import Controladores.MovimientosJpaController;
import Controladores.PlanoJpaController;
import Controladores.SolicitudJpaController;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_pendiente extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        SolicitudJpaController jpa_solicitud = new SolicitudJpaController();
        PlanoJpaController jpa_plano = new PlanoJpaController();
        MovimientosJpaController jpa_movimiento = new MovimientosJpaController();
        Date fecha = new Date();
        List lst_pendientes = null;
        List lst_plano = null;
        List lst_Fichas = null;
        int var = 0;
        DateFormat FechaYHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int id_solicitud = 0;
        try {
            try {
                id_solicitud = Integer.parseInt(pageContext.getRequest().getAttribute("id_solicitud").toString());
            } catch (Exception e) {
                id_solicitud = 0;
            }
            if (pageContext.getRequest().getAttribute("Pendiente").toString().equals("Herramental")) {
                //<editor-fold defaultstate="collapsed" desc="HERRAMENTAL">
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Módulo Pendiente (Herramental)</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header'>");
                out.print("<h4>Listado de pendientes</h4>");
                out.print("</div>");

                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-bordered' id='table-1'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Fecha</th>");
                out.print("<th>Molde</th>");
                out.print("<th>Cavidades</th>");
                out.print("<th>Causas</th>");
                out.print("<th>Sugerencias</th>");
                out.print("<th>Responsable</th>");
                out.print("<th>Solicitud</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_pendientes = jpa_solicitud.consultaPendientesHerramental();
                if (lst_pendientes != null) {
                    for (int i = 0; i < lst_pendientes.size(); i++) {
                        Object[] obj_pendientes = (Object[]) lst_pendientes.get(i);
                        lst_plano = jpa_plano.consultaPlanoNombre(obj_pendientes[2].toString());
                        int test = Integer.parseInt(obj_pendientes[0].toString());
                        List lst_solicitud = jpa_solicitud.consultaSolicitudIdPendiente(test);
                        if (lst_plano == null) {
                            jpa_plano.registroPlano("Moldes", obj_pendientes[1].toString(), FechaYHora.format(fecha));
                            lst_plano = jpa_plano.consultaPlanoNombre(obj_pendientes[1].toString());
                        }
                        Object[] obj_plano = (Object[]) lst_plano.get(0);
                        out.print("<tr>");
                        out.print("<td>" + obj_pendientes[3] + "</td>");
                        out.print("<td>" + obj_pendientes[2] + "</td>");
                        String[] arg_descripcion = obj_pendientes[4].toString().replace("<hr />", "<hr/>").split("<hr/>");
                        for (int j = 0; j < arg_descripcion.length; j++) {
                            out.print("<td valign='top'>" + arg_descripcion[j].replace("<strong>Causas: </strong>", "").replace("<strong>Sugerencias: </strong>", "").replace("<div>", "").replace("</div>", "").replace("<p>", "").replace("</p>", "").replace("*", "") + "</td>");
                        }
                        out.print("<td>" + obj_pendientes[5] + "</td>");
                        if (lst_solicitud == null) {
                            out.print("<td align='center'><a href='#' onclick='javascript:document.formS" + i + ".submit();' style='color:white;' class='btn btn-red btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Registrar Solicitud'><i style='font-size:13px;' class=\"fas fa-folder-plus\"></i></a></td>");
                            out.print("<form action='Solicitud?opc=1' method='post' name='formS" + i + "' id='formS" + i + "'>");
                            out.print("<input type='hidden' name='idPd' value='" + obj_pendientes[0] + "'>");
                            out.print("<input type='hidden' name='idP' value='" + obj_plano[0] + "'>");
                            out.print("<input type='hidden' name='desc' value='" + arg_descripcion[0].replace("<strong>Causas: </strong><br />", "").replace("<div>", "").replace("</div>", "").replace("<p>", "").replace("</p>", "") + "'>");
                            out.print("<input type='hidden' name='btn_bus' value='2'>");
                            out.print("</td>");
                            out.print("</form>");
                        } else {
                            Object[] obj_solicitud = (Object[]) lst_solicitud.get(0);
                            out.print("<td align='center'><a href='Seguimiento?opc=1&idS=" + obj_solicitud[0] + "' style='color:white;' class='btn btn-info btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Consultar Solicitud'><i style='font-size:13px;' class='fas fa-eye'></i></a></td>");
                        }
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr><td colspan='7'>No existe pendiente registrados</td></tr>");
                }
                out.print("</tbody>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Pendiente").toString().equals("Visual_solicitud")) {
                //<editor-fold defaultstate="collapsed" desc="VISUAL DE SOLICITUD APP HERRAMENTAL">
                try {
                    var = Integer.parseInt(pageContext.getRequest().getAttribute("var").toString());
                } catch (Exception e) {
                    var = 0;
                }
                List lst_solicitud = null;
                List lst_movimientos = null;
                lst_solicitud = jpa_solicitud.consultaSolicitudId(id_solicitud);
                lst_movimientos = jpa_movimiento.consultaMovimientos(id_solicitud);
                if (lst_solicitud != null) {
                    Object[] obj_solicitud = (Object[]) lst_solicitud.get(0);
                    out.print("<div class='BannerVista'>");
                    out.print("<h2>Módulo Seguimiento <img src='Interfaz/Contenido/Imagen/solicitud_proyectos.png' style='width: 68px; height: 61px' alt=''> <b style='color:#b72e27'>Solicitudes Proyectos</b></h2>");
                    out.print("</div>");
                    out.print("<div style='margin-left:94%'><button class='btn buttomSP' style='border-radius: 4px;' onclick='abrirVentana();' data-toggle='tooltip' data-placement='top' title='Ingresar a Solicitud Proyectos'>"
                            + "<img src='Interfaz/Contenido/Imagen/solicitud_proyectos.png' style='width: 34px; height: 32px' alt=''></button>"
                            + "</div>");
                    out.print("<div class='card-body mt-2' '>");
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
                            String est = obj_movimientos[13].toString();
                            out.print("<td class='td_stl " + (est.equals("devolucion") ? "EstadoDV" : "EstadoAP") + "'>" + obj_movimientos[8] + "</td>");
                            out.print("<td class='td_stl " + (est.equals("devolucion") ? "EstadoDV" : "EstadoAP") + "'>" + obj_movimientos[10] + "</td>");
                            out.print("<td class='td_stl " + (est.equals("devolucion") ? "EstadoDV" : "EstadoAP") + "'>" + (obj_movimientos[9].toString().equals("N/A") ? "N/A" : "<b style='color:red'>" + obj_movimientos[9] + "</b>") + "</td>");
                            out.print("<td class='td_stl " + (est.equals("devolucion") ? "EstadoDV" : "EstadoAP") + "'>" + obj_movimientos[11] + "</td>");
                            //</editor-fold>
                        }
                    } else {
                        out.print("<td colspan='8' style='text-align:center;'>No existen movimientos registrados</td>");
                    }
                    out.print("</tr>");
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                }
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Pendiente").toString().equals("Ficha_tecnica")) {
                //<editor-fold defaultstate="collapsed" desc="FICHA TECNICA">
                String desc = "";
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Modulo Pendiente (Ficha Técnica)</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header'>");
                out.print("<h4>Listado de pendientes </h4>");
                out.print("</div>");

                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-bordered' id='table-1'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Fecha</th>");
                out.print("<th>Ficha técnica</th>");
                out.print("<th>Causas</th>");
                out.print("<th>Sugerencias</th>");
                out.print("<th>Responsable</th>");
                out.print("<th>Solicitud</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_Fichas = jpa_solicitud.consultaSolicitudfichaPendientes();
                if (lst_Fichas != null) {
                    for (int i = 0; i < lst_Fichas.size(); i++) {
                        Object[] obj_pendientes = (Object[]) lst_Fichas.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_pendientes[2] + "</td>");
                        out.print("<td>" + obj_pendientes[4] + "</td>");
                        if (!obj_pendientes[5].equals("")) {
                            String[] arg_descripcion = obj_pendientes[5].toString()
                                    .replace("<strong>Causas: </strong><br>", "")
                                    .replace("<div contenteditable=\"true\">", "")
                                    .replace("<div contenteditable=\"true\">", "")
                                    .replace("<strong>Sugerencias: </strong><br>", "")
                                    .replace("<p style=\"height: auto;\">", "")
                                    .replace("</div>", "")
                                    .replace("\n", "")
                                    .replace("</p>", "")
                                    .replace("<p style='height: auto;'>\n", "")
                                    .replace("*", "")
                                    .split("<hr />");
                            out.print("<td>" + arg_descripcion[0] + "</td>");
                            out.print("<td>" + arg_descripcion[1] + "</td>");
                            desc = arg_descripcion[0];
                        } else {
                            out.print("<td>Fallo en descripción</td>");
                            out.print("<td>Fallo en descripción</td>");
                        }
                        out.print("<td>" + obj_pendientes[6] + "</td>");
                        List lst_solicitud = jpa_solicitud.consultaSolicitudIdPendiente((Integer) obj_pendientes[0]);
                        if (lst_solicitud == null) {
                            out.print("<td align='center'><a href='#' onclick='javascript:document.formS" + i + ".submit();' style='color:white;' class='btn btn-red btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Registrar Solicitud'><i style='font-size:13px;' class=\"fas fa-folder-plus\"></i></a></td>");
                            out.print("<form action='Solicitud?opc=1&var=1' method='post' name='formS" + i + "' id='formS" + i + "'>");
                            out.print("<input type='hidden' name='idf' value='" + obj_pendientes[1] + "'>");
                            out.print("<input type='hidden' name='idPd' value='" + obj_pendientes[0] + "'>");
                            out.print("<input type='hidden' name='btn_bus' value='3'>");
                            out.print("<input type='hidden' name='desc' value='" + desc
                                    .replace("<strong>Causas: </strong><br />", "")
                                    .replace("<div>", "")
                                    .replace("</div>", "")
                                    .replace("<p>", "")
                                    .replace("</p>", "") + "'>");
                            out.print("</form>");
                            out.print("</td>");
                        } else {
                            Object[] obj_solicitud = (Object[]) lst_solicitud.get(0);
                            out.print("<td align='center'><a href='Seguimiento?opc=1&idS=" + obj_solicitud[0] + "&var=1' style='color:white;' class='btn btn-info btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Consultar Solicitud'><i style='font-size:13px;' class='fas fa-eye'></i></a></td>");
                        }
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr><td colspan='7' style='text-align:center;'>No existe pendiente registrados</td></tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_pendiente.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
