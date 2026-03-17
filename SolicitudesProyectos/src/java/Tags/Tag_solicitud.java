package Tags;

import Controladores.CabeceraEtdJpaController;
import Controladores.ElectrodoJpaController;
import Controladores.PlanoJpaController;
import Controladores.SolicitudJpaController;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_solicitud extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        PlanoJpaController jpa_plano = new PlanoJpaController();
        SolicitudJpaController jpa_solicitud = new SolicitudJpaController();
        CabeceraEtdJpaController jpa_cabecera = new CabeceraEtdJpaController();
        ElectrodoJpaController jpa_electrodo = new ElectrodoJpaController();
        String rol = sesion.getAttribute("Rol").toString();
        int area = Integer.parseInt(sesion.getAttribute("Area").toString());
        int id_usuario = Integer.parseInt(sesion.getAttribute("id").toString());
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = formato.format(fechaActual);
        String nombre = sesion.getAttribute("Nombre").toString();
        List lst_planos = jpa_plano.consultaPlanos();
        List lst_tipoP = jpa_plano.consultaTipoPlano();
        List lst_plano = null;
        List lst_solicitudes = null;
        List lst_solicitudes_filtrada = null;
        List lst_cabecera = null;
        List lst_itmVerPln = null;
        List lst_solicitud = null;
        List lst_piezas = null;
        List lst_fichas = null;
        List lst_pendiente = null;
        int id_pendiente_ft = 0;
        int rgt_proyectos = 0;
        int btn_filter = 0, id_solicitud = 0, id_plano = 0, id_ficha = 0, estado = 0, temp = 0, tempC = 0;
        String nsolicitud = "", fechaI = "", fechaF, filtro = "", busqueda = "";
        try {
            try {
                rgt_proyectos = Integer.parseInt(pageContext.getRequest().getAttribute("registro").toString());
            } catch (NumberFormatException e) {
                rgt_proyectos = 0;
            }
            if (rgt_proyectos == 0) {
                //<editor-fold defaultstate="collapsed" desc="MÓDULO DE SOLICITUDES PROYECTOS">
                //<editor-fold defaultstate="collapsed" desc="VARIABLES">
                try {
                    id_solicitud = Integer.parseInt(pageContext.getRequest().getAttribute("id_solicitud").toString());
                } catch (NumberFormatException e) {
                    id_solicitud = 0;
                }
                try {
                    id_plano = Integer.parseInt(pageContext.getRequest().getAttribute("id_plano").toString());
                } catch (NumberFormatException e) {
                    id_plano = 0;
                }
                try {
                    id_ficha = Integer.parseInt(pageContext.getRequest().getAttribute("id_ficha").toString());
                } catch (NumberFormatException e) {
                    id_ficha = 0;
                }
                try {
                    id_pendiente_ft = Integer.parseInt(pageContext.getRequest().getAttribute("id_pendiente").toString());
                } catch (NumberFormatException e) {
                    id_pendiente_ft = 0;
                }
                try {
                    btn_filter = Integer.parseInt(pageContext.getRequest().getAttribute("filtro_btns").toString());
                } catch (NumberFormatException e) {
                    btn_filter = 1;
                }
                try {
                    estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                } catch (NumberFormatException e) {
                    estado = 0;
                }
                //</editor-fold>
                if (id_solicitud > 0) {
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR SOLICITUD">
                    lst_solicitud = jpa_solicitud.consultaSolicitudId(id_solicitud);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_solicitudM'>");
                    if (lst_solicitud != null) {
                        Object[] obj_solicitud = (Object[]) lst_solicitud.get(0);
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h3>Modificar Solicitud</h3>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");

                        out.print("<form action='Solicitud?opc=3' onsubmit='modificarS();' method='post' name='form1' >");
                        out.print("<input type='hidden' name='idS' value='" + id_solicitud + "'>");
                        out.print("<input type='hidden' name='txt_solicitud' value='" + obj_solicitud[3] + "'>");
                        out.print("<input type='hidden' name='txt_ficha' value='" + obj_solicitud[5] + "'>");
                        out.print("<input type='hidden' name='filtro_btns' value='" + btn_filter + "'>");

                        out.print("<div class='col-lg-7 col-md-4' style='display: flex;padding-left:1px;'>");
                        out.print("<div class='col-11 pb-2'>");
                        out.print("<input type='text' class='form-control' value='" + obj_solicitud[3] + "' placeholder='Numero Solicitud' disabled autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Numero Solicitud'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col-11 pb-2' >");
                        out.print("<input type='text' class='form-control' value='" + obj_solicitud[13] + "' placeholder='Responsable' disabled autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Responsable'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-7 col-md-4' style='display: flex;padding-left:1px;'>");
                        out.print("<div class='col-11 pb-2'>");
                        out.print("<input type='text' class='form-control' value='" + obj_solicitud[10] + "' placeholder='Estado' disabled autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Estado'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col-11 pb-2'>");
                        out.print("<input type='text' class='form-control' value='" + (((Integer) obj_solicitud[5] == 0) ? "N/A" : obj_solicitud[5]) + "' placeholder='Ficha' disabled autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Ficha'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-12 pb-2' data-toggle='tooltip' data-placemente='top' title='Plano al que pertenece'>");
                        out.print("<select class='select2' name='idP' required id='plano-id' style='margin-top: 12px;margin-bottom: 12px;' onchange='Javascript:document.formPlano1.submit();'>");
                        out.print("<option style='display:none;' value='" + obj_solicitud[18] + "'>" + obj_solicitud[6] + "</option>");
                        for (int i = 0; i < lst_tipoP.size(); i++) {
                            Object[] obj_tipo = (Object[]) lst_tipoP.get(i);
                            out.print("<optgroup label='" + obj_tipo[0] + "'>");
                            for (int j = 0; j < lst_planos.size(); j++) {
                                Object[] obj_planos = (Object[]) lst_planos.get(j);
                                if (obj_tipo[0].equals(obj_planos[2])) {
                                    out.print("<option value='" + obj_planos[0] + "'>" + obj_planos[1] + "</opction>");
                                }
                            }
                            out.print("</optgroup>");
                        }
                        out.print("</optgroup>");
                        out.print("</select>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='col-12 pb-2'>");
                        out.print("<textarea class='form-control' name='txt_pieza'  id='piezax-id' value='" + obj_solicitud[7] + "' required onchange='javascript:this.value=this.value.toUpperCase();' placeholder='Las piezas se deben separar con guiones (-)' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Piezas'>" + obj_solicitud[7] + "</textarea>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-12 pb-1' data-toggle='tooltip' data-placemente='top' title='Prioridad'>");
                        out.print("<select class='select2' required name='slc_prioridad'  id='select-id' style='margin-top: 12px; margin-bottom: 12px;' required>");
                        out.print("<option selected >" + obj_solicitud[4] + "</option>");
                        out.print("<option title='Importante y urgente, esta prioridad se atenderá de manera inmediata, usualmente implica que hay parada de máquina colpitt o screen, se pueden suspender montajes en máquinas de taller para dar cumplimiento a esta solicitud.'>");
                        out.print("1A - INMEDIATA</option>");
                        out.print("<option title='Importante y urgente, esta prioridad se atenderá dentro de las siguientes 24 horas. Esto implica no suspender de inmediato trabajos que se estén ejecutando en las máquinas del taller. Se puede usar, por ejemplo, para riesgo de parada de máquinas o parada de otras máquinas.'>");
                        out.print("2A - 24 HORAS</option>");
                        out.print("<option title='Importante y no urgente, se atenderá en el transcurso de una semana. Por ejemplo, mantenimiento de electrodos, solicitud de repuestos en cantidades mínimas.'>");
                        out.print("1B - 1 SEMANA</option>");
                        out.print("<option title='Importante y no urgente, se atenderá en el transcurso de un mes. Por ejemplo, repuestos en general en cantidades razonables de los cuales se debe contar con stock.'>");
                        out.print("2B - 1 MES</option>");
                        out.print("<option title='No importante no urgente, esta prioridad no tiene límite de tiempo, si no se ha atendido en el transcurso de un año se reprogramará o eliminará según la necesidad, se puede asignar a trabajos para ensayos, arreglos estéticos, repuestos de los cuales se tiene stock o no son críticos, etc.'>");
                        out.print("1C - SIN LÍMITE DE TIEMPO</option>");
                        out.print("</select>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una prioridad.</div>");
                        out.print("</div>");

                        if (obj_solicitud[8].toString().contains("-")) {
                            String[] cantidad = obj_solicitud[8].toString().split("-");
                            out.print("<div class='pb-2' style='display:flex;justify-content: space-evenly;'>");
                            out.print("<div class='col-lg-6'>"
                                    + "<input type='number' class='form-control' name='txt_cantidad' id='cantidad-id' value='" + cantidad[0] + "' size='10' min='1' required placeholder='Cantidad' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Cantidad'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                            out.print("<div class='col-lg-6'>"
                                    + "<input type='text' class='form-control' name='txt_tipo' id='Tipo-id' value='" + cantidad[1] + "' placeholder='Tipo' onchange='javascript:this.value=this.value.toUpperCase();' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Tipo'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                            out.print("</div>");
                        } else {
                            out.print("<div class='col-12 pb-2'>"
                                    + "<input type='number' class='form-control' name='txt_cantidad' id='cantidad-id' size='10' value='" + obj_solicitud[8] + "' min='1' required placeholder='Cantidad' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Cantidad'>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                        }
                        if (btn_filter == 2) {
                            out.print("<div class='col-12 pb-2'>");
                            out.print("<div style='font-size:14px;padding: 0px 15px;height: 42px;border: 1px solid #e4e6fc;border-radius: 0.25rem;background-color:#e9ecef' data-toggle='tooltip' data-placemente='top' title='Descripción'>" + obj_solicitud[9].toString().replace("<strong>Cavidades des-habilitadas: </strong>", "</br><strong>Cavidades des-habilitadas: </strong>") + "</div>");
                            out.print("</div>");
                            out.print("<input type='hidden' name='txt_descripcion' value='" + obj_solicitud[9] + "'>");
                        } else {
                            out.print("<div class='col-12 pb-2'>");
                            out.print("<textarea class='form-control' name='txt_descripcion' id='descripcion-id'  value='" + obj_solicitud[9] + "' onchange='javascript:this.value=this.value.toUpperCase();' placeholder='Descripción' required onchange='javascript:this.value=this.value.toUpperCase();' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Descripción'>" + obj_solicitud[9] + "</textarea>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                        }

                        out.print("<div class='col-12 pb-2'>");
                        out.print("<input type='text' class='form-control'  name='txt_solicitante' value='" + nombre + "' placeholder='Solicitante' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Solicitante' style='background-color:#e9ecef; cursor:no-drop;'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='col-12 pb-3'>");
                        out.print("<input type='text' class='form-control'  name='txt_justificacion' value='' required placeholder='Justificacion' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Justificación'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='' style='width: 100%; text-align:center;'>");
                        out.print("<button class='btn btn-red btn-lg'>Modificar</button>");
                        out.print("</div>");

                        out.print("</form>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>                    
                }
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR SOLICITUD">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_solicitud'>");
                //<editor-fold defaultstate="collapsed" desc="PARTE 1 - CONSULTA">
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar Solicitud</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<form action='Solicitud?opc=1&desc=' onsubmit='checkSubmit();' method='post' name='formPlano' id='formPlano'>");

                out.print("<div class='col-12 pb-3'>");
                out.print("<input type='text' class='form-control' name='txt_sol' id='solicitud-id' value='' style='cursor:no-drop' placeholder='Numero Solicitud' readonly='true' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Numero Solicitud'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");

                out.print("<div class='col-lg-12 pb-3' data-toggle='tooltip' data-placemente='top' title='Plano al que pertenece'>");
                out.print("<select class='select2 form-control'  name='idP' id='plano-id'  style='margin-top: 12px;margin-bottom: 12px;' onchange='Javascript:document.formPlano.submit();'>");
                out.print("<option value='0'>Seleccione Plano</option>");
                for (int i = 0; i < lst_tipoP.size(); i++) {
                    Object[] obj_tipo = (Object[]) lst_tipoP.get(i);
                    out.print("<optgroup label='" + obj_tipo[0] + "'>");
                    for (int j = 0; j < lst_planos.size(); j++) {
                        Object[] obj_plano = (Object[]) lst_planos.get(j);
                        if (obj_tipo[0].equals(obj_plano[2])) {
                            out.print("<option value='" + obj_plano[0] + "'>" + obj_plano[1] + "</option>");
                        }
                    }
                    out.print("</optgroup>");
                }
                out.print("</optgroup>");
                out.print("</select>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Seleccione un plano!</div>");
                out.print("</div>");

                out.print("</form>");

                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                if (id_plano > 0) {
                    //<editor-fold defaultstate="collapsed" desc="FORMULARIO REGISTRO SOLICITUD">
                    String descripcion = "";
                    int id_pendiente = 0;
                    //<editor-fold defaultstate="collapsed" desc="VARIABLES">
                    try {
                        descripcion = pageContext.getRequest().getAttribute("descripcion").toString();
                    } catch (Exception e) {
                        descripcion = "";
                    }
                    try {
                        id_pendiente = Integer.parseInt(pageContext.getRequest().getAttribute("id_pendiente").toString());
                    } catch (Exception e) {
                        id_pendiente = 0;
                    }
                    try {
                        nsolicitud = pageContext.getRequest().getAttribute("nsolicitud").toString();
                    } catch (Exception e) {
                        nsolicitud = "";
                    }
                    //</editor-fold>
                    lst_plano = jpa_plano.consultaPlanoId(id_plano);
                    if (lst_plano != null) {
                        Object[] obj_plano = (Object[]) lst_plano.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='cont_solicitud'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h3>Registrar Solicitud</h3>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2);' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        //<editor-fold defaultstate="collapsed" desc="CABECERA REGISTRO">
                        out.print("<form action='Solicitud?opc=1&desc=' onsubmit='checkSubmit();' method='post' name='formPlano1' id='formPlano1'>");
                        out.print("<div class='col-12 pb-3'>");
                        out.print("<input type='hidden' class='form-control' name='txt_solicitud' id='solicitud-id3' value='" + nsolicitud + "'>");
                        out.print("<input type='text' class='form-control' name='txt_sol' id='solicitud-id2' value='" + nsolicitud + "' style='cursor:no-drop' placeholder='Numero Solicitud' disabled autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Numero Solicitud'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col-lg-12 pb-3' data-toggle='tooltip' data-placemente='top' title='Plano al que pertenece'>");
                        out.print("<select class='select2' name='idP' required id='plano-id' style='margin-top: 12px;margin-bottom: 12px;' onchange='Javascript:document.formPlano1.submit();'>");
                        out.print("<option style='display:none;' value='" + obj_plano[0] + "'>" + obj_plano[1] + "</option>");
                        for (int i = 0; i < lst_tipoP.size(); i++) {
                            Object[] obj_tipo = (Object[]) lst_tipoP.get(i);
                            out.print("<optgroup label='" + obj_tipo[0] + "'>");
                            for (int j = 0; j < lst_planos.size(); j++) {
                                Object[] obj_planos = (Object[]) lst_planos.get(j);
                                if (obj_tipo[0].equals(obj_planos[2])) {
                                    out.print("<option value='" + obj_planos[0] + "'>" + obj_planos[1] + "</option>");
                                }
                            }
                            out.print("</optgroup>");
                        }
                        out.print("</optgroup>");
                        out.print("</select>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("</form>");
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="FORMULARIO REGISTRO">
                        out.print("<form action='Solicitud?opc=2' onsubmit='checkSubmit();' method='post' name='formSolicitud' id='formSolicitud' class='needs-validation' novalidate='' >");
                        out.print("<input type='hidden' name='txt_solicitud' id='solicitud-id4' value='" + nsolicitud + "'>");
                        out.print("<input type='hidden' name='idP' value='" + id_plano + "'>");
                        lst_piezas = jpa_electrodo.consultaPiezasIdPlano(id_plano);
                        if (lst_piezas != null) {
                            out.print("<div class='col-lg-12 pb-2' data-toggle='tooltip' data-placemente='top' title='Plano al que pertenece'>");
                            out.print("<select class='select2'  required onChange='Agregar()' name='slc_piezas'  id='piezas-id' style='margin-top: 12px;margin-bottom: 12px;'>");
                            out.print("<option value='N/A' selected>N/A</option>");
                            for (int i = 0; i < lst_piezas.size(); i++) {
                                Object[] obj_piezas = (Object[]) lst_piezas.get(i);
                                out.print("<option value='" + obj_piezas[0] + "'>" + obj_piezas[1] + "/" + obj_piezas[3] + "</option>");
                            }
                            out.print("</select>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                        }
                        out.print("<div class='col-12 pb-2'>");
                        out.print("<textarea class='form-control' name='txt_pieza'  id='piezax-id' value='' required onchange='javascript:this.value=this.value.toUpperCase();' placeholder='Las piezas se deben separar con guiones (-)' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Piezas'></textarea>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-12 pb-1'>");
                        out.print("<select class='select2 form-control' name='slc_prioridad'  id='select-id' style='margin-top: 12px; margin-bottom: 12px;' required>");
                        out.print("<option selected disabled value=''>Seleccione la prioridad</option>");
                        out.print("<option title='Importante y urgente, esta prioridad se atenderá de manera inmediata, usualmente implica que hay parada de máquina colpitt o screen, se pueden suspender montajes en máquinas de taller para dar cumplimiento a esta solicitud.'>");
                        out.print("1A - INMEDIATA</option>");
                        out.print("<option title='Importante y urgente, esta prioridad se atenderá dentro de las siguientes 24 horas. Esto implica no suspender de inmediato trabajos que se estén ejecutando en las máquinas del taller. Se puede usar, por ejemplo, para riesgo de parada de máquinas o parada de otras máquinas.'>");
                        out.print("2A - 24 HORAS</option>");
                        out.print("<option title='Importante y no urgente, se atenderá en el transcurso de una semana. Por ejemplo, mantenimiento de electrodos, solicitud de repuestos en cantidades mínimas.'>");
                        out.print("1B - 1 SEMANA</option>");
                        out.print("<option title='Importante y no urgente, se atenderá en el transcurso de un mes. Por ejemplo, repuestos en general en cantidades razonables de los cuales se debe contar con stock.'>");
                        out.print("2B - 1 MES</option>");
                        out.print("<option title='No importante no urgente, esta prioridad no tiene límite de tiempo, si no se ha atendido en el transcurso de un año se reprogramará o eliminará según la necesidad, se puede asignar a trabajos para ensayos, arreglos estéticos, repuestos de los cuales se tiene stock o no son críticos, etc.'>");
                        out.print("1C - SIN LÍMITE DE TIEMPO</option>");
                        out.print("</select>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una prioridad.</div>");
                        out.print("</div>");

                        out.print("<div class='pb-2' style='display:flex;justify-content: space-evenly;'>");
                        out.print("<div class='col-lg-6'>"
                                + "<input type='number' class='form-control' name='txt_cantidad' id='cantidad-id' size='10' value='1' min='1' required placeholder='Cantidad' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Cantidad'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                        out.print("<div class='col-lg-6'>"
                                + "<input type='text' class='form-control' name='txt_tipo' id='Tipo-id' value='' placeholder='Tipo' onchange='javascript:this.value=this.value.toUpperCase();' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Tipo'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                        out.print("</div>");

                        if (descripcion.equals("")) {
                            out.print("<div class='col-12 pb-2'>");
                            out.print("<textarea class='form-control' name='txt_desc' id='descripcion-id'  value='' placeholder='Descripción' required onchange='javascript:this.value=this.value.toUpperCase();' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Descripción'></textarea>");
                            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                            out.print("</div>");
                        } else {
                            out.print("<div class='col-12 pb-2'>");
                            out.print("<div class='DivDescripcion'  data-toggle='tooltip' data-placemente='top' title='Descripción'><b>Descripción:</b><br/>");
                            out.print("" + descripcion.replace("<strong>", "<strong style='color:#6e1c17;'>") + "</div>");
                            out.print("</div>");
                            out.print("<input type='hidden' name='txt_desc' value='" + descripcion + "'>");
                            out.print("<input type='hidden' name='idPd' value='" + id_pendiente + "'>");
                        }
                        out.print("<input type='hidden' name='btn_bus' value='" + btn_filter + "'>");
                        out.print("<div class='' style='width: 100%; text-align:center;'>");
                        out.print("<button class='btn btn-red btn-lg'>Registrar</button>");
                        out.print("</div>");
                        out.print("</form>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                    }
                    //</editor-fold>
                } else if (id_ficha > 0) {
                    //<editor-fold defaultstate="collapsed" desc="FORMULARIO FICHA TECNICA">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_solicitud'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h3>Registrar Solicitud Ficha</h3>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3);' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    lst_fichas = jpa_solicitud.ConsultasSolicitudidfichas(id_ficha);
                    if (lst_fichas != null || lst_fichas.isEmpty()) {
                        Object[] Obj_ficha = (Object[]) lst_fichas.get(0);
                        out.print("<form action='Solicitud?opc=9' method='post' name='formSolicitud' id='formSolicitud' class='needs-validation' novalidate=''>");
                        out.print("<input type='hidden' name='id_usuari' value='" + id_usuario + "'>");
                        out.print("<input type='hidden' name='id_ficha' value='" + id_ficha + "'>");
                        out.print("<input type='hidden' name='id_pendiente' value='" + id_pendiente_ft + "'>");

                        out.print("<div class='col-12 pb-3'>");
                        out.print("<input type='text' class='form-control' name='txt_sol' id='solicitud-id' value='' placeholder='Numero Solicitud' readonly='true' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Numero Solicitud'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col-12 pb-3'>");
                        out.print("<input type='text' class='form-control' value='" + Obj_ficha[1] + "' placeholder='Ficha Tecnica' readonly='true' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Ficha Tecnica'>");
                        out.print("</div>");
                        if (lst_pendiente != null) {
                            Object[] Obj_pendiente = (Object[]) lst_pendiente.get(0);
                            out.print("<div class='col-12 pb-3'>");
                            out.print("<input type='text' class='form-control' name='descripcion' value='" + Obj_pendiente[5] + "' placeholder='Descripción' readonly='true' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Descripción'>");
                            out.print("</div>");
                        }
                        out.print("<div class='col-lg-12 pb-1'>");
                        out.print("<select class='select2 form-control' name='prioridad'  id='select-id' style='margin-top: 12px; margin-bottom: 12px;' required>");
                        out.print("<option selected disabled value=''>Seleccione la prioridad</option>");
                        out.print("<option title='Importante y urgente, esta prioridad se atenderá de manera inmediata, usualmente implica que hay parada de máquina colpitt o screen, se pueden suspender montajes en máquinas de taller para dar cumplimiento a esta solicitud.'>");
                        out.print("1A - INMEDIATA</option>");
                        out.print("<option title='Importante y urgente, esta prioridad se atenderá dentro de las siguientes 24 horas. Esto implica no suspender de inmediato trabajos que se estén ejecutando en las máquinas del taller. Se puede usar, por ejemplo, para riesgo de parada de máquinas o parada de otras máquinas.'>");
                        out.print("2A - 24 HORAS</option>");
                        out.print("<option title='Importante y no urgente, se atenderá en el transcurso de una semana. Por ejemplo, mantenimiento de electrodos, solicitud de repuestos en cantidades mínimas.'>");
                        out.print("1B - 1 SEMANA</option>");
                        out.print("<option title='Importante y no urgente, se atenderá en el transcurso de un mes. Por ejemplo, repuestos en general en cantidades razonables de los cuales se debe contar con stock.'>");
                        out.print("2B - 1 MES</option>");
                        out.print("<option title='No importante no urgente, esta prioridad no tiene límite de tiempo, si no se ha atendido en el transcurso de un año se reprogramará o eliminará según la necesidad, se puede asignar a trabajos para ensayos, arreglos estéticos, repuestos de los cuales se tiene stock o no son críticos, etc.'>");
                        out.print("1C - SIN LÍMITE DE TIEMPO</option>");
                        out.print("</select>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una prioridad.</div>");
                        out.print("</div>");
                        out.print("<div class='' style='width: 100%; text-align:center;'>");
                        out.print("<button class='btn btn-red btn-lg'>Registrar</button>");
                        out.print("</div>");
                        out.print("</form>");

                    }
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //</editor-fold>
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Modulo Solicitudes</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<h4>Listado de solicitudes</h4>");
                if (rol.equals("JEFE.PR") || rol.equals("COORD.PR") || rol.equals("ADMIN") || rol.equals("MTF") || rol.equals("PI") || rol.equals("MI") || rol.equals("AU")) {
                    out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick='mostrarConvencion(1);obtenerSolicitud()' data-toggle='tooltip' data-placement='top' title='Registrar Solicitud'><i class='fas fa-plus'></i></button>");
                }
                out.print("</div>");
                out.print("<form action='Solicitud?opc=10&estado=1' method='post' id='form_btns'>");
                out.print("<input type='radio' name='btn_bus' value='1' id='option-1' style='display:none;' onclick='execute_form()' " + ((btn_filter == 1) ? "checked " : "") + ">");
                out.print("<input type='radio' name='btn_bus' value='2' id='option-2' style='display:none;' onclick='execute_form()' " + ((btn_filter == 2) ? "checked " : "") + ">");
                out.print("<input type='radio' name='btn_bus' value='3' id='option-3' style='display:none;' onclick='execute_form()' " + ((btn_filter == 3) ? "checked " : "") + ">");
                out.print("</form>");
                out.print("<div style='display:flex;justify-content:space-evenly;'>");
                if (rol.equals("JEFE.PR") || rol.equals("COORD.PR") || rol.equals("ADMIN") || rol.equals("TEC.PR")) {
                    out.print("<div style='display:flex;justify-content:space-evenly;width:48%;align-items:center'>");
                    out.print("<div><label for='option-1'><div class='btn btn-" + ((btn_filter == 1) ? "danger" : "outline-danger") + "' data-toggle='tooltip' data-placement='top' title='Solicitudes'><i style='font-size:18px;' class='fas fa-people-carry fa-lg'></i></div></label></div>");
                    out.print("<div><label for='option-2'><div class='btn btn-" + ((btn_filter == 2) ? "warning" : "outline-warning") + "' data-toggle='tooltip' data-placement='top' title='Pendiente(s) Herramental'><i style='font-size:18px;' class='fas fa-vote-yea fa-lg'></i></div></label></div>");
                    out.print("<div><label for='option-3'><div class='btn btn-" + ((btn_filter == 3) ? "success" : "outline-success") + "' data-toggle='tooltip' data-placement='top' title='Pendiente(s) Ficha'><i style='font-size:18px;' class='fas fa-file-alt fa-lg'></i></div></label></div>");
                    out.print("</div>");
                }
                out.print("<div style='width:48%;align-items: center;display:flex;justify-content:space-around'>");
                out.print("<div class='selectgroup w-50' >");
                out.print("<label class=\"selectgroup-item\" onclick=\"javascript:location.href='Solicitud?opc=1&btn_bus=" + btn_filter + "&estado=0'\">");
                out.print("<input type=\"radio\" name=\"estado\" value=\"0\" class=\"selectgroup-input\" " + ((estado == 0) ? "checked" : "") + ">");
                out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Todas</span>");
                out.print("</label>");
                out.print("<label class=\"selectgroup-item\" onclick=\"javascript:location.href='Solicitud?opc=1&btn_bus=" + btn_filter + "&estado=1'\">");
                out.print("<input type=\"radio\" name=\"estado\" value=\"1\" class=\"selectgroup-input\" " + ((estado == 1) ? "checked" : "") + " >");
                out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Abiertas</span>");
                out.print("</label>");
                out.print("<label class=\"selectgroup-item\" onclick=\"javascript:location.href='Solicitud?opc=1&btn_bus=" + btn_filter + "&estado=2'\">");
                out.print("<input type=\"radio\" name=\"estado\" value=\"2\" class=\"selectgroup-input\" " + ((estado == 2) ? "checked" : "") + ">");
                out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Cerradas</span>");
                out.print("</label>");
                out.print("</div>");

                out.print("</div>");

                out.print("</div>");

                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                //<editor-fold defaultstate="collapsed" desc="TABLA SOLICITUDES">
                if (rol.equals("MTF") || rol.equals("PI") || rol.equals("AU") || rol.equals("MI")) {
                    if (estado == 1 || estado == 2) {
                        lst_solicitudes = jpa_solicitud.consultaSolicitudesRolEstado(rol, ((estado == 1) ? 0 : 100));
                    } else {
                        lst_solicitudes = jpa_solicitud.consultaSolicitudesRol(rol);
                    }
                } else {
                    switch (btn_filter) {
                        case 1:
                            if (estado == 1 || estado == 2) {
                                lst_solicitudes = jpa_solicitud.consultaSolicitudesEstado("S", ((estado == 1) ? 0 : 100));
                            } else {
                                lst_solicitudes = jpa_solicitud.consultaSolicitudes("S");
                            }
                            break;
                        case 2:
                            if (estado == 1 || estado == 2) {
                                lst_solicitudes = jpa_solicitud.consultaSolicitudesEstado("H", ((estado == 1) ? 0 : 100));
                            } else {
                                lst_solicitudes = jpa_solicitud.consultaSolicitudes("H");
                            }
                            break;
                        default:
                            if (estado == 1 || estado == 2) {
                                lst_solicitudes = jpa_solicitud.consultaSolicitudesFichaEstado("F", ((estado == 1) ? 0 : 100));
                            } else {
                                lst_solicitudes = jpa_solicitud.consultaSolicitudesFicha("F");
                            }
                            break;
                    }
                }
                out.print("<table class='table table-bordered table-hover CustomTable' id='table-1'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Solicitudes</th>");
                out.print("<th>Prioridad</th>");
                if (btn_filter == 1) {
                    out.print("<th style='width:51.7812px;'>Ficha</th>");
                    out.print("<th>Pieza</th>");
                    out.print("<th>Plano</th>");
                    out.print("<th>Cantidad</th>");
                    out.print("<th>Descripción</th>");
                }
                if (btn_filter == 2) {
                    out.print("<th>Plano</th>");
                    out.print("<th>Cantidad</th>");
                    out.print("<th>Cavidades</th>");
//                    out.print("<th>Cavidades des-habilitadas</th>");
                    out.print("<th>Descripción</th>");
                }
                if (btn_filter == 3) {
                    out.print("<th>Causas</th>");
                    out.print("<th>Sugerencias</th>");
                }
                out.print("<th style='text-align: center;'>Opc</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                for (int i = 0; i < lst_solicitudes.size(); i++) {
                    Object[] obj_solicitud = (Object[]) lst_solicitudes.get(i);
                    out.print("<tr>");
                    //<editor-fold defaultstate="collapsed" desc="# SOLICITUDES - RESPONSABLE">
                    out.print("<td style='text-align:center;'><b style='color:#f70f03;'>#" + obj_solicitud[3] + "</b><br/><b>" + obj_solicitud[13] + " " + obj_solicitud[14] + "</b></td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="PRIORIDAD">
                    if (obj_solicitud[4].equals("-")) {
                        out.print("<td>" + obj_solicitud[4].toString().split("-")[1] + "</td>");
                    } else {
                        out.print("<td>" + obj_solicitud[4] + "</td>");
                    }
                    //</editor-fold>
                    if (btn_filter == 1 || btn_filter == 2) {
                        if (btn_filter == 1) {
                            //<editor-fold defaultstate="collapsed" desc="FICHA">
                            if (rol.equals("COORD.PR") || rol.equals("ADMIN")) {
                                if (obj_solicitud[5].equals(0)) {
                                    out.print("<td style='text-align:center;'>N/A</td>");
                                } else if ((Integer) obj_solicitud[10] != 100) {
                                    out.print("<form action='Solicitud?opc=5&idS=" + obj_solicitud[0] + "' method='post' >");
//                                    out.print("<td style='text-align:center;'><input type='text' class='form-control btn-sm' name='txt_ficha' id='ficha-id' size='10' value='" + obj_solicitud[5] + "' placeholder='Ficha' onchange='javascript:this.value=this.value.toUpperCase();' style='font-size:13px;padding:0px;height:29px;text-align:center;'></td>");
                                    out.print("<td style='text-align:center;'>"
                                            + "<span style='display:none;'>" + obj_solicitud[5] + "</span>"
                                            + "<input type='text' class='form-control btn-sm' "
                                            + "name='txt_ficha' id='ficha-id' size='10' "
                                            + "value='" + obj_solicitud[5] + "' placeholder='Ficha' "
                                            + "onchange='javascript:this.value=this.value.toUpperCase();' "
                                            + "style='font-size:13px;padding:0px;height:29px;text-align:center;'>"
                                            + "</td>");
                                    out.print("</form>");
                                } else {
                                    out.print("<td style='text-align:center;'>" + obj_solicitud[5] + "</td>");
                                }
                            } else if (obj_solicitud[5].equals(0)) {
                                out.print("<td style='text-align:center;'>N/A</td>");
                            } else {
                                out.print("<td style='text-align:center;'>" + obj_solicitud[5] + "</td>");
                            }
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="PIEZA">
                            if ((Integer) obj_solicitud[0] < 4582) {
                                out.print("<td>" + obj_solicitud[7] + "</td>");
                            } else if (rol.equals("MTF") || rol.equals("ADMIN")) {
                                if (obj_solicitud[19].equals("Electrodo")) {
                                    String[] arg_piezas = obj_solicitud[7].toString().split("-");
                                    out.print("<td>");
                                    if ((Integer) obj_solicitud[10] == 100) {
                                        lst_cabecera = jpa_cabecera.consultarUltimaCabecera((String) obj_solicitud[3]);
                                        if (lst_cabecera == null) {
                                            lst_itmVerPln = jpa_plano.consultaItemsVerificadosIdPlano((Integer) obj_solicitud[18]);
                                            for (int h = 0; h < lst_itmVerPln.size(); h++) {
                                                Object[] objectVeri = (Object[]) lst_itmVerPln.get(h);
                                                if (Integer.parseInt(objectVeri[0].toString()) == Integer.parseInt(objectVeri[1].toString())) {
                                                    out.print("<i class=\"fas fa-comment-alt fa-lg mr-1\" style=\"color: #d3a709;\"></i>");
                                                } else {
                                                    out.print("<i class=\"fas fa-comment-alt fa-lg\" style=\"color: #e32643;\"></i> ");
                                                }
                                            }
                                        } else {
                                            out.print("<i class=\"fas fa-comment-alt fa-lg\" style=\"color: #0eb941;\"></i>  ");
                                        }
                                        for (int j = 0; j < arg_piezas.length; j++) {
                                            out.print("<a href='Verificacion?opc=2&idP=" + obj_solicitud[18] + "&numS=" + obj_solicitud[3] + "&pieza=" + arg_piezas[j] + "'>" + arg_piezas[j] + "</a>" + ((j == arg_piezas.length - 1) ? "" : "-") + "");
                                        }
                                    } else {
                                        out.print(obj_solicitud[7]);
                                    }
                                    out.print("</td>");
                                } else {
                                    out.print("<td>" + obj_solicitud[7] + "</td>");
                                }
                            } else {
                                out.print("<td>" + obj_solicitud[7] + "</td>");
                            }
                            //</editor-fold>
                        }
                        //<editor-fold defaultstate="collapsed" desc="PLANO">
                        out.print("<td>" + obj_solicitud[6] + "</td>");
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="CANTIDAD">
                        out.print("<td>" + obj_solicitud[8] + "</td>");
                        //</editor-fold>
                        if (btn_filter == 1) {
                            //<editor-fold defaultstate="collapsed" desc="DESCRIPCION">
                            out.print("<td>" + obj_solicitud[9] + "</td>");
                            //</editor-fold>
                        } else if (btn_filter == 2) {
                            //<editor-fold defaultstate="collapsed" desc="DESCRIPCION HERRAMENTAL">
                            if (obj_solicitud[25].toString() != null) {
                                String[] Arr_cavidad = obj_solicitud[25].toString()
                                        .replace("<strong>Cavidades: </strong>", "")
                                        .replace("<strong>Cavidades des-habilitadas: </strong>", "")
                                        .replace("<strong>Cavidades des-habilitadas: </strong>", "")
                                        .replace("<strong>Cavidades des-habilitadas:&nbsp;</strong>", "")
                                        .replace("<strong>Cavidades des-habilitatdas.&nbsp;</strong>", "")
                                        .replace("<strong>Cavidades des-habilitadas</strong>", "")
                                        .replace("<strong>Causas: </strong>", "")
                                        .replace("<br>", "")
                                        .replace("<p>", "<p style='margin:0;'>")
                                        .replace("<p style=\"margin:0;\"></p>", "")
                                        .trim().split("<hr />");
                                out.print("<td>" + Arr_cavidad[0] + "</td>");
                                out.print("<td>" + Arr_cavidad[1] + "</td>");
                            }
                            //</editor-fold>
                        }
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="DESCRIPCIÓN">
                        if (obj_solicitud[9].toString().contains("hr")) {
                            String[] Arr_descripcion = obj_solicitud[9].toString().trim().split("<hr />");
                            out.print("<td>" + Arr_descripcion[0].replace("Causas:", "") + "</td>");
                            out.print("<td>" + Arr_descripcion[1].replace("Sugerencias:", "") + "</td>");
                        } else {
                            out.print("<td>" + obj_solicitud[9] + "</td>");
                            out.print("<td>N/A</td>");
                        }
                        //</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="OPCIONES">
                    out.print("<td>");
                    out.print("<div style='display:flex;margin-left:12%;' class='mr-2'>");
                    if (btn_filter == 3) {
                    } else if (obj_solicitud[15].equals(rol) || rol.equals("COORD.PR")) {
                        if ((Integer) obj_solicitud[10] != 100) {
                            out.print("<div style='margin-right:10px;'><a href='Solicitud?opc=1&idS=" + obj_solicitud[0] + "&btn_bus=" + btn_filter + "' style='color:white;' class='btn btn-warning btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Modificar'><i class='fas fa-pencil-alt'></i></a></div>");
                        } else {
                            out.print("<div style='margin-right:10px;' data-toggle='tooltip' data-placement='top' title='Solicitud finalizada'><a style='color:white;' class='btn btn-warning disabled btn-icon btn-sm'><i class='fas fa-pencil-alt'></i></a></div>");
                        }
                    } else {
                        out.print("<div style='margin-right:10px;' data-toggle='tooltip' data-placement='top' title='Sin permisos | Solicitud finalizada'><a style='color:white;' class='btn btn-warning disabled btn-icon btn-sm'><i class='fas fa-pencil-alt'></i></a></div>");
                    }
                    if (btn_filter == 3) {
                        out.print("<div style='margin-right:10px;'><a  href='Seguimiento?opc=1&idS=" + obj_solicitud[0] + "&var=1' class='btn btn-dark btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Seguimientos'><i class='fas fa-eye'></i></a></div>");
                    } else if (area == 1) {
                        out.print("<div style='margin-right:10px;'><a  href='Seguimiento?opc=1&idS=" + obj_solicitud[0] + "&var=0' class='btn btn-dark btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Seguimientos'><i class='fas fa-eye'></i></a></div>");
                    } else if (rol.equals("TEC.PR")) {
                        out.print("<div style='margin-right:10px;'><a  href='Solicitud?opc=6&idS=" + obj_solicitud[0] + "&var=0' class='btn btn-dark btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Seguimientos'><i class='fas fa-eye'></i></a></div>");
                    } else {
                        out.print("<div style='margin-right:10px;'><a  href='Seguimiento?opc=1&idS=" + obj_solicitud[0] + "&var=0' class='btn btn-dark btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Seguimientos'><i class='fas fa-eye'></i></a></div>");
                    }
                    if ((Integer) obj_solicitud[10] == 100) {
                        out.print("<div style='margin-right:10px;'><a style='color:white;' class='btn btn-info btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Estado Finalizado'><i class='fas fa-lock '></i></a></div></div>");
                    } else {
                        out.print("<div style='margin-right:10px;'><a style='color:white;' class='btn btn-info btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Estado Pendiente'><i class='fas fa-lock-open'></i></a></div></div>");
                    }
                    out.print("</tr>");
                    out.print("</tr>");
                    //</editor-fold>
                }
                out.print("</tbody>");
                out.print("</table>");
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="MÓDULO INDICADOR Y REGISTRO DE SOLICITUDES">
                try {
                    temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
                } catch (NumberFormatException e) {
                    temp = 0;
                }
                try {
                    fechaI = pageContext.getRequest().getAttribute("fecha_inicio").toString();
                } catch (Exception e) {
                    fechaI = "";
                }
                try {
                    fechaF = pageContext.getRequest().getAttribute("fecha_fin").toString();
                } catch (Exception e) {
                    fechaF = "";
                }
                try {
                    estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                } catch (NumberFormatException e) {
                    estado = 0;
                }
                try {
                    tempC = Integer.parseInt(pageContext.getRequest().getAttribute("tempC").toString());
                } catch (NumberFormatException e) {
                    tempC = 0;
                }
                try {
                    busqueda = pageContext.getRequest().getAttribute("busqueda").toString();
                } catch (Exception e) {
                    busqueda = "";
                }
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<h1>Indicador</h1>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<h4>Inidicador solicitudes</h4>");
                out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick=\"javascript:location.href='Solicitud?opc=4&temp=0'\" data-toggle='tooltip' data-placement='top' title='Filtrar'><i class='fas fa-search'></i></button>");
                out.print("</div>");
                out.print("<div class=\"selectgroup w-50 card-header\">"
                        + "<label class=\"selectgroup-item\" onclick=\"javascript:location.href='Solicitud?opc=4&txt_fechaI=" + fechaI + "&txt_fechaF=" + fechaF + "&slc_estado=" + estado + "&temp=1&tempC=0'\">"
                        + "<input type=\"radio\" name=\"state\" value=\"1\" class=\"selectgroup-input\" " + ((tempC == 0) ? "checked" : "") + " >"
                        + "<span class=\"selectgroup-button selectgroup-button-icon\">Contadores</span>"
                        + "</label>"
                        + "<label class=\"selectgroup-item\" onclick=\"javascript:location.href='Solicitud?opc=4&txt_fechaI=" + fechaI + "&txt_fechaF=" + fechaF + "&slc_estado=" + estado + "&temp=1&tempC=1&txt_bus=" + busqueda + "'\">"
                        + "<input type=\"radio\" name=\"state\" value=\"1\" class=\"selectgroup-input\" " + ((tempC == 1) ? "checked" : "") + " >"
                        + "<span class=\"selectgroup-button selectgroup-button-icon\">R-PM-009</span>"
                        + "</label>"
                        + "</div>");
                if (temp == 0) {
                    //<editor-fold defaultstate="collapsed" desc="GENERACIÓN FILTRO">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='contFiltroFechas'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h3>Filtro Indicador</h3>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1);' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");

                    out.print("<form action='Solicitud?opc=4' method='post' name='' id='' class='needs-validation' novalidate='' >");
                    out.print("<input type='hidden' name='temp' value='1'>");
                    out.print("<div class='col-12 pb-3'>");
                    out.print("<input type='date' class='form-control' name='txt_fechaI' id='txt_fechaI' value='' required placeholder='Fecha Inicio' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Fecha Inicio'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-12 pb-3'>");
                    out.print("<input type='date' class='form-control' name='txt_fechaF' id='txt_fechaF' value='' required placeholder='Fecha Fin' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Fecha Fin'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-12 pb-3'>");
                    out.print("<select class='form-control' name='slc_estado' id='estadoF-id'>");
                    out.print("<option value='3'>Todas</option>");
                    out.print("<option value='1'>Abiertas</option>");
                    out.print("<option value='2'>Cerradas</option>");
                    out.print("</select>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-12 pb-3'>");
                    out.print("<textarea class='form-control' name='txt_bus' placeholder='Busqueda' data-toggle='tooltip' data-placemente='top' title='Busqueda'></textarea>");
                    out.print("</div>");

                    out.print("<div class='' style='width: 100%; text-align:center;'>");
                    out.print("<button class='btn btn-red btn-lg'>Consultar</button>");
                    out.print("</div>");

                    out.print("</form>");

                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    if (tempC == 0) {
                        lst_solicitud = jpa_solicitud.consultaContadorSolicitudes(fechaI, fechaF);
                        //<editor-fold defaultstate="collapsed" desc="CONTADORES">
                        out.print("<table class='table-hover' style='width:95%;margin:2%'>");
                        out.print("<tr>");
                        out.print("<th class='th1Verificacion'>Área</th>");
                        out.print("<th class='th1Verificacion'>Solicitud Pendientes</th>");
                        out.print("<th class='th1Verificacion'>Solicitud Realizadas</th>");
                        out.print("<th class='th1Verificacion'>Total Solicitudes</th>");
                        out.print("</tr>");
                        if (lst_solicitud != null) {
                            for (int i = 0; i < lst_solicitud.size(); i++) {
                                Object[] obj_solicitud = (Object[]) lst_solicitud.get(i);
                                out.print("<tr>");
                                out.print("<td align='center'><b>" + obj_solicitud[0] + "</b></td>");
                                out.print("<td align='center'><b style='color: black;'>" + obj_solicitud[1] + "</b></td>");
                                out.print("<td align='center' '><b style='color: black;'>" + obj_solicitud[2] + "</b><br /></td>");
                                out.print("<td align='center' '><b style='color: black;'>" + obj_solicitud[3] + "</b><br /></td>");
                                out.print("</tr>");
                            }
                        } else {
                            out.print("<tr><td colspan='4' style='text-align:center;'>No existen datos registrados</td></tr>");
                        }
                        out.print("</table>");
                        //</editor-fold>    
                    } else {
                        lst_solicitudes_filtrada = jpa_solicitud.consultaSolicitudesRango(fechaI, fechaF, estado, busqueda);
                        //<editor-fold defaultstate="collapsed" desc="R-PM-009">
                        if (rol.equals("ADMIN") || rol.equals("COORD.PR") || rol.equals("JEFE.PR")) {
                            out.print("<div class='card-header' style='justify-content: space-evenly;'>");
                            out.print("<button class='btn btn-info imprimir' onclick='Imprimir();' data-toggle='tooltip' data-placement='top' title='Imprimir / PDF'><i class='fas fa-print'></i></button>");
                            out.print("</div>");
                            out.print("<div id='Imprimir'>");
                            //<editor-fold defaultstate="collapsed" desc="CABECERA REGISTRO R-MTF-009">
                            String Fecha = "" + fechaFormateada + "";
                            String datoFecha = Fecha.replace("-", "");
                            int FechaInt = Integer.parseInt(datoFecha);
                            out.print("<table style='width:100%'>");
                            out.print("<thead>");
                            if (FechaInt >= 20160115) {
                                out.print("<tr>");
                                out.print("<tr><td colspan='12' style='background-color:#979595;height:22px !important;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                                out.print("</tr>");
                            }
                            out.print("<tr>");
                            out.print("<td style='width:30%' align='center' rowspan='2'><img src='Interfaz/Contenido/Imagen/Logo.png' style='width: 211px; height: 72px' alt=''></td>");
                            out.print("<td style='width:40%;'><h6 style='text-align: center;'>REGISTRO</h6></td>");
                            out.print("<td style='width:15%'  align='center'><b>CODIGO</b><b style='color:black'> R-PM-009</b></td>");
                            out.print("<td style='width:15%'  align='center'><b>VERSIÓN</b><b style='color:black'> 01</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='width:40%;'><h6 style='text-align: center;'>REGISTRO DE REQUISICION INTERNA MANTENIMIENTO FARMACEUTICO</h6></td>");
                            out.print("<td style='width:15%' colspan='2' align='center'><b>FECHA</b><b style='color:black'> " + fechaF + "</b></td>");
                            out.print("</tr>");
                            //</editor-fold>
                            out.print("</thead>");
                            out.print("</table>");

                            out.print("<table class='table-hover' style='width:100%;'>");
                            out.print("<thead>");
                            out.print("<tr>");
                            out.print("<th class='thRPM009'>No. Solicitud</th>");
                            out.print("<th class='thRPM009'>Responsable</th>");
                            out.print("<th class='thRPM009'>Prioridad</th>");
                            out.print("<th class='thRPM009'>Ficha</th>");
                            out.print("<th class='thRPM009'>Plano</th>");
                            out.print("<th class='thRPM009'>Pieza</th>");
                            out.print("<th class='thRPM009'>Cantidad</th>");
                            out.print("<th class='thRPM009'>Descripción</th>");
                            out.print("<th class='thRPM009'>Estado</th>");
                            out.print("</tr>");
                            out.print("</thead>");
                            out.print("<tbody>");
                            if (lst_solicitudes_filtrada != null) {
                                //<editor-fold defaultstate="collapsed" desc="CICLO">
                                for (int i = 0; i < lst_solicitudes_filtrada.size(); i++) {
                                    Object[] obj_solicitudes = (Object[]) lst_solicitudes_filtrada.get(i);
                                    out.print("<tr>");
                                    out.print("<td class='PdgTd'>" + obj_solicitudes[3] + "</td>");
                                    out.print("<td class='PdgTd'>" + obj_solicitudes[13] + " " + obj_solicitudes[14] + "</td>");
                                    out.print("<td class='PdgTd'>" + obj_solicitudes[4] + "</td>");
                                    if (rol.equals("COORD.PR") || rol.equals("ADMIN")) {
                                        if (obj_solicitudes[5].equals(0)) {
                                            out.print("<td class='PdgTd'>N/A</td>");
                                        } else {
                                            out.print("<td class='PdgTd'>" + obj_solicitudes[5] + "</td>");
                                        }
                                    } else if (obj_solicitudes[5].equals(0)) {
                                        out.print("<td class='PdgTd'>N/A</td>");
                                    } else {
                                        out.print("<td class='PdgTd'>" + obj_solicitudes[5] + "</td>");
                                    }
                                    out.print("<td class='PdgTd'>" + obj_solicitudes[6] + "</td>");
                                    out.print("<td class='PdgTd'>" + obj_solicitudes[7] + "</td>");
                                    out.print("<td class='PdgTd'>" + obj_solicitudes[8] + "</td>");
                                    out.print("<td class='PdgTd'>" + obj_solicitudes[9] + "</td>");
                                    if ((Integer) obj_solicitudes[10] == 100) {
                                        out.print("<td style='text-align:center;' class='PdgTd'><div><a style='color:white;' class='btn btn-info btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Estado Finalizado'><img src='Interfaz/Contenido/Imagen/lock.png' style='width: 19px; height: 19px;color:white' alt=''></a></div></td>");
                                    } else {
                                        out.print("<td style='text-align:center;' class='PdgTd'><div><a style='color:white;' class='btn btn-info btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Estado Pendiente'><img src='Interfaz/Contenido/Imagen/lock_open.png' style='width: 19px; height: 19px;color:white' alt=''></a></div></td>");
                                    }
                                    out.print("</tr>");
                                }
                                //</editor-fold>
                            } else {
                                out.print("<tr><td colspan='9' style='text-align:center;'>No existen datos registrados</td></tr>");
                            }
                            out.print("<tr><td colspan='9'><span style='    font-size: 12px;\n"
                                    + "    font-style: italic;\n"
                                    + "    margin-left: 8px;'>La informacion personal en este documento sera tratada y protegida de acuerdo con nuestras politicas de proteccion de datos personales. </span></td></tr>");
                            out.print("</tbody>");
                            out.print("</table>");
                        }
                        //</editor-fold>
                    }
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_solicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
