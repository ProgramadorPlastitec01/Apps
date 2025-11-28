package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import Controladores.RetoJpaController;
import Controladores.ConfiguracionJpaController;
import Controladores.RegistroJpaController;
import Controladores.EquipoJpaController;

public class Tag_reto extends TagSupport {
    
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        RetoJpaController RetoJpa = new RetoJpaController();
        ConfiguracionJpaController ConfiguracionJpa = new ConfiguracionJpaController();
        RegistroJpaController RegistroJpa = new RegistroJpaController();
        EquipoJpaController EquipoJpa = new EquipoJpaController();
        String nombre_rol = pageContext.getSession().getAttribute("Nombre_rol").toString();
        String rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString();
        List lst_reto = null, lst_configuracion = null, lst_registro = null, lst_equipo = null, lst_reto_detalle = null, lst_retoInsp = null,
                lst_retoGrpo = null, lst_modifica = null;
        int Estado = 0, Tipo = 0, IdRetoDetalle = 0, Validacion = 0;
        String Modulo = "", FechaReto = "";
        try {
            try {
                Modulo = pageContext.getRequest().getAttribute("Modulo").toString();
            } catch (Exception e) {
                Modulo = "RetoCabecera";
            }
            switch (Modulo) {
                case "RetoCabecera":
                    //<editor-fold defaultstate="collapsed" desc="RETO CABECERA">
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Tecnico") || nombre_rol.equals("Coordinador") || nombre_rol.equals("Jefe_Mtto")) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                        out.print("<div class=\"card shadow mb-4\">");
                        out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                        out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Registrar R-MTF-052</h6>");
                        out.print("</a>");
                        out.print("<div class=\"collapse\" id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                        out.print("<div class=\"card-body\" >");
                        out.print("<form action='Reto?opc=2' method='post' id='FormReto'>");
                        out.print("<div style='width:50%;float:left'>");
                        out.print("<b>Fecha: </b>");
                        out.print("<input class='form-control' type='date' name='Txt_fecha' id='Txt_fecha' placeholder='Nombre(s)' title='Nombres(s)' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_fecha');val1.add(Validate.Presence);</script>");
                        out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" class=' mt-2 far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                        out.print("<span onclick=\"javascript:location.href='Reto?opc=1'\" class='mt-2 far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                        out.print("</div>");
                        out.print("<br /><div style='display:none'><input type='submit'  value='Registrar' id='Btn_accion' /></div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-header py-3\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Retos de Inspección</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Ver</th>");
                    out.print("<th>Fecha</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Informe</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tfoot>");
                    out.print("<tr>");
                    out.print("<th>Ver</th>");
                    out.print("<th>Fecha</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Informe</th>");
                    out.print("</tr>");
                    out.print("</tfoot>");
                    out.print("<tbody>");
                    lst_reto = RetoJpa.ConsultaRetoxDia();
                    if (lst_reto != null) {
                        for (int i = 0; i < lst_reto.size(); i++) {
                            Object[] Obj_reto = (Object[]) lst_reto.get(i);
                            out.print("<tr>");
                            String Link = "RetoDetalle";
                            out.print("<td align='center'><span onclick=\"location.href='Reto?opc=1&Modulo=" + Link + "&FechaReto=" + Obj_reto[1] + "&Tipo=1'\" class='fa fa-eye fa-size_small' title='Reto Detalle'></span></td>");
                            out.print("<td  align='center'><b>" + Obj_reto[1] + "</b></td>");
                            if (Obj_reto[5].equals(0) || Obj_reto[7].equals(0) || Obj_reto[9].equals(0)) {
                                out.print("<td align='center' class='text-warning font-weight-bold'>En Proceso</td>");
                            } else {
                                out.print("<td  align='center' class='text-success font-weight-bold'>Completo</td>");
                            }
                            out.print("<td><div class='d-flex justify-content-around' style='font-size:21px'>");
                            out.print("<span onclick=\"location.href='Reto?opc=9&Modulo=Visor&FechaReto=" + Obj_reto[1] + "'\" class=\"fas fa-file-alt\"></span>");
                            out.print("</div></td>");
                        }
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    break;
                case "RetoDetalle":
                    out.print("<input type='hidden' id='Txt_enlace_volver' value='Reto?opc=1' />");
                    //<editor-fold defaultstate="collapsed" desc="RETO DETALLE">
                    //<editor-fold defaultstate="collapsed" desc="VARIABLES DE ENTORNO">
                    try {
                        FechaReto = pageContext.getRequest().getParameter("FechaReto");
                    } catch (Exception e) {
                        FechaReto = "";
                    }
                    try {
                        Tipo = Integer.parseInt(pageContext.getRequest().getParameter("Tipo"));
                    } catch (NumberFormatException e) {
                        Tipo = 1;
                    }
                    try {
                        IdRetoDetalle = Integer.parseInt(pageContext.getRequest().getParameter("IdRetoDetalle"));
                    } catch (NumberFormatException e) {
                        IdRetoDetalle = 0;
                    }
                    try {
                        Validacion = Integer.parseInt(pageContext.getRequest().getParameter("Validacion"));
                    } catch (NumberFormatException e) {
                        Validacion = 0;
                    }
                    //</editor-fold>
                    lst_retoGrpo = RetoJpa.ConsultaRetoIds(FechaReto, Tipo);
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Inspectora_Calidad")) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR CABECERA">
                        out.print("<div class=\"card shadow mb-4\">");
                        out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse " + ((lst_reto_detalle != null) ? "collapsed" : "") + "\" data-toggle=\"collapse\" role=\"button\" aria-expanded='false' aria-controls=\"collapseCardExample\">");
                        out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Registrar Cabecera</h6>");
                        out.print("</a>");
                        out.print("<div class='collapse' id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                        out.print("<div class=\"card-body\" >");
                        out.print("<form action='Reto?opc=8' method='post' id='FormRetoDetalle'>");
                        if (lst_retoGrpo != null) {
                            Object[] Obj_reto = (Object[]) lst_retoGrpo.get(0);
                            int Cont = Integer.parseInt(Obj_reto[12].toString());
                            out.print("<input type='hidden' name='Val' value='" + (Cont) + "'>");
                            out.print("<input type='hidden' name='IdReto' value='" + Obj_reto[0] + "'>");
                            out.print("<input type='hidden' name='Tipo' value='" + Tipo + "'>");
                            out.print("<input type='hidden' name='FechaReto' value='" + FechaReto + "'>");
                            out.print("<input type='hidden' name='Modulo' value='" + Modulo + "'>");
                        } else {
                            out.print("<input type='hidden' name='Val' value='" + 2 + "'>");
                            out.print("<input type='hidden' name='IdReto' value='" + 0 + "'>");
                            out.print("<input type='hidden' name='Tipo' value='" + Tipo + "'>");
                            out.print("<input type='hidden' name='FechaReto' value='" + FechaReto + "'>");
                            out.print("<input type='hidden' name='Modulo' value='" + Modulo + "'>");
                        }
                        out.print("<div class='d-flex'>");
                        out.print("<div style='width:30%;'>");
                        out.print("<b>Fecha: </b>");
                        out.print("<input class='form-control' type='date' name='Txt_fecha' id='Txt_fecha' placeholder='Hora' title='Hora' onchange='javascript:this.value=this.value.toUpperCase();' value='" + FechaReto + "' readonly/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_fecha');val1.add(Validate.Presence);</script>");
                        out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" class=' mt-2 far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                        out.print("<span onclick=\"javascript:location.href='Reto?opc=1&Modulo=RetoDetalle&FechaReto=" + FechaReto + "&Tipo=" + Tipo + "'\" class='mt-2 far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                        out.print("</div>");
                        out.print("<div style='width:30%'>");
                        out.print("<b>Turno: </b>");
                        out.print("<select class='form-control' name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                        out.print("<option value='0' >Seleccionar Turno</option>");
                        lst_configuracion = ConfiguracionJpa.ConsultarConfiguracionCategoria("Turno");
                        if (lst_configuracion != null) {
                            for (int i = 0; i < lst_configuracion.size(); i++) {
                                Object[] obj_configuracion = (Object[]) lst_configuracion.get(i);
                                out.print("<option value='" + obj_configuracion[2] + "'>" + obj_configuracion[2] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</div>");
                        out.print("<div style='width:30%'>");
                        out.print("<b>Hora: </b>");
                        out.print("<input class='form-control' type='time' name='Txt_hora' id='Txt_hora' placeholder='Hora' title='Hora' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_hora');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Inspectora_Calidad") || nombre_rol.equals("Coordinadora_Calidad")) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR DETALLE ENSAMBLADORA Y GRAFADORA">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:" + ((Validacion == 1) ? "block" : "none") + ";'>");
                        out.print("<div class='ContModReto'>");
                        
                        out.print("<div class=\"modal-header\">");
                        out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel2\">Registrar Maquina<b></b></h5>");
                        out.print("</div>");
                        
                        out.print("<div class='mt-3'>");
                        out.print("<div class=\"selectgroup w-90 mb-3\">\n"
                                + "                        <label class=\"selectgroup-item\">\n"
                                + "                          <input type=\"radio\" name='TipoReg' onclick='CambioDivRegistro()' style='display:none' value=\"1\" class=\"selectgroup-input\" checked  >\n"
                                + "                          <span id='' class=\"selectgroup-button selectgroup-button-icon negrita\">UNO x UNO</span>\n"
                                + "                        </label>\n"
                                + "                        <label class=\"selectgroup-item\" >\n"
                                + "                          <input type=\"radio\" name='TipoReg' onclick='CambioDivRegistro()'  style='display:none' value=\"2\" class=\"selectgroup-input\" >\n"
                                + "                          <span id='' class=\"selectgroup-button selectgroup-button-icon\"><b>MASIVO</b></span>\n"
                                + "                        </label>\n"
                                + "                      </div>");
                        out.print("</div>");
                        
                        out.print("<div id='RG1' style='display:block'>");
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO UNO X UNO">
                        out.print("<form action='Reto?opc=3' method='post' id='FormUnoXUno'>");
                        out.print("<div class=\"modal-body\">");
                        if (Validacion == 1) {
                            List lst_ultimo = RetoJpa.ConsultaUltimoRetoDetalle(rol_usuario);
                            if (lst_ultimo != null) {
                                //<editor-fold defaultstate="collapsed" desc="FORM">
                                Object[] obj_ultimo = (Object[]) lst_ultimo.get(0);
                                int Id_Maquina = Integer.parseInt(obj_ultimo[4].toString());
                                String Maquina = obj_ultimo[5].toString();
                                String Lote = obj_ultimo[6].toString();
                                String Observacion = obj_ultimo[10].toString();
                                out.print("<input type='hidden' name='Modulo' value='RetoDetalle'>");
                                out.print("<input type='hidden' name='FechaReto' value='" + FechaReto + "'>");
                                out.print("<input type='hidden' name='Tipo' value='" + Tipo + "'>");
                                out.print("<input type='hidden' name='Validacion' id='Validacion' value='0'>");
                                
                                out.print("<div class='d-flex'>");
                                out.print("<div style='width:50%;'>");
                                out.print("<b>Turno: </b>");
                                out.print("<select class='form-control' name='IdReto' id='IdReto' title='Turno'>");
                                out.print("<option value='0' >Seleccionar Turno</option>");
                                if (lst_retoGrpo != null) {
                                    for (int i = 0; i < lst_retoGrpo.size(); i++) {
                                        Object[] obj_turno = (Object[]) lst_retoGrpo.get(i);
                                        out.print("<option value='" + obj_turno[0] + "'>" + obj_turno[2] + " - " + obj_turno[4] + "</option>");
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('IdReto');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                
                                out.print("<b>Maquina: </b>");
                                out.print("<select class='form-control' name='Cbx_maquina' id='Cbx_maquina' title='Maquina'>");
                                out.print("<option value='" + Id_Maquina + "'>" + Maquina + "</option>");
                                lst_equipo = EquipoJpa.EquipoRetos(Tipo);
                                if (lst_equipo != null) {
                                    for (int i = 0; i < lst_equipo.size(); i++) {
                                        Object[] obj_equipo = (Object[]) lst_equipo.get(i);
                                        if (Id_Maquina != Integer.parseInt(obj_equipo[0].toString())) {
                                            out.print("<option value='" + obj_equipo[0] + "'>" + obj_equipo[1] + "</option>");
                                        }
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_maquina');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                
                                out.print("<b>Lote: </b>");
                                out.print("<input class='form-control' type='text' name='Txt_lote' id='Txt_lote' value='" + Lote + "' placeholder='Lote' autocomplete='off' tilte='Lote' />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                                
                                out.print("</div>");
                                
                                out.print("<div style='width:50%;'>");
                                out.print("<b>Reto :</b>");
                                
                                out.print("<select class='form-control' name='Cbx_reto' id='Cbx_reto' title='Reto'>");
                                out.print("<option value='0' >Seleccionar Reto</option>");
                                lst_retoInsp = RegistroJpa.Traer_registro_ot("R-MTF-052", FechaReto);
                                if (lst_retoInsp != null) {
                                    Object[] obj_reto = (Object[]) lst_retoInsp.get(0);
                                    String[] RetoDes = {};
                                    if (Tipo == 1) {
                                        RetoDes = obj_reto[4].toString().split("///");
                                    } else {
                                        RetoDes = obj_reto[5].toString().split("///");
                                    }
                                    for (int h = 0; h < RetoDes.length; h++) {
                                        String valor = RetoDes[h];
                                        int pos = valor.indexOf(":");
                                        if (pos != -1) {
                                            valor = valor.substring(0, pos);
                                        }
                                        out.print("<option value='" + RetoDes[h] + "'>" + valor + "</option>");
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_reto');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                
                                out.print("<b>Cumple: </b>");
                                out.print("<div class=\"selectgroup2 selectgroup-pills\">\n"
                                        + "                        <label class=\"selectgroup-item2 mr-2\">\n"
                                        + "                          <input type=\"radio\" name=\"Estado\" value=\"1\" style='display:none' class=\"selectgroup-input2\" >\n"
                                        + "                          <span class=\"selectgroup-button2 selectgroup-button-icon2\">SI</i></span>\n"
                                        + "                        </label>\n"
                                        + "                        <label class=\"selectgroup-item2\">\n"
                                        + "                          <input type=\"radio\" name=\"Estado\" value=\"2\"  style='display:none' class=\"selectgroup-input2\" checked=\"\">\n"
                                        + "                          <span class=\"selectgroup-button2 selectgroup-button-icon2\">NO</i></span>\n"
                                        + "                        </label>\n"
                                        + "                    </div>");
                                
                                out.print("<b>Observaciones: </b>");
                                out.print("<input class='form-control' type='text' name='Txt_observacion' value='" + Observacion + "' id='Txt_observacion' placeholder='Observaciones' autocomplete='off' tilte='Observaciones' />");
                                
                                out.print("</div>");
                                out.print("</div>");
                                //</editor-fold>
                            }
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="FORM">
                            out.print("<input type='hidden' name='Modulo' value='RetoDetalle'>");
                            out.print("<input type='hidden' name='FechaReto' value='" + FechaReto + "'>");
                            out.print("<input type='hidden' name='Tipo' value='" + Tipo + "'>");
                            out.print("<input type='hidden' name='Validacion' id='Validacion' value='0'>");
                            
                            out.print("<div class='d-flex'>");
                            out.print("<div style='width:50%;'>");
                            out.print("<b>Turno: </b>");
                            out.print("<select class='form-control' name='IdReto' id='IdReto' title='Turno'>");
                            out.print("<option value='0' >Seleccionar Turno</option>");
                            if (lst_retoGrpo != null) {
                                for (int i = 0; i < lst_retoGrpo.size(); i++) {
                                    Object[] obj_turno = (Object[]) lst_retoGrpo.get(i);
                                    out.print("<option value='" + obj_turno[0] + "'>" + obj_turno[2] + " - " + obj_turno[4] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('IdReto');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            
                            out.print("<b>Maquina: </b>");
                            out.print("<select class='form-control' name='Cbx_maquina' id='Cbx_maquina' title='Maquina'>");
                            out.print("<option value='0' >Seleccionar Maquina</option>");
                            lst_equipo = EquipoJpa.EquipoRetos(Tipo);
                            if (lst_equipo != null) {
                                for (int i = 0; i < lst_equipo.size(); i++) {
                                    Object[] obj_equipo = (Object[]) lst_equipo.get(i);
                                    out.print("<option value='" + obj_equipo[0] + "'>" + obj_equipo[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_maquina');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            
                            out.print("<b>Lote: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_lote' id='Txt_lote' placeholder='Lote' autocomplete='off' tilte='Lote' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                            
                            out.print("</div>");
                            
                            out.print("<div style='width:50%;'>");
                            out.print("<b>Reto :</b>");
                            
                            out.print("<select class='form-control' name='Cbx_reto' id='Cbx_reto' title='Reto'>");
                            out.print("<option value='0' >Seleccionar Reto</option>");
                            lst_retoInsp = RegistroJpa.Traer_registro_ot("R-MTF-052", FechaReto);
                            if (lst_retoInsp != null) {
                                Object[] obj_reto = (Object[]) lst_retoInsp.get(0);
                                String[] RetoDes = {};
                                if (Tipo == 1) {
                                    RetoDes = obj_reto[4].toString().split("///");
                                } else {
                                    RetoDes = obj_reto[5].toString().split("///");
                                }
                                for (int h = 0; h < RetoDes.length; h++) {
                                    String valor = RetoDes[h];
                                    int pos = valor.indexOf(":");
                                    if (pos != -1) {
                                        valor = valor.substring(0, pos);
                                    }
                                    out.print("<option value='" + RetoDes[h] + "'>" + valor + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_reto');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            
                            out.print("<b>Cumple: </b>");
                            out.print("<div class=\"selectgroup2 selectgroup-pills\">\n"
                                    + "                        <label class=\"selectgroup-item2 mr-2\">\n"
                                    + "                          <input type=\"radio\" name=\"Estado\" value=\"1\" style='display:none' class=\"selectgroup-input2\" >\n"
                                    + "                          <span class=\"selectgroup-button2 selectgroup-button-icon2\">SI</i></span>\n"
                                    + "                        </label>\n"
                                    + "                        <label class=\"selectgroup-item2\">\n"
                                    + "                          <input type=\"radio\" name=\"Estado\" value=\"2\"  style='display:none' class=\"selectgroup-input2\" checked=\"\">\n"
                                    + "                          <span class=\"selectgroup-button2 selectgroup-button-icon2\">NO</i></span>\n"
                                    + "                        </label>\n"
                                    + "                    </div>");
                            
                            out.print("<b>Observaciones: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_observacion' id='Txt_observacion' placeholder='Observaciones' autocomplete='off' tilte='Observaciones' />");
                            
                            out.print("</div>");
                            out.print("</div>");

                            //</editor-fold>
                        }
                        out.print("</div>");
                        
                        out.print("<div class=\"modal-footer\">");
                        
                        out.print("<div class='d-flex w-100 justify-content-between'>");
                        
                        out.print("<div>");
                        out.print("<input type='submit' onclick='ContinuarReg(1);' class=\"btn btn-info\" value='Guardar y continuar'>");
                        out.print("</div>");
                        
                        out.print("<div>");
                        out.print("<input type='submit' onclick='ContinuarReg(0);' class=\"btn btn-primary mr-2\" value='Registrar'>");
                        out.print("<button class=\"btn btn-secondary\" type=\"button\" onclick='mostrarConvencion(2)'  data-dismiss=\"modal\">Cancelar</button>");
                        out.print("</div>");
                        
                        out.print("</div>");
                        
                        out.print("</div>");
                        
                        out.print("</form>");
                        //</editor-fold>
                        out.print("</div>");
                        
                        out.print("<div id='RG2' style='display:none'>");

                        //<editor-fold defaultstate="collapsed" desc="REGISTRO MASIVO">
                        out.print("<form action='Reto?opc=10' method='post' id='FormMasivo'>");
                        out.print("<input type='hidden' name='Modulo' value='RetoDetalle'>");
                        out.print("<input type='hidden' name='FechaReto' value='" + FechaReto + "'>");
                        out.print("<input type='hidden' name='Tipo' value='" + Tipo + "'>");
                        out.print("<div class=\"modal-body\">");
                        
                        out.print("<div class='d-flex justify-content-around'>");
                        out.print("<div class='row'>");
                        //<editor-fold defaultstate="collapsed" desc="TURNO">
                        out.print("<b>Turno: </b>");
                        out.print("<select class='form-control' name='IdReto' id='TurnoVal' title='Turno'>");
                        out.print("<option value='0' >Seleccionar Turno</option>");
                        if (lst_retoGrpo != null) {
                            for (int i = 0; i < lst_retoGrpo.size(); i++) {
                                Object[] obj_turno = (Object[]) lst_retoGrpo.get(i);
                                out.print("<option value='" + obj_turno[0] + "'>" + obj_turno[2] + " - " + obj_turno[4] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('TurnoVal');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        //</editor-fold>
                        out.print("</div>");
                        
                        out.print("<div class='row'>");
                        //<editor-fold defaultstate="collapsed" desc="MAQUINA">
                        out.print("<b>Maquina: </b>");
                        out.print("<select class='form-control' name='Cbx_maquina' id='Cbx_maquina2' title='Maquina'>");
                        out.print("<option value='0' >Seleccionar Maquina</option>");
                        lst_equipo = EquipoJpa.EquipoRetos(Tipo);
                        if (lst_equipo != null) {
                            for (int i = 0; i < lst_equipo.size(); i++) {
                                Object[] obj_equipo = (Object[]) lst_equipo.get(i);
                                out.print("<option value='" + obj_equipo[0] + "'>" + obj_equipo[1] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_maquina2');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        //</editor-fold>
                        out.print("</div>");
                        
                        out.print("<div class='row'>");
                        //<editor-fold defaultstate="collapsed" desc="LOTE">
                        out.print("<b>Lote: </b>");
                        out.print("<input class='form-control' type='text' name='Txt_lote' id='lote2' placeholder='Lote' autocomplete='off' tilte='Lote'  required />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('lote2');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                        
                        out.print("<div>");
                        //<editor-fold defaultstate="collapsed" desc="RETO">
                        int Cont = 0;
                        int Cant = 0;
                        lst_retoInsp = RegistroJpa.Traer_registro_ot("R-MTF-052", FechaReto);
                        if (lst_retoInsp != null) {
                            Object[] obj_reto = (Object[]) lst_retoInsp.get(0);
                            String[] RetoDes = {};
                            if (Tipo == 1) {
                                RetoDes = obj_reto[4].toString().split("///");
                            } else {
                                RetoDes = obj_reto[5].toString().split("///");
                            }
                            for (int h = 0; h < RetoDes.length; h++) {
                                String valor = RetoDes[h];
                                Cont = (h + 1);
                                Cant = RetoDes.length;
                                int pos = valor.indexOf(":");
                                if (pos != -1) {
                                    valor = valor.substring(0, pos);
                                }
                                out.print("<div class='d-flex justify-content-around mt-2'>");
                                
                                out.print("<div class='row'>");
                                out.print("<b>Reto: #" + (Cont) + " </b>");
                                out.print("<input class='form-control' type='text' name=''  placeholder='Lote' autocomplete='off' tilte='Lote' value='" + valor + "' readonly='false' />");
                                out.print("<input type='hidden' id='LoteArg" + Cont + "' placeholder='Lote' value='" + RetoDes[h] + "' />");
                                out.print("</div>");
                                
                                out.print("<div class='row'>");
                                out.print("<div>");
                                out.print("<b>Cumple: </b>");
                                out.print("<div class=\"selectgroup2 selectgroup-pills\">\n"
                                        + "                        <label for='Estado1_" + Cont + "' class=\"selectgroup-item2 mr-2\">\n"
                                        + "                          <input type=\"radio\" name='Estado" + Cont + "' value=\"1\" id='Estado1_" + Cont + "' style='display:none' class=\"selectgroup-input2\" >\n"
                                        + "                          <span class=\"selectgroup-button2 selectgroup-button-icon2\">SI</i></span>\n"
                                        + "                        </label>\n"
                                        + "                        <label for='Estado2_" + Cont + "' class=\"selectgroup-item2\">\n"
                                        + "                          <input type='radio' name='Estado" + Cont + "' value='2' id='Estado2_" + Cont + "' onclick='sugerirObservacion(" + Cont + ")' style='display:none' class='selectgroup-input2' >\n"
                                        + "                          <span class=\"selectgroup-button2 selectgroup-button-icon2\">NO</i></span>\n"
                                        + "                        </label>\n"
                                        + "                    </div>");
                                out.print("</div>");
                                out.print("</div>");
                                
                                out.print("<div class='row'>");
                                out.print("<b>Observaciones: </b>");
                                out.print("<input class='form-control' type='text' name='' id='Observacion" + Cont + "' placeholder='Observaciones' autocomplete='off' tilte='Observaciones' />");
                                out.print("</div>");
                                
                                out.print("</div>");
                                
                                out.print("<input type='hidden' name='ArgReto" + Cont + "' id='ArgReto" + Cont + "'>");
//                                out.print("<hr class='hrStl'>");
                            }
                        }

                        //</editor-fold>
                        out.print("</div>");
                        
                        out.print("</div>");
                        
                        out.print("<input type='hidden' name='Cantidad' id='Cantidad' value='" + Cant + "'>");
                        out.print("<div class=\"modal-footer\">");
                        out.print("<div>");
                        out.print("<input type='submit' class=\"btn btn-primary mr-2\" onclick='return ArregloRetosMasivo(" + Cant + ")' value='Registrar'>");
                        out.print("<button class=\"btn btn-secondary\" type=\"button\" onclick='mostrarConvencion(2)'  data-dismiss=\"modal\">Cancelar</button>");
                        out.print("</div>");
                        out.print("</div>");
                        
                        out.print("</div>");
                        out.print("</form>");

                        //</editor-fold>
                        out.print("</div>");
                        
                        out.print("</div>");
                        //</editor-fold>
                        if (IdRetoDetalle > 0) {
                            //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                            lst_modifica = RetoJpa.ConsultaRetoDetalleaId(IdRetoDetalle);
                            if (nombre_rol.equals("Inspectora_Calidad")) {
                                if (lst_modifica != null) {
                                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR DETALLE ID">
                                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                                    out.print("<div class='ContModReto'>");
                                    out.print("<div class=\"modal-header d-flex\">");
                                    out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel2\">Modificar Maquina<b></b></h5>");
                                    out.print("</div>");
                                    Object[] Obj_modifica = (Object[]) lst_modifica.get(0);
                                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR MAQUINA">
                                    out.print("<div class=\"modal-body\" id='DivCab2'>");
                                    out.print("<form action='Reto?opc=5' method='post' id='FormModificar'>");
                                    out.print("<input type='hidden' name='Modulo' value='RetoDetalle'>");
                                    out.print("<input type='hidden' name='FechaReto' value='" + FechaReto + "'>");
                                    out.print("<input type='hidden' name='Tipo' value='" + Tipo + "'>");
                                    out.print("<input type='hidden' name='IdRetoDetalle' id='IdRetoDetalle' value='" + IdRetoDetalle + "'>");
                                    
                                    out.print("<div class='d-flex'>");
                                    
                                    out.print("<div style='width:50%'>");
                                    out.print("<b>Turno: </b>");
                                    out.print("<input class='form-control' type='text' name='IdReto' id='' autocomplete='off' tilte='' value='" + Obj_modifica[2] + " - " + Obj_modifica[3] + "' readonly='fale' />");
                                    out.print("<b>Maquina: </b>");
                                    out.print("<select class='form-control' name='Cbx_maquina' id='Cbx_maquina' title='Maquina'>");
                                    out.print("<option value='" + Obj_modifica[4] + "' >" + Obj_modifica[5] + "</option>");
                                    lst_equipo = EquipoJpa.EquipoRetos(Tipo);
                                    if (lst_equipo != null) {
                                        for (int i = 0; i < lst_equipo.size(); i++) {
                                            Object[] obj_equipo = (Object[]) lst_equipo.get(i);
                                            if (Integer.parseInt(Obj_modifica[4].toString()) != Integer.parseInt(obj_equipo[0].toString())) {
                                                out.print("<option value='" + obj_equipo[0] + "'>" + obj_equipo[1] + "</option>");
                                            }
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_maquina');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    
                                    out.print("<b>Lote: </b>");
                                    out.print("<input class='form-control' type='text' name='Txt_lote' id='Txt_loteM' placeholder='Lote' autocomplete='off' tilte='Lote' value='" + Obj_modifica[6] + "' />"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_loteM');val1.add(Validate.Presence);</script>");
                                    
                                    out.print("</div>");
                                    
                                    out.print("<div style='width:50%'>");
                                    
                                    out.print("<b>Reto :</b>");
                                    
                                    out.print("<div>");
                                    out.print("<select class='form-control' name='Cbx_reto' id='Cbx_reto' title='Reto'>");
                                    out.print("<option value='" + Obj_modifica[7] + "' >" + Obj_modifica[8] + "</option>");
                                    lst_retoInsp = RegistroJpa.Traer_registro_ot("R-MTF-052", FechaReto);
                                    if (lst_retoInsp != null) {
                                        Object[] obj_reto = (Object[]) lst_retoInsp.get(0);
                                        String[] RetoDes = {};
                                        if (Tipo == 1) {
                                            RetoDes = obj_reto[4].toString().split("///");
                                        } else {
                                            RetoDes = obj_reto[5].toString().split("///");
                                        }
                                        for (int h = 0; h < RetoDes.length; h++) {
                                            String valor = RetoDes[h];
                                            int pos = valor.indexOf(":");
                                            if (pos != -1) {
                                                valor = valor.substring(0, pos);
                                            }
                                            if (!Obj_modifica[8].equals(valor)) {
                                                out.print("<option value='" + RetoDes[h] + "'>" + valor + "</option>");
                                            }
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_reto');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("</div>");
                                    int Est = Integer.parseInt(Obj_modifica[9].toString());
                                    out.print("<b>Cumple: </b>");
                                    out.print("<div class=\"selectgroup2 selectgroup-pills\">\n"
                                            + "                        <label class=\"selectgroup-item2 mr-2\">\n"
                                            + "                          <input type=\"radio\" name=\"Estado\" value=\"1\" style='display:none' class=\"selectgroup-input2\" " + ((Est == 1) ? "checked" : "") + " >\n"
                                            + "                          <span class=\"selectgroup-button2 selectgroup-button-icon2\">SI</i></span>\n"
                                            + "                        </label>\n"
                                            + "                        <label class=\"selectgroup-item2\">\n"
                                            + "                          <input type=\"radio\" name=\"Estado\" value=\"2\"  style='display:none' class=\"selectgroup-input2\" " + ((Est == 2) ? "checked" : "") + ">\n"
                                            + "                          <span class=\"selectgroup-button2 selectgroup-button-icon2\">NO</i></span>\n"
                                            + "                        </label>\n"
                                            + "                    </div>");
                                    
                                    out.print("<b>Observaciones: </b>");
                                    out.print("<input class='form-control' type='text' name='Txt_observacion' id='Txt_observacion' placeholder='Observaciones' autocomplete='off' tilte='Observaciones' value='" + Obj_modifica[10] + "' />");
                                    out.print("</div>");
                                    out.print("</div>");
                                    
                                    out.print("</div>");
                                    out.print("<div class=\"modal-footer\">");
                                    out.print("<input type='submit' class=\"btn btn-primary\" value='Modificar'>");
                                    out.print("<button class=\"btn btn-secondary\" type=\"button\" onclick='mostrarConvencion(1)'  data-dismiss=\"modal\">Cancelar</button>");
                                    out.print("</div>");
                                    out.print("</form>");

                                    //</editor-fold>
                                    out.print("</div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                } else {
                                    //<editor-fold defaultstate="collapsed" desc="SALIDA POR FALLO">
                                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                                    out.print("<div class='ContModReto'>");
                                    out.print("<div class=\"modal-header d-flex\">");
                                    out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel2\">Modificar Maquina<b></b></h5>");
                                    out.print("</div>");
                                    out.print("<h3><i class=\"fas fa-sad-tear\"></i>Fallo en modificar, favor informa a T.I</h3");
                                    out.print("</div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                }
                            }
                            if (nombre_rol.equals("Coordinadora_Calidad")) {
                                if (lst_modifica != null) {
                                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR CABECERA">
                                    Object[] Obj_modifica = (Object[]) lst_modifica.get(0);
                                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                                    out.print("<div class='ContModReto'>");
                                    out.print("<div class=\"modal-header d-flex\">");
                                    out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel2\">Modificar Maquina<b></b></h5>");
                                    out.print("</div>");
                                    out.print("<div class=\"modal-body\" id='DivCab1' style='display:blcck'>");
                                    out.print("<form action='Reto?opc=8' method='post' id='FormMCabecera'>");
                                    
                                    out.print("<input type='hidden' name='Tipo' value='" + Tipo + "'>");
                                    out.print("<input type='hidden' name='FechaReto' value='" + FechaReto + "'>");
                                    out.print("<input type='hidden' name='Modulo' value='" + Modulo + "'>");
                                    out.print("<input type='hidden' name='Val' value='1'>");
                                    out.print("<input type='hidden' name='IdReto' value='" + Obj_modifica[1] + "'>");
                                    
                                    out.print("<div class='d-flex'>");
                                    out.print("<div style='width:30%;'>");
                                    out.print("<b>Fecha: </b>");
                                    out.print("<input class='form-control' type='date' name='Txt_fecha' id='Txt_fecha' placeholder='Hora' title='Hora' onchange='javascript:this.value=this.value.toUpperCase();' value='" + FechaReto + "' readonly/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_fecha');val1.add(Validate.Presence);</script>");
                                    out.print("<span onclick=\"javascript:document.getElementById('Btn_accion1').click();\" class=' mt-2 far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                                    out.print("<span onclick=\"javascript:location.href='Reto?opc=1&Modulo=RetoDetalle&FechaReto=" + FechaReto + "&Tipo=" + Tipo + "'\" class='mt-2 far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                                    out.print("</div>");
                                    out.print("<div style='width:30%'>");
                                    out.print("<b>Turno: </b>");
                                    out.print("<select class='form-control' name='Cbx_turno' id='Cbx_turno' title='Turno'>");
                                    out.print("<option value='0' >Seleccionar Turno</option>");
                                    lst_configuracion = ConfiguracionJpa.ConsultarConfiguracionCategoria("Turno");
                                    if (lst_configuracion != null) {
                                        for (int i = 0; i < lst_configuracion.size(); i++) {
                                            Object[] obj_configuracion = (Object[]) lst_configuracion.get(i);
                                            if (Obj_modifica[2].equals(obj_configuracion[2])) {
                                                out.print("<option value='" + obj_configuracion[2] + "' selected>" + obj_configuracion[2] + "</option>");
                                            } else {
                                                out.print("<option value='" + obj_configuracion[2] + "'>" + obj_configuracion[2] + "</option>");
                                            }
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_turno');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                    out.print("</div>");
                                    out.print("<div style='width:30%'>");
                                    out.print("<b>Hora: </b>");
                                    out.print("<input class='form-control' type='time' name='Txt_hora' id='Txt_hora' placeholder='Hora' title='Hora' value='" + Obj_modifica[3] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_hora');val1.add(Validate.Presence);</script>");
                                    out.print("</div>");
                                    out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion1' /></div>");
                                    out.print("</div>");
                                    out.print("</form>");
                                    out.print("</div>");
                                    
                                    out.print("</div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                } else {
                                    //<editor-fold defaultstate="collapsed" desc="SALIDA POR FALLO">
                                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                                    out.print("<div class='ContModReto'>");
                                    out.print("<div class=\"modal-header d-flex\">");
                                    out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel2\">Modificar Maquina<b></b></h5>");
                                    out.print("</div>");
                                    out.print("<h3><i class=\"fas fa-sad-tear\"></i>Fallo en modificar, favor informa a T.I</h3");
                                    out.print("</div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                }
                            }
                            //</editor-fold>
                        }
                    }
                    
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"selectgroup w-90 mb-3\">\n"
                            + "                        <label class=\"selectgroup-item\">\n"
                            + "                          <input type=\"radio\" name=\"transportation\" onclick=\"location.href='Reto?opc=1&Modulo=" + Modulo + "&FechaReto=" + FechaReto + "&Tipo=" + 1 + "'\"  style='display:none' value=\"2\" class=\"selectgroup-input\" " + ((Tipo == 1) ? "checked" : "") + " >\n"
                            + "                          <span id=\"grafadora\" class=\"selectgroup-button selectgroup-button-icon negrita\">GRAFADORA</span>\n"
                            + "                        </label>\n"
                            + "                        <label class=\"selectgroup-item\" >\n"
                            + "                          <input type=\"radio\" name=\"transportation\"  onclick=\"location.href='Reto?opc=1&Modulo=" + Modulo + "&FechaReto=" + FechaReto + "&Tipo=" + 2 + "'\" style='display:none' value=\"1\" class=\"selectgroup-input\" " + ((Tipo == 2) ? "checked" : "") + " >\n"
                            + "                          <span id=\"ensambladora\" class=\"selectgroup-button selectgroup-button-icon\"><b>ENSAMBLADORA</b></span>\n"
                            + "                        </label>\n"
                            + "                      </div>");
                    
                    if (lst_retoGrpo != null) {
                        Object[] Obj_reto = (Object[]) lst_retoGrpo.get(0);
                        if (Obj_reto[2] != null && Obj_reto[4] != null) {
                            out.print("<div class='d-flex justify-content-end'>");
                            if (nombre_rol.equals("Administrador") || nombre_rol.equals("Inspectora_Calidad")) {
                                //<editor-fold defaultstate="collapsed" desc="BOTON REGISTRAR">
                                out.print("<button type='button' onclick='mostrarConvencion(2)' class='btn btn-primary btn-sm' style='    height: 2%;'><i class='fas fa-plus'></i></button>");
                                //</editor-fold>
                            }
                            if (nombre_rol.equals("Administrador") || nombre_rol.equals("Inspectora_Calidad") || nombre_rol.equals("Coordinadora_Calidad")) {
                                //<editor-fold defaultstate="collapsed" desc="ENVIAR DATOS MODIFICAR">
                                out.print("<div class='mb-2 ml-3'><form action='Reto?opc=1' method='post' id='FormEnvMod'>");
                                out.print("<input type='hidden' name='Modulo' value='RetoDetalle'>");
                                out.print("<input type='hidden' name='Tipo' value='" + Tipo + "'>");
                                out.print("<input type='hidden' name='FechaReto' value='" + FechaReto + "'>");
                                out.print("<input type='hidden' name='IdRetoDetalle' id='IdMaquina' value=''>");
                                out.print("<input type='hidden' name='TipoRol' id='TipoRol' value=''>");
                                if (nombre_rol.equals("Coordinadora_Calidad") || nombre_rol.equals("Administrador")) {
                                    out.print("<button type='button' onclick='ValidarInputMaquina()' class='btn btn-warning btn-sm' title='Editar cabecera'><i class='fas fa-pencil-alt'></i></button>");
                                } else {
                                    out.print("<button type='button' onclick='ValidarInputMaquina()' class='btn btn-warning btn-sm' title='Editar detalle'><i class='fas fa-pencil-alt'></i></button>");
                                }
                                out.print("</form></div>");
                                //</editor-fold>
                            }
                            if (nombre_rol.equals("Administrador") || nombre_rol.equals("Coordinadora_Calidad")) {
                                //<editor-fold defaultstate="collapsed" desc="ENVIAR INACTIVO REGISTRO DETALLE">
                                out.print("<div class='mb-2 ml-3'>");
                                out.print("<form action='Reto?opc=7' method='post' id='FormEliminar'>");
                                out.print("<input type='hidden' name='Modulo' value='RetoDetalle'>");
                                out.print("<input type='hidden' name='Tipo' value='" + Tipo + "'>");
                                out.print("<input type='hidden' name='FechaReto' value='" + FechaReto + "'>");
                                out.print("<input type='hidden' name='IdRetoDetalle' id='IdDetalle' value=''>");
                                out.print("<button type='button' onclick='InactivarDetalle()' class='btn btn-danger btn-sm'><i class=\"fas fa-trash\" title='Eliminar reto detalle'></i></button>");
                                out.print("</form>");
                                out.print("</div>");
                                //</editor-fold>
                            }
                            if (nombre_rol.equals("Administrador") || nombre_rol.equals("Coordinadora_Calidad")) {
                                //<editor-fold defaultstate="collapsed" desc="ELIMINAR RETO VALIDADO">
                                out.print("<div class='mb-2 ml-3'>");
                                out.print("<form action='Reto?opc=12' method='post' id='FormEliminarReto'>");
                                out.print("<input type='hidden' name='Modulo' value='RetoDetalle'>");
                                out.print("<input type='hidden' name='Tipo' value='" + Tipo + "'>");
                                out.print("<input type='hidden' name='FechaReto' value='" + FechaReto + "'>");
                                out.print("<input type='hidden' name='IdRetoElimnar' id='IdRetoElimnar' value=''>");
                                out.print("<button type='button' onclick='EliminarReto()' class='btn btn-secondary btn-sm'><i class=\"fas fa-ban\" title='Eliminar cabecera'></i></button>");
                                out.print("</form>");
                                out.print("</div>");
                                //</editor-fold>
                            }
                        }
                        out.print("</div>");
                    }
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR FIRMA PRODUCCION">
                    out.print("<div class=\"modal fade\" id=\"FPModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel2\" aria-hidden=\"true\">");
                    out.print("  <div class=\"modal-dialog\" role=\"document\">");
                    out.print("    <div class=\"modal-content\" style=\"width:72%; font-size:15px;\">");
                    out.print("      <div class=\"modal-header\">");
                    out.print("        <h5 class=\"modal-title\" id=\"exampleModalLabel2\">Firma Producción</h5>");
                    out.print("        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">");
                    out.print("          <span aria-hidden=\"true\">&times;</span>");
                    out.print("        </button>");
                    out.print("      </div>");
                    out.print("      <form action='Reto?opc=6' method='post' id='FormPrd'>");
                    out.print("        <div class=\"modal-body\">");
                    out.print("          <input type='hidden' id='ModuloInput' name='Modulo' value='" + Modulo + "'>");
                    out.print("          <input type='hidden' id='FechaRetoInput' name='FechaReto' value='" + FechaReto + "'>");
                    out.print("          <input type='hidden' id='TipoInput' name='Tipo' value='" + Tipo + "'>");
                    out.print("          <input type='hidden' id='IdRetoFirma' name='IdReto' value=''>");
                    out.print("          <div class=\"form-group\">");
                    out.print("            <label for=\"DocFirma\"><b>Documento:</b></label>");
                    out.print("            <input class='form-control' type='number' name='Documento' id='DocFirma' placeholder='Documento' autocomplete='off' required>");
                    out.print("          </div>");
                    out.print("          <div class=\"form-group\">");
                    out.print("            <label for=\"CodFirma\"><b>Código:</b></label>");
                    out.print("            <input class='form-control' type='number' name='Codigo' id='CodFirma' placeholder='Código' autocomplete='off' required>");
                    out.print("          </div>");
                    out.print("        </div>");
                    out.print("        <div class=\"modal-footer\">");
                    out.print("          <button class=\"btn btn-primary\" type=\"submit\">Firmar</button>");
                    out.print("          <button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancelar</button>");
                    out.print("        </div>");
                    out.print("      </form>");
                    out.print("    </div>");
                    out.print("  </div>");
                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR FIRMA MTF">
                    out.print("<form action='Reto?opc=4' method='post' id='FormMttoS'>");
                    out.print("<input type='hidden' id='' name='Modulo' value='" + Modulo + "'>");
                    out.print("<input type='hidden' id='' name='FechaReto' value='" + FechaReto + "'>");
                    out.print("<input type='hidden' id='' name='Tipo' value='" + Tipo + "'>");
                    out.print("<input type='hidden' id='IdRetoP' name='IdReto' value=''>");
                    out.print("</form>");
                    //</editor-fold>
                    lst_registro = RegistroJpa.Traer_registro_ot("R-MTF-052", FechaReto);
                    //<editor-fold defaultstate="collapsed" desc="CABECERA">
                    if (lst_registro != null) {
                        out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                        out.print("<tr><td colspan='7' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                        out.print("<tr>");
                        out.print("<td align='center' style='width:25%;' colspan='2' rowspan='2'>");
                        out.print("<img src='Interfaz/Images/Logo.png' alt='Logo' style='width:60%' /></td>");
                        out.print("<td colspan='4' align='center' style='width:50%;'>REGISTRO</td>");
                        Object[] Obj_registro = (Object[]) lst_registro.get(0);
                        out.print("<td align='center' style='width:50%;'>CODIGO " + Obj_registro[1] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='4' align='center'>" + Obj_registro[2] + "</td>");
                        out.print("<td align='center'>VERSIÓN: " + Obj_registro[3] + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
                    }   //</editor-fold>
                    if (lst_retoGrpo != null) {
                        for (int i = 0; i < lst_retoGrpo.size(); i++) {
                            Object[] Obj_retoId = (Object[]) lst_retoGrpo.get(i);
                            //<editor-fold defaultstate="collapsed" desc="POST CABECERA">
                            out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                            out.print("<tr>");
                            out.print("<td colspan='1' style='width:25%'><b>FECHA:</b> " + FechaReto + "</td>");
                            out.print("<td colspan='1' style='width:50%'><b>TURNO:</b> " + ((Obj_retoId[2] == null) ? "" : Obj_retoId[2]) + " </td>");
                            if (nombre_rol.equals("Administrador") || nombre_rol.equals("Coordinadora_Calidad")) {
                                if (Obj_retoId[5] == null || Obj_retoId[6] == null || Obj_retoId[7] == null) {
                                    out.print("<td colspan='1'><b >HORA:</b><span style='color:blue'>" + ((Obj_retoId[4] == null) ? "" : Obj_retoId[4] + "<input type='radio' class='ml-1' name='IdMq' onclick='IdRetoEliminar(" + Obj_retoId[0] + ")'>") + "</span></td>");
                                }else{
                                    out.print("<td colspan='1'><b >HORA:</b><span style='color:blue'>" + ((Obj_retoId[4] == null) ? "" : Obj_retoId[4]) + "</span></td>");
                                }
                            } else {
                                out.print("<td colspan='1'><b >HORA:</b><span style='color:blue'>" + ((Obj_retoId[4] == null) ? "" : Obj_retoId[4]) + "</span></td>");
                            }
                            out.print("</tr>");
                            out.print("<tr>");
                            if (nombre_rol.equals("Administrador") || nombre_rol.equals("Coordinador") || nombre_rol.equals("Asistente") || nombre_rol.equals("Tecnico") || nombre_rol.equals("Jefe_Mtto")) {
                                out.print("<td colspan='1'><b>EJECUTO MTTO:</b> <b style='color:gray'>" + (Obj_retoId[5] == null ? "<a class='btn btn-info btn-sm' style='font-size:9px' onclick='EnviarLoteId(" + Obj_retoId[0] + ")'><i style='color:white' class=\"fas fa-signature\"></i></a>" : Obj_retoId[5]) + "</b></td>");
                            } else {
                                out.print("<td colspan='1'><b>EJECUTO MTTO:</b> <b style='color:gray'>" + (Obj_retoId[5] == null ? " " : Obj_retoId[5]) + "</b></td>");
                            }
                            out.print("<td colspan='1'><b>VERIFICO CALIDAD:</b> <b style='color:blue'>" + (Obj_retoId[6] == null ? "" : Obj_retoId[6]) + "</b></td>");
                            if (nombre_rol.equals("Administrador") || nombre_rol.equals("Coordinador") || nombre_rol.equals("Asistente") || nombre_rol.equals("Tecnico") || nombre_rol.equals("Jefe_Mtto")) {
                                out.print(
                                        "<td colspan='1'><b>PRODUCCION:</b> <b style='color:black'>"
                                        + (Obj_retoId[7] == null
                                                ? "<a class='btn btn-info btn-sm' style='font-size:9px' data-toggle='modal' data-target='#FPModal' onclick='EnviarFirmaProduccion(" + Obj_retoId[0] + ")'><i style='color:white' class=\"fas fa-signature\"></i></a>"
                                                : "<span class='opcion' onclick='QuitarFirmaPR(" + Obj_retoId[0] + ",\"" + Modulo + "\",\"" + FechaReto + "\"," + Tipo + ")'>" + Obj_retoId[7] + "</span>")
                                        + "</b></td>"
                                );
                                
                            } else {
                                out.print("<td colspan='1'><b>PRODUCCION:</b> <b style='color:black'>" + (Obj_retoId[7] == null ? "" : Obj_retoId[7]) + "</b></td>");
                            }
                            out.print("</tr>");
                            out.print("</table>");
                            //</editor-fold>
                            if (Tipo >= 1 && Obj_retoId[2] != null && Obj_retoId[4] != null) {
                                lst_reto_detalle = RetoJpa.ConsultaRetoDetallexDiaxTurno(FechaReto, Obj_retoId[2].toString(), Obj_retoId[4].toString(), Tipo);
                                //<editor-fold defaultstate="collapsed" desc="DETALLE">
                                out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tr class='text-center'>");
                                if (Tipo == 1) {
                                    out.print("<td rowspan='2' style='width:4%'>#G</td>");
                                } else {
                                    out.print("<td rowspan='2' style='width:4%'>#E</td>");
                                }
                                out.print("<td rowspan='2' style='width:8%'>LOTE</td>");
                                out.print("<td rowspan='2' style='width:13%'>PRODUCTO ACTUAL</td>");
                                out.print("<td rowspan='2' style='width:42%'>RETO</td>");
                                out.print("<td colspan='2' class='text-center'>CUMPLE</td>");
                                out.print("<td rowspan='2'>OBSERVACIONES</td>");
                                out.print("</tr>");
                                
                                out.print("<tr class='text-center'>");
                                out.print("<td style='width:4%'>SI</td>");
                                out.print("<td style='width:4%'>NO</td>");
                                out.print("</tr>");
                                
                                if (lst_reto_detalle != null) {
                                    for (int j = 0; j < lst_reto_detalle.size(); j++) {
                                        Object[] Obj_detalle = (Object[]) lst_reto_detalle.get(j);
                                        out.print("<tr>");
                                        String Mqn = Obj_detalle[6].toString().replace("GRAFADORA", "").replace("ENSAMBLADORA DE MARIPOSA", "");
                                        if (Obj_retoId[7] != null) {
                                            out.print("<td class='text-center'>" + Mqn + "</td>");
                                        } else {
                                            out.print("<td class='text-center'>" + Mqn + "<br/><input type='radio' name='IdMq' onclick='IdModElMaquina(" + Obj_detalle[0] + ")'></td>");
                                        }
                                        out.print("<td style='color:blue'>" + Obj_detalle[7] + "</td>");
                                        out.print("<td>" + Obj_detalle[8] + "</td>");
                                        int filaPorGrupo = Integer.parseInt(Obj_detalle[18].toString());
                                        if (filaPorGrupo == 1) {
                                            int cantidadRepeticiones = Integer.parseInt(Obj_detalle[17].toString());
                                            out.print("<td id='' rowspan='" + cantidadRepeticiones + "'>" + Obj_detalle[9] + "</td>");
                                        }
                                        Estado = Integer.parseInt(Obj_detalle[11].toString());
                                        if (Estado == 1) {
                                            out.print("<td class='text-center'><i class=\"fas fa-times\"></i></td>");
                                            out.print("<td class='text-center'></td>");
                                        } else {
                                            out.print("<td class='text-center'></td>");
                                            out.print("<td class='text-center'><i class=\"fas fa-times\"></i></td>");
                                        }
                                        out.print("<td style='color:blue'>" + Obj_detalle[12] + "</td>");
                                        out.print("</tr>");
                                    }
                                }
                                out.print("</table>");
                                //</editor-fold>
                            }
                        }
                    }
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    break;
                case "Visor":
                    out.print("<input type='hidden' id='Txt_enlace_volver' value='Reto?opc=1' />");
                    //<editor-fold defaultstate="collapsed" desc="VISOR">
                    try {
                        FechaReto = pageContext.getRequest().getParameter("FechaReto");
                    } catch (Exception e) {
                        FechaReto = "";
                    }
                    out.print("<input type='hidden' id='Txt_enlace_volver' value='Reto?opc=1' />");
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-body\">");
                    out.print("<div style='float:right'>");
                    out.print("<span class='fas fa-print fa-size_small' onclick=\"Imprimir();\" title='Imprimir'></span>");
                    out.print("</div>");
                    out.print("<div id='Div_export'>");
                    lst_registro = RegistroJpa.Traer_registro_ot("R-MTF-052", FechaReto);
                    //<editor-fold defaultstate="collapsed" desc="CABECERA">
                    if (lst_registro != null) {
                        out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                        out.print("<tr><td colspan='7' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                        out.print("<tr>");
                        out.print("<td align='center' style='width:25%;' colspan='2' rowspan='2'>");
                        out.print("<img src='Interfaz/Images/Logo.png' alt='Logo' style='width:60%' /></td>");
                        out.print("<td colspan='4' align='center' style='width:50%;'>REGISTRO</td>");
                        Object[] Obj_registro = (Object[]) lst_registro.get(0);
                        out.print("<td align='center' style='width:50%;'>CODIGO " + Obj_registro[1] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='4' align='center'>" + Obj_registro[2] + "</td>");
                        out.print("<td align='center'>VERSIÓN: " + Obj_registro[3] + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
                    }   //</editor-fold>
                    lst_retoGrpo = RetoJpa.ConsultarCabeceraRetoVisor(FechaReto);
                    if (lst_retoGrpo != null) {
                        for (int i = 0; i < lst_retoGrpo.size(); i++) {
                            Object[] Obj_retoId = (Object[]) lst_retoGrpo.get(i);
                            //<editor-fold defaultstate="collapsed" desc="POST CABECERA">
                            out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                            out.print("<tr>");
                            out.print("<td colspan='1' style='width:25%'><b>FECHA:</b> " + FechaReto + "</td>");
                            out.print("<td colspan='1' style='width:50%'><b>TURNO:</b> " + ((Obj_retoId[2] == null) ? "" : Obj_retoId[2]) + " </td>");
                            out.print("<td colspan='1'><b >HORA:</b><span style='color:blue'>" + ((Obj_retoId[4] == null) ? "" : Obj_retoId[4]) + "</span></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='1'><b>EJECUTO MTTO:</b> <b style='color:gray'>" + (Obj_retoId[5] == null ? "" : Obj_retoId[5]) + "</b></td>");
                            out.print("<td colspan='1'><b>VERIFICO CALIDAD:</b> <b style='color:blue'>" + (Obj_retoId[6] == null ? "" : Obj_retoId[6]) + "</b></td>");
                            out.print("<td colspan='1'><b>PRODUCCION:</b> <b style='color:black'>" + (Obj_retoId[7] == null ? "" : Obj_retoId[7]) + "</b></td>");
                            out.print("</tr>");
                            out.print("</table>");
                            //</editor-fold>
                            if (Obj_retoId[2] != null && Obj_retoId[4] != null) {
                                lst_reto_detalle = RetoJpa.ConsultaRetoDetallexDiaxTurno(FechaReto, Obj_retoId[2].toString(), Obj_retoId[4].toString(), Integer.parseInt(Obj_retoId[3].toString()));
                                //<editor-fold defaultstate="collapsed" desc="DETALLE">
                                out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tr class='text-center'>");
                                if (Integer.parseInt(Obj_retoId[3].toString()) == 1) {
                                    out.print("<td rowspan='2' style='width:4%'>#G</td>");
                                } else {
                                    out.print("<td rowspan='2' style='width:4%'>#E</td>");
                                }
                                out.print("<td rowspan='2' style='width:8%'>LOTE</td>");
                                out.print("<td rowspan='2' style='width:13%'>PRODUCTO ACTUAL</td>");
                                out.print("<td rowspan='2' style='width:42%'>RETO</td>");
                                out.print("<td colspan='2' class='text-center'>CUMPLE</td>");
                                out.print("<td rowspan='2'>OBSERVACIONES</td>");
                                out.print("</tr>");
                                
                                out.print("<tr class='text-center'>");
                                out.print("<td style='width:4%'>SI</td>");
                                out.print("<td style='width:4%'>NO</td>");
                                out.print("</tr>");
                                
                                if (lst_reto_detalle != null) {
                                    for (int j = 0; j < lst_reto_detalle.size(); j++) {
                                        Object[] Obj_detalle = (Object[]) lst_reto_detalle.get(j);
                                        out.print("<tr>");
                                        String Mqn = Obj_detalle[6].toString().replace("GRAFADORA", "").replace("ENSAMBLADORA DE MARIPOSA", "");
                                        out.print("<td class='text-center'>" + Mqn + "</td>");
                                        out.print("<td style='color:blue'>" + Obj_detalle[7] + "</td>");
                                        out.print("<td>" + ((Obj_detalle[8] == null) ? "" : Obj_detalle[8]) + "</td>");
                                        int filaPorGrupo = Integer.parseInt(Obj_detalle[18].toString());
                                        if (filaPorGrupo == 1) {
                                            int cantidadRepeticiones = Integer.parseInt(Obj_detalle[17].toString());
                                            out.print("<td id='' rowspan='" + cantidadRepeticiones + "'>" + Obj_detalle[9] + "</td>");
                                        }
                                        Estado = Integer.parseInt(Obj_detalle[11].toString());
                                        if (Estado == 1) {
                                            out.print("<td class='text-center'><i class=\"fas fa-times\"></i></td>");
                                            out.print("<td class='text-center'></td>");
                                        } else {
                                            out.print("<td class='text-center'></td>");
                                            out.print("<td class='text-center'><i class=\"fas fa-times\"></i></td>");
                                        }
                                        out.print("<td style='color:blue'>" + Obj_detalle[12] + "</td>");
                                        out.print("</tr>");
                                    }
                                }
                                out.print("</table>");
                                //</editor-fold>
                            }
                        }
                        out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                        out.print("<tr class='text-center'><td colspan='7'><b style='    font-size: 9px;'>La información personal en este documento será tratada y protegida de acuerdo con nuestras políticas de protección de datos personales.</b></td></tr>");
                        out.print("</table>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    break;
                default:
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_reto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return super.doStartTag();
    }
}
