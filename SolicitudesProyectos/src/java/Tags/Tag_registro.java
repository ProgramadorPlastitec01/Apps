package Tags;

import Controladores.DefectoJpaController;
import Controladores.MaquinaJpaController;
import Controladores.PlanoJpaController;
import Controladores.RegistroJpaController;
import Controladores.SolicitudJpaController;
import Controladores.UsuarioJpaController;
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

public class Tag_registro extends TagSupport {

    /**
     * Fecha de corte del formato R-PM-001 version 5, en formato yyyyMMdd (la
     * misma convencion de `fechaint`, derivado de solicitud.Fecha_Ingreso).
     *
     * Control de versiones documental del SGC: cada registro se imprime con el
     * formato VIGENTE EL DIA EN QUE SE CREO, no con el formato de hoy. Un
     * registro de 2024 es version 4 y no llevaba No. de parte; mostrarle esa
     * columna vacia falsearia el formato original.
     *
     *   fechaint <= 20231020            -> version 3 (10 columnas)
     *   20231020 < fechaint < corte     -> version 4 (11 columnas)
     *   fechaint >= corte               -> version 5 (12 columnas, con No. de parte)
     *
     * La vista de filtro dinamico usa la misma constante pero su tabla tiene una
     * columna mas, porque agrupa Descripcion con colspan 2: 12 y 13.
     */
    private static final int FECHA_LANZAMIENTO_V5 = 20260908;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol = sesion.getAttribute("Rol").toString();
        SolicitudJpaController jpa_solicitud = new SolicitudJpaController();
        RegistroJpaController jpa_registro = new RegistroJpaController();
        MaquinaJpaController jpa_maquina = new MaquinaJpaController();
        DefectoJpaController jpa_defecto = new DefectoJpaController();
        UsuarioJpaController jpa_usuarios = new UsuarioJpaController();
        PlanoJpaController jpa_plano = new PlanoJpaController();
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = formato.format(fechaActual);
        List lst_registro = null;
        List lst_descripcion = null;
        List lst_tecnicos = null;
        List lst_maquina = jpa_maquina.consultaMaquinas();
        List lst_planos = jpa_plano.consultaPlanos();
        List lst_tipoP = jpa_plano.consultaTipoPlano();
        String query = "";
        int id_solicitud = 0;
        try {
            try {
                id_solicitud = Integer.parseInt(pageContext.getRequest().getAttribute("id_solicitud").toString());
            } catch (Exception e) {
                id_solicitud = 0;
            }
            if (id_solicitud != 0) {
                //<editor-fold defaultstate="collapsed" desc="MÓDULO GESTIÓN REGISTRO">
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<div style='display:flex;align-items:center'>"
                        + "<div class='mr-2'><a class=\"btn btn-white btn-icon btn-sm\" data-toggle=\"tooltip\" href='Solicitud?opc=1&estado=1' data-placement=\"top\" title=\"\" data-original-title=\"Volver\"><i class=\"fas fa-arrow-left\"></i></a></div>"
                        + "<div><h1>Módulo Registro</h1></div></div>");
                out.print("</div>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                List lst_solicitud = jpa_solicitud.consultaSolicitudId(id_solicitud);
                if (lst_solicitud != null) {
                    Object[] obj_solicitud = (Object[]) lst_solicitud.get(0);
                    lst_registro = jpa_registro.consultarRegistrosSolicitudId(id_solicitud);
                    if ((Integer) obj_solicitud[10] != 100) {
                        out.print("<div style='margin-left:95%'>");
                        out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick='mostrarConvencion(1);' data-toggle='tooltip' data-placement='top' title='Registrar Control'><i class='fas fa-plus'></i></button>");
                        out.print("</div>");
                        //<editor-fold defaultstate="collapsed" desc="FORM REGISTRO">
                        out.print("<div class='sweet-local' tabindex='-7' id='Ventana1' style='opacity: 1.03; display:none;'>");
                        out.print("<div class='cont_solicitud'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h3>Registrar Control</h3>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1);' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<form action='Solicitud?opc=7' method='post' name='formSolicitud' id='formSolicitud' class='needs-validation' novalidate='' >");
                        out.print("<div class='FormControl'>");
                        out.print("<div class='col-lg-6'>"
                                + "<input type='text' class='form-control cursorDenegado' name='' id='fecha-id' readonly='false' value='" + fechaFormateada + "' required placeholder='Cantidad' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Fecha'>");
                        out.print("<div class='valid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");

                        out.print("<div class='col-lg-6'>"
                                + "<input type='text' class='form-control cursorDenegado' name='' id='solicitud' readonly='false' value='" + obj_solicitud[3] + "' required placeholder='Cantidad' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Solicitud'>");
                        out.print("<div class='valid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                        out.print("</div>");

                        out.print("<div class='FormControl mt-3'>");
                        out.print("<div class='col-lg-6'>"
                                + "<select class='select2 form-control' required name='slc_maquina' id='select-id' required data-toggle='tooltip' data-placemente='top' title='Maquina'>"
                                + "<option selected disabled value=''  style='display:none;'>Seleccione maquina</opction>");
                        for (int i = 0; lst_maquina != null && i < lst_maquina.size(); i++) {
                            Object[] obj_maquina = (Object[]) lst_maquina.get(i);
                            out.print("<option value='" + obj_maquina[0] + "'>" + obj_maquina[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-6'>"
                                + "<input type='text' class='form-control cursorDenegado' name='' id='plano-id' readonly='false' value='" + obj_solicitud[6] + "' required placeholder='Plano' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Plano'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                        out.print("</div>");

                        // No. de parte que se CONGELA en el registro (inmutabilidad
                        // historica del R-PM-001). Si el plano trae varias partes
                        // separadas por '-', el tecnico elige la que intervino; una
                        // sola queda preseleccionada. El valor viaja en el POST.
                        String numParteSolicitud = numeroParte(obj_solicitud, 26);
                        out.print("<div class='FormControl mt-3'>");
                        out.print("<div class='col-lg-12'>");
                        if (numParteSolicitud.isEmpty()) {
                            // Sin No. de parte cargado: se envia vacio para no bloquear
                            // el registro de planos que aun no tienen numero.
                            out.print("<input type='hidden' name='txt_numero_parte' value=''>");
                            out.print("<input type='text' class='form-control cursorDenegado' readonly='readonly' value='' placeholder='No. de parte' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='No. de parte del plano'>");
                            out.print("<small style='color:#ADB5BD;'>Este plano todavia no tiene No. de parte cargado en el modulo Planos.</small>");
                        } else {
                            String[] partesNp = numParteSolicitud.split("-");
                            out.print("<select class='form-control' name='txt_numero_parte' id='numero-parte-id' required data-toggle='tooltip' data-placemente='top' title='No. de parte que se intervino'>");
                            for (int ip = 0; ip < partesNp.length; ip++) {
                                String pnp = partesNp[ip].trim();
                                if (!pnp.isEmpty()) {
                                    out.print("<option value='" + pnp + "'>" + pnp + "</option>");
                                }
                            }
                            out.print("</select>");
                            if (partesNp.length > 1) {
                                out.print("<small style='color:#ADB5BD;'>Este plano tiene varias partes: elija la que intervino.</small>");
                            }
                        }
                        out.print("</div>");
                        out.print("</div>");

                        String piezas = obj_solicitud[7].toString();
                        StringTokenizer token = new StringTokenizer(piezas, "-");
                        int npiezas = token.countTokens();
                        String[] datos = new String[npiezas];
                        int ida = 0;

                        out.print("<div class='FormControl mt-1 mt-2'>");
                        out.print("<div class='col-lg-6'>"
                                + "<select class='select2 form-control' required name='slc_pieza'  id='select-id' required>"
                                + "<option selected disabled value=''' style='display:none;'>Seleccione Pieza</option>");
                        while (token.hasMoreTokens()) {
                            String str = token.nextToken();
                            datos[ida] = str;
                            out.print("<option>" + datos[ida] + "</opction>");
                            ida++;
                        }
                        out.print("</select>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<div class='col-lg-6'>"
                                + "<input type='number' class='form-control' name='txt_cant' id='cantidad-id' min='1' value='1' required placeholder='Cantidad' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Cantidad'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                        out.print("</div>");
                        lst_descripcion = jpa_defecto.descripcion();
                        out.print("<div class='mt-3'>");
                        out.print("<div class='col-lg-12'>"
                                + "<input type='text' class='form-control' name='txt_descripcion' id='descripcion-id' list='desc' required placeholder='Descripcion' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Descripción'>");
                        out.print("<datalist id='desc'>");
                        for (int i = 0; lst_descripcion != null && i < lst_descripcion.size(); i++) {
                            Object[] obj_desc = (Object[]) lst_descripcion.get(i);
                            out.print("<option value='" + obj_desc[2] + "'></option>");
                        }
                        out.print("</datalist>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                        out.print("</div>");

                        out.print("<div class='FormControl mt-3'>");
                        out.print("<div class='col-lg-6'>"
                                + "<input type='datetime-local' class='form-control' name='txt_horaI' id='horaI-id' required placeholder='Hora inicial' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Fecha y hora inicial'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                        out.print("<div class='col-lg-6'>"
                                + "<input type='datetime-local' class='form-control' name='txt_horaF' id='horaF-id' required placeholder='Hora Final' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Fecha y hora final'>");
                        out.print("<div class='invalid-feedback invalid_data_rll' id='feedback-horaF'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Ingrese una hora valida (max. 9 horas de labor)</div></div>");
                        out.print("</div>");

                        out.print("<input type='hidden' name='idS' value ='" + obj_solicitud[0] + "' />");
                        out.print("<input type='hidden' name='idP' value ='" + obj_solicitud[18] + "'/>");
                        out.print("<div class='mt-3' style='width: 100%; text-align:center;'>");
                        out.print("<button class='btn btn-red btn-lg'>Registrar</button>");
                        out.print("</div>");
                        out.print("</form>");

                        //<editor-fold defaultstate="collapsed" desc="CANDADO DE TURNO (MAX 8 H)">
                        // Doble candado en la UI, espejo del SIGNAL del SP: al fijar la
                        // hora inicial se acota el calendario de la final a [inicio,
                        // inicio+9h]; y en el submit se corta el envio si la duracion no
                        // es valida (<=0) o supera 540 min. El SP es la ultima linea.
                        out.print("<script>");
                        out.print("(function() {");
                        out.print("  var hi = document.getElementById('horaI-id');");
                        out.print("  var hf = document.getElementById('horaF-id');");
                        out.print("  var form = document.getElementById('formSolicitud');");
                        out.print("  if (!hi || !hf || !form) { return; }");
                        out.print("  function dos(n) { return (n < 10 ? '0' : '') + n; }");
                        out.print("  function masNueveHoras(val) {");
                        out.print("    var d = new Date(val);");
                        out.print("    if (isNaN(d.getTime())) { return ''; }");
                        out.print("    d.setHours(d.getHours() + 9);");
                        out.print("    return d.getFullYear() + '-' + dos(d.getMonth() + 1) + '-' + dos(d.getDate()) + 'T' + dos(d.getHours()) + ':' + dos(d.getMinutes());");
                        out.print("  }");
                        out.print("  hi.addEventListener('change', function() {");
                        out.print("    if (hi.value) {");
                        out.print("      hf.min = hi.value;");
                        out.print("      hf.max = masNueveHoras(hi.value);");
                        out.print("    }");
                        out.print("  });");
                        out.print("  form.addEventListener('submit', function(e) {");
                        out.print("    var ini = new Date(hi.value);");
                        out.print("    var fin = new Date(hf.value);");
                        out.print("    if (isNaN(ini.getTime()) || isNaN(fin.getTime())) { return; }");
                        out.print("    var min = (fin.getTime() - ini.getTime()) / 60000;");
                        out.print("    if (min <= 0) {");
                        out.print("      e.preventDefault();");
                        out.print("      e.stopPropagation();");
                        out.print("      swal({title: 'Horas no validas', text: 'La fecha y hora final debe ser posterior a la hora inicial.', type: 'warning', confirmButtonText: 'Entendido'});");
                        out.print("      hf.focus();");
                        out.print("      return false;");
                        out.print("    } else if (min > 540) {");
                        out.print("      e.preventDefault();");
                        out.print("      e.stopPropagation();");
                        out.print("      var fb = document.getElementById('feedback-horaF');");
                        out.print("      if (fb) { fb.innerHTML = \"<i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Excede la jornada de 9 horas (\" + Math.floor(min / 60) + \"h \" + (min % 60) + \"m)\"; }");
                        out.print("      swal({title: 'Turno Laboral Excedido', text: 'El tiempo seleccionado (' + Math.floor(min / 60) + 'h ' + (min % 60) + 'm) supera el maximo de 9 horas por turno. Por favor registre controles separados por cada jornada.', type: 'error', confirmButtonText: 'Corregir horas'});");
                        out.print("      hf.focus();");
                        out.print("      return false;");
                        out.print("    }");
                        out.print("  });");
                        out.print("})();");
                        out.print("</script>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>    
                    }
                    if (rol.equals("COORD.PR") || rol.equals("ASIST.PR") || rol.equals("ADMIN")) {
                        out.print("<div class='divSpaceEvenly mb-4'>");
                        out.print("<div><a href='Seguimiento?opc=1&idS=" + id_solicitud + "&var=0' class='btn btn-outline-secondary' data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Módulo Seguimiento\"><i class=\"fas fa-user-friends\"></i></a></div>");
                        out.print("<div><a href='Solicitud?opc=6&idS=" + id_solicitud + "&var=0' class='btn btn-rojo' data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Módulo R-PM-001\"><i class=\"fas fa-wrench\"></i></a></div>");
                        out.print("</div>");
                    }
                    //<editor-fold defaultstate="collapsed" desc="INFO SOLICITUD">
                    out.print("<div class='card-body'>");
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
                            + "<div class='StyleDiv4'>" + obj_solicitud[6] + "</div>");

                    out.print("</div>");

                    out.print("<div>");
                    out.print("<div  class='DivFlex'>");
                    out.print("<div class='StyleDiv2'>Cantidad:</div>"
                            + "<div class='StyleDiv2'>Estado:</div>"
                            + "<div class='StyleDiv2'>Pieza:</div>"
                            + "<div class='StyleDiv2'>Descripción:</div>");
                    out.print("</div>");

                    out.print("<div class='DivFlex2' style='margin-bottom:6px;'>");
                    out.print("<div class='StyleDiv4'>" + obj_solicitud[8] + "</div>"
                            + "<div class='StyleDiv4'>" + (Integer.parseInt(obj_solicitud[10].toString()) == 100 ? "<a style=\"color:white;\" class=\"btn btn-info btn-icon btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Estado Cerrado\"><i class=\"fas fa-lock\"></i></a>" : "<a style=\"color:white;\" class=\"btn btn-info btn-icon btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Estado Pendiente\"><i class=\"fas fa-lock-open\"></i></a>") + "</div>"
                            + "<div class='StyleDiv4'>" + obj_solicitud[7] + "</div>"
                            + "<div class='StyleDiv4'>" + obj_solicitud[9] + "</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    String datoFecha = obj_solicitud[1].toString().replace("-", "").split(" ")[0];
                    int fechaint = Integer.parseInt(datoFecha);
                    out.print("<div style='margin-top:12px;'>");
                    out.print("<table  style='width:100%'>");
                    out.print("<tbody>");
                    out.print("<tr>");
                    // Total de columnas de ESTA tabla. El diseno viejo agrupa
                    // Descripcion y Tiempo con colspan, por eso son menos.
                    // Se calcula una vez y se usa en todas las franjas
                    // institucionales para que la tabla no se descuadre.
                    // Version del formato vigente el dia en que se creo la
                    // solicitud. La columna No. de parte solo existe desde la v5.
                    boolean v5 = fechaint >= FECHA_LANZAMIENTO_V5;
                    String versionFormato = (fechaint <= 20231020) ? "3" : (v5 ? "5" : "4");
                    int columnas = (fechaint <= 20231020) ? 10 : (v5 ? 12 : 11);
                    out.print("<tr><td colspan='" + columnas + "' style='background-color:#979595;height:22px !important;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:30%' align='center' colspan='3'><img src='Interfaz/Contenido/Imagen/Logo.png' style='width: 211px; height: 72px' alt=''></td>");
                    // 3 (logo) + titulo + 1 (codigo) + 2 (version) = columnas
                    out.print("<td colspan='" + (columnas - 6) + "' style='width:49%;'><h6 style='text-align: center;'>REGISTRO HISTÓRICO DE PRODUCCIÓN TALLER</h6></td>");
                    out.print("<td style='width:10%' align='center'><b>CODIGO</b><br /><b style='color:black'>R-PM-001</b></td>");
                    out.print("<td style='width:10%' colspan='2' align='center'><b>VERSION</b><br /><b style='color:black'>" + versionFormato + "</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:8%;' align='center' rowspan='2'><b>Fecha</b></td>");
                    out.print("<td style='width:7%;' align='center' rowspan='2'><b>S/S</b></td>");
                    out.print("<td style='width:10%;' align='center' rowspan='2'><b>Maquina</b></td>");
                    out.print("<td style='width:5.7%;' align='center' rowspan='2'><b>Plano</b></td>");
                    if (v5) {
                        out.print("<td style='width:7%;' align='center' rowspan='2'><b>No. de parte</b></td>");
                    }
                    out.print("<td style='width:8%' align='center' rowspan='2'><b>Pieza</b></td>");
                    out.print("<td style='width:6%' align='center' rowspan='2'><b>Cantidad</b></td>");
                    if (fechaint <= 20231020) {
                        out.print("<td style='width:25%;' align='center' rowspan='2' colspan='2'><b>Descripcion</b></td>");
                    } else {
                        out.print("<td style='width:25%;' align='center' rowspan='2'><b>Descripcion</b></td>");
                        out.print("<td style='width:7%;' align='center' rowspan='2'><b>Técnico</b></td>");
                    }
                    if (fechaint <= 20231020) {
                        out.print("<td style='width:20%' align='center' colspan='2'><b>Tiempo Empleado</b></td>");
                    } else {
                        out.print("<td style='width:20%' align='center' colspan='3'><b>Tiempo Empleado</b></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    if (fechaint <= 20231020) {
                        out.print("<td style='width:8%;' align='center'><b>Hora Inicio</b></td>");
                        out.print("<td style='width:8%;'align='center'><b>Hora Fin</b></td>");
                    } else {
                        out.print("<td style='width:8%;' align='center'><b>Hora Inicio</b></td>");
                        out.print("<td style='width:8%;'align='center'><b>Hora Fin</b></td>");
                        out.print("<td style='width:7%;'align='center'><b>Tiempo (H:mm)</b></td>");
                    }
                    out.print("</tr>");
                    if (lst_registro != null) {
                        for (int i = 0; i < lst_registro.size(); i++) {
                            Object[] obj_registro = (Object[]) lst_registro.get(i);
                            String[] Fechar = obj_registro[1].toString().split(" ");
                            String[] FecharI = obj_registro[12].toString().split("-");
                            String[] FecharF = obj_registro[13].toString().split("-");
                            out.print("<tr>");
                            out.print("<td align='center'>" + Fechar[0] + "</td>");
                            out.print("<td align='center'>" + obj_registro[4] + "</td>");
                            out.print("<td align='center'>" + obj_registro[6] + "</td>");
                            out.print("<td align='center'>" + obj_registro[8] + "</td>");
                            if (v5) {
                                // Indice 16: columna agregada al final de
                                // sp_rgt_c_registro para no correr las anteriores.
                                out.print("<td align='center'>" + numeroParte(obj_registro, 16) + "</td>");
                            }
                            out.print("<td align='center'>" + obj_registro[9] + "</td>");
                            out.print("<td align='center'>" + obj_registro[10] + "</td>");
                            if (fechaint <= 20231020) {
                                out.print("<td colspan='2'>" + obj_registro[11] + "<br /><div style='float:right;'><b style='color:black;'>" + obj_registro[2] + "</b></div></td>");
                                out.print("<td align='center' colspan='2'>" + FecharI[1] + " | " + FecharF[1] + " | <b>Tiempo: </b>" + obj_registro[14] + " hora(s)</td>");
                            } else {
                                out.print("<td>" + obj_registro[11] + "</td>");
                                out.print("<td align='center'><b style='color:black;'>" + obj_registro[2] + "</b></td>");
                                // Cuando inicio y fin caen en dias distintos se muestra la
                                // fecha debajo de la hora para que el tiempo acumulado sea
                                // autoexplicativo (ej. 864 h no sorprende si se ve la fecha
                                // de agosto al lado). Dias iguales: solo la hora, como antes.
                                boolean diasDistintos = !FecharI[0].equals(FecharF[0]);
                                String sufI = diasDistintos
                                    ? "<br/><small class='text-muted' style='font-size:10px;'>" + FecharI[0].replace("/", "-") + "</small>"
                                    : "";
                                String sufF = diasDistintos
                                    ? "<br/><small class='text-muted' style='font-size:10px;'>" + FecharF[0].replace("/", "-") + "</small>"
                                    : "";
                                out.print("<td align='center'><div data-toggle='tooltip' data-placement='top' title='" + FecharI[0].replace("/", "-") + "'>" + FecharI[1] + sufI + "</div></td>");
                                out.print("<td align='center'><div data-toggle='tooltip' data-placement='top' title='" + FecharF[0].replace("/", "-") + "'>" + FecharF[1] + sufF + "</div></td>");
                                out.print("<td align='center'>" + obj_registro[14] + "</td>");
                            }
                        }
                    }
                    out.print("</tr>");
                    out.print("</tbody>");
                    if (fechaint >= 20250410) {
                        out.print("<tr><td colspan='" + columnas + "'><span style='    font-size: 12px;\n"
                                + "    font-style: italic;\n"
                                + "    margin-left: 8px;'>La informacion personal en este documento sera tratada y protegida de acuerdo con nuestras politicas de proteccion de datos personales. </span></td></tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</section>");
                }
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="REPORTE R-PM-001">
                try {
                    query = pageContext.getRequest().getAttribute("Query").toString();
                } catch (Exception e) {
                    query = "";
                }
                // Los parametros viajan aparte para que consultaRegistrosFiltro
                // los enlace con setParameter: la query ya no concatena valores.
                java.util.List<Object> queryParams = null;
                try {
                    queryParams = (java.util.List<Object>) pageContext.getRequest().getAttribute("QueryParams");
                } catch (Exception e) {
                    queryParams = null;
                }
                if (queryParams == null) {
                    queryParams = new java.util.ArrayList<>();
                }
                lst_registro = jpa_solicitud.consultaRegistrosFiltro(query, queryParams);
                lst_tecnicos = jpa_usuarios.consultaUsuariosRol("TEC.PR");
                out.print("<section class='section'>");
                out.print("<div class='section-header'>");
                out.print("<div style='display:flex;align-items:center'>"
                        + "<div><h1>Modulo R-PM-001</h1></div></div>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='FormControlOpc mb-1'>");

                out.print("<div>");
                out.print("<button class='btn btn-red' style='border-radius: 4px;' onclick='mostrarConvencion(2);' data-toggle='tooltip' data-placement='top' title='Filtrar R-PM-001'><i class='fas fa-search'></i></button>");
                out.print("</div>");

                out.print("<div class='ml-2'>");
                out.print("<button class='btn imprimir' onclick='Imprimir();' data-toggle='tooltip' data-placement='top' title='Imprimir / PDF'><i class='fas fa-print'></i></button>");
                out.print("</div>");

                out.print("<div class='ml-2'>");
                out.print("<button class='btn excel'  onclick=\"tableToExcel('TableRPM1', 'Reporte')\" data-toggle='tooltip' data-placement='top' title='Exportar a Excel'><i class='fas fa-file-excel'></i></button>");
                out.print("</div>");

                out.print("</div>");
                //<editor-fold defaultstate="collapsed" desc="MODAL FILTRO DE BUSQUEDA">
                out.print("<div class='sweet-local' tabindex='-7' id='Ventana2' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_filtro'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h3>Filtrar R-PM-001</h3>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2);' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<form action='Solicitud?opc=8' method='post' name='FormRegF' id='FormRegF' class='needs-validation' novalidate='' >");
                out.print("<div class='FormControl'>");
                out.print("<div class='col-lg-6'>"
                        + "<b class='clssB'>Fecha Inicio:</b><br/>"
                        + "<input type='date' class='form-control' name='fch_inicio' id='fechaI-id' value='' required placeholder='Fecha Inicial' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Fecha Inicio'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                out.print("<div class='col-lg-6'>"
                        + "<b class='clssB'>Hora Inicio:</b><br/>"
                        + "<input type='time' class='form-control' name='horaI' id='HoraI-id' value='' placeholder='Hora Inicial' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Hora Inicio'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                out.print("</div>");
                out.print("<div class='FormControl mt-3'>");
                out.print("<div class='col-lg-6'>"
                        + "<b class='clssB'>Fecha Fin:</b><br/>"
                        + "<input type='date' class='form-control' name='fch_fin' id='fechaI-id' value='' required placeholder='Fecha Fin' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Fecha Fin'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                out.print("<div class='col-lg-6'>"
                        + "<b class='clssB'>Hora Fin:</b><br/>"
                        + "<input type='time' class='form-control' name='horaF' id='HoraI-id' value='' placeholder='Hora Final' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Hora Final'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                out.print("</div>");

                out.print("<div class='FormControl mt-3'>");
                out.print("<div class='col-lg-6'>"
                        + "<b class='clssB'># Solicitud:</b><br/>"
                        + "<input type='text' class='form-control' name='txt_sol' id='sol-id' value='' placeholder='Numero Solicitud' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Numero Solicitud'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                out.print("<div class='col-lg-6'>"
                        + "<b class='clssB'>Máquina:</b><br/>"
                        + "<select class='select2 form-control' name='slc_maquina'  id='select-id' required>"
                        + "<option value='0' style='display:none;'>Seleccione Máquina</option>"
                        + "<option value='0'>Todas</option>");
                for (int i = 0; lst_maquina != null && i < lst_maquina.size(); i++) {
                    Object[] obj_maquina = (Object[]) lst_maquina.get(i);
                    out.print("<option value='" + obj_maquina[0] + "'>" + obj_maquina[1] + "</opction>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='FormControl mt-3'>");
                out.print("<div class='col-lg-6'>"
                        + "<b class='clssB'>Plano:</b><br/>"
                        + "<select class='select2 form-control' name='slc_plano'  id='select-id' required>"
                        + "<option value='0' style='display:none;'>Seleccione Plano</option>"
                        + "<option value='0'>Todas</option>");
                for (int i = 0; lst_tipoP != null && i < lst_tipoP.size(); i++) {
                    Object[] obj_tipo = (Object[]) lst_tipoP.get(i);
                    out.print("<optgroup label='" + obj_tipo[0] + "'>");
                    for (int j = 0; lst_planos != null && j < lst_planos.size(); j++) {
                        Object[] obj_plano = (Object[]) lst_planos.get(j);
                        if (obj_tipo[0].equals(obj_plano[2])) {
                            out.print("<option value='" + obj_plano[0] + "'>" + obj_plano[1] + "</option>");
                        }
                    }
                    out.print("</optgroup>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("<div class='col-lg-6'>"
                        + "<b class='clssB'>Pieza:</b><br/>"
                        + "<input type='text' class='form-control' name='txt_pieza' id='Pieza-id' value='' placeholder='Pieza' autocomplete='off' data-toggle='tooltip' data-placemente='top' title='Pieza'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div></div>");
                out.print("</div>");

                out.print("<div class='mt-3'>");
                out.print("<div class='col-lg-12'>"
                        + "<b class='clssB'>Técnicos:</b><br/>"
                        + "<select class='select2 form-control' name='slc_tecnico'  id='tecnicos-id' required>"
                        + "<option value='0' style='display:none;'>Seleccione Técnico</option>"
                        + "<option value='0'>Todos</option>");
                for (int i = 0; lst_tecnicos != null && i < lst_tecnicos.size(); i++) {
                    Object[] obj_tec = (Object[]) lst_tecnicos.get(i);
                    out.print("<option>" + obj_tec[1] + "</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='mt-3' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-red btn-lg'>Consultar</button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("<div id='Imprimir'>");
                out.print("<table class='table-hover' id='TableRPM1' style='width:100%'>");
                out.print("<thead>");
                out.print("<tr>");
                // La version del reporte emitido la marca la fecha INICIAL del
                // rango consultado: si el filtro arranca antes del corte, lo que
                // se esta imprimiendo es formato v4 y no lleva No. de parte.
                int fechaFiltroInt = FECHA_LANZAMIENTO_V5;
                try {
                    fechaFiltroInt = Integer.parseInt(
                            pageContext.getRequest().getAttribute("filtro_fecha_ini")
                                    .toString().replace("-", "").split(" ")[0]);
                } catch (Exception e) {
                    // Sin fecha en el filtro se asume el formato vigente.
                    fechaFiltroInt = FECHA_LANZAMIENTO_V5;
                }
                boolean v5Filtro = fechaFiltroInt >= FECHA_LANZAMIENTO_V5;
                String versionFiltro = v5Filtro ? "5" : "4";
                // Esta tabla tiene una columna mas que la vista individual porque
                // agrupa Descripcion con colspan 2.
                int columnasFiltro = v5Filtro ? 13 : 12;
                out.print("<tr><td colspan='" + columnasFiltro + "' style='background-color:#979595;height:22px !important;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='width:30%' align='center' colspan='3'><img src='Interfaz/Contenido/Imagen/Logo.png' style='width: 211px; height: 72px' alt=''></td>");
                // 3 (logo) + 7 (titulo) + 1 (codigo) + 2 (version) = 13
                out.print("<td colspan='" + (columnasFiltro - 6) + "' style='width:49%;'><h6 style='text-align: center;'>REGISTRO HISTÓRICO DE PRODUCCIÓN TALLER</h6></td>");
                out.print("<td style='width:10%'  align='center'><b>CODIGO</b><br /><b style='color:black'>R-PM-001</b></td>");
                out.print("<td style='width:10%' colspan='2' align='center'><b>VERSION</b><br /><b style='color:black'>" + versionFiltro + "</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='width:8%;' align='center' rowspan='2'><b>Fecha</b></td>");
                out.print("<td style='width:7%;' align='center' rowspan='2'><b>S/S</b></td>");
                out.print("<td style='width:10%;' align='center' rowspan='2'><b>Máquina</b></td>");
                out.print("<td style='width:5.7%;' align='center' rowspan='2'><b>Plano</b></td>");
                if (v5Filtro) {
                    out.print("<td style='width:7%;' align='center' rowspan='2'><b>No. de parte</b></td>");
                }
                out.print("<td style='width:8%' align='center' rowspan='2'><b>Pieza</b></td>");
                out.print("<td style='width:6%' align='center' rowspan='2'><b>Cantidad</b></td>");
                out.print("<td style='width:25%;' align='center' rowspan='2' colspan='2'><b>Descripción</b></td>");
                out.print("<td style='width:7%;' align='center' rowspan='2'><b>Tecnico</b></td>");
                out.print("<td style='width:20%' align='center' colspan='3'><b>Tiempo Empleado</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='width:8%;' align='center'><b>Hora Inicio</b></td>");
                out.print("<td style='width:8%;'align='center'><b>Hora Fin</b></td>");
                out.print("<td style='width:7%;'align='center'><b>Tiempo (H:mm)</b></td>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                if (lst_registro != null) {
                    for (int i = 0; i < lst_registro.size(); i++) {
                        Object[] obj_registro = (Object[]) lst_registro.get(i);
                        String[] Fechar = obj_registro[1].toString().split(" ");
                        String[] FecharI = obj_registro[12].toString().split("-");
                        String[] FecharF = obj_registro[13].toString().split("-");
                        out.print("<tr>");
                        out.print("<td align='center'>" + Fechar[0] + "</td>");
                        out.print("<td align='center'>" + obj_registro[4] + "</td>");
                        out.print("<td align='center'>" + obj_registro[6] + "</td>");
                        out.print("<td align='center'>" + obj_registro[8] + "</td>");
                        if (v5Filtro) {
                            // Indice 19: columna agregada al final de la consulta
                            // del case 8 para no correr los indices 0..18.
                            out.print("<td align='center'>" + numeroParte(obj_registro, 19) + "</td>");
                        }
                        out.print("<td align='center'>" + obj_registro[9] + "</td>");
                        out.print("<td align='center' colspan='2'>" + obj_registro[10] + "</td>");
                        out.print("<td>" + obj_registro[11] + "</td>");
                        out.print("<td align='center'><b style='color:black;'>" + obj_registro[2].toString().replace("Ã‰", "É").replace("Ã‘", "Ñ") + "</b></td>");
                        // Bug corregido: el tooltip de Hora Inicio usaba FecharF[0] en vez
                        // de FecharI[0]. Tambien se aplica la misma logica de fecha visible
                        // cuando inicio y fin caen en dias distintos.
                        boolean diasDistintosFiltro = !FecharI[0].equals(FecharF[0]);
                        String sufIF = diasDistintosFiltro
                            ? "<br/><small class='text-muted' style='font-size:10px;'>" + FecharI[0].replace("/", "-") + "</small>"
                            : "";
                        String sufFF = diasDistintosFiltro
                            ? "<br/><small class='text-muted' style='font-size:10px;'>" + FecharF[0].replace("/", "-") + "</small>"
                            : "";
                        out.print("<td align='center'><div data-toggle='tooltip' data-placement='top' title='" + FecharI[0].replace("/", "-") + "'>" + FecharI[1] + sufIF + "</div></td>");
                        out.print("<td align='center'><div data-toggle='tooltip' data-placement='top' title='" + FecharF[0].replace("/", "-") + "'>" + FecharF[1] + sufFF + "</div></td>");
                        out.print("<td align='center'>" + obj_registro[14] + "</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='" + columnasFiltro + "' align='center'><b style='color:black;'>No se encuentran registros</b></td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("<tr><td colspan='" + columnasFiltro + "'><span style='    font-size: 12px;\n"
                        + "    font-style: italic;\n"
                        + "    margin-left: 8px;'>La informacion personal en este documento sera tratada y protegida de acuerdo con nuestras politicas de proteccion de datos personales. </span></td></tr>");
                out.print("</table>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            }
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(Tag_registro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

    /**
     * Numero de parte del plano de un registro, listo para imprimir.
     *
     * La columna se agrego AL FINAL de cada consulta para no correr los indices
     * que el resto de la tabla lee por posicion, y el indice difiere segun la
     * consulta: 16 en sp_rgt_c_registro y 19 en el filtro del case 8. Se lee
     * defensivamente porque las dos alimentan la misma variable lst_registro.
     *
     * @param fila fila devuelta por la consulta
     * @param indice posicion del numero de parte en esa consulta
     * @return el numero de parte, o cadena vacia si el plano no lo tiene
     */
    private static String numeroParte(Object[] fila, int indice) {
        if (fila == null || indice >= fila.length || fila[indice] == null) {
            return "";
        }
        return fila[indice].toString().trim();
    }
}
