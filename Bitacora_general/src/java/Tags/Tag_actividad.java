package Tags;

import Controladoras.ActividadJpaController;
import Controladoras.CargoJpaController;
import Controladoras.FormularioJpaController;
import Controladoras.UsuarioJpaController;
import Controladoras.MaquinaJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_actividad extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol = sesion.getAttribute("Rol").toString();
        String nombre = sesion.getAttribute("Nombre").toString();
        int IdUsuario = Integer.parseInt(sesion.getAttribute("Identificacion").toString());
        int IdArea = Integer.parseInt(sesion.getAttribute("Area").toString());
        int CargoUsa = Integer.parseInt(sesion.getAttribute("Cargo").toString());
        FormularioJpaController jpa_formulario = new FormularioJpaController();
        ActividadJpaController jpa_actividad = new ActividadJpaController();
        CargoJpaController jpa_cargo = new CargoJpaController();
        UsuarioJpaController jpa_usuario = new UsuarioJpaController();
        MaquinaJpaController jpa_maquina = new MaquinaJpaController();
        int contM = 1;
        List Consultaform = null;
        List Consecutivo = null;
        List ConCampo = null;
        List NomCampo = null;
        List MdcActividad = null;
        List permisos = null;
        List lst_cargos = null;
        List lst_Actividades = null;
        List lst_Usuarios = null;
        List lst_UsuariosArea = null;
        List lst_maquina = null;
        lst_cargos = jpa_cargo.ConsultaCargosPorIdArea(IdArea);
        lst_Usuarios = jpa_usuario.ConsultaUsuarioPorIdCargo(CargoUsa);
        lst_UsuariosArea = jpa_usuario.ConsultaUsuarioArea(IdArea);
        Consecutivo = jpa_actividad.ConsultaConsecutivoPorIdArea(IdArea);
        Object[] obj_conse = (Object[]) Consecutivo.get(0);
        ConCampo = jpa_actividad.ConsultaContadorCamposPorIdCargo(CargoUsa);
        Object[] obj_conCam = (Object[]) ConCampo.get(0);
        long cont = (Long) obj_conCam[1];
        permisos = jpa_cargo.ConsultaCargosPorId(CargoUsa);
        Object[] obj_permisos = (Object[]) permisos.get(0);
        lst_Actividades = jpa_cargo.ConsultaCargosPorIdArea(IdArea);
        NomCampo = jpa_formulario.ConsultaNombreCampos(CargoUsa);
        Object[] obj_camposNom = (Object[]) NomCampo.get(0);
        String HTMLeditor = "";
        String archivo = "";
        try {
            out.print("<div id='container'>");
            out.print("</br>");
            out.print("<i id='Menu_registro' class=\"fas fa-bars fa-lg\"></i>");
            out.print("<script>");
            out.print("$(Menu_registro).click(function() {");
            out.print("$(\"#toggleR\").toggle(\"slide\");");
            out.print("});");
            out.print("</script>");
            if (pageContext.getRequest().getAttribute("ActividadM") != null) {
                out.print("<div style='display:block;' id='toggleR'>");
                out.print("<div id='sidebar' style='border: 1px solid #666666;width:475px;padding: 20px 0 20px 10px;'>");
                // <editor-fold defaultstate="collapsed"  desc="Modificar actividad">
                MdcActividad = (List) pageContext.getRequest().getAttribute("ActividadM");
                String filtro = (String) pageContext.getRequest().getAttribute("filtro");
                Object[] obj_Mactividad = (Object[]) MdcActividad.get(0);
                out.print("<h3>Modificar actividad </h3>");
                out.print("<form action='AdjuntosM.jsp' method='post' enctype='multipart/form-data' name='form1' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<input type='hidden' name='idA' value='" + obj_Mactividad[0] + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<input type='hidden' name='Mtxt_registro' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<table class='table' style='width:100%; margin: 0;'>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<b>Hora: </b><br />");
                out.print("<input type='time' name='Mtmhora' id='Mtmhora_id' readonly='true' value='" + obj_Mactividad[6] + "'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Mtmhora_id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("</td>");
                out.print("<td>");
                out.print("<b>Fecha: </b><br />");
                out.print("<input id='Mfecha-id' type='text' name='Mtxtfecha' class='required input_field' value='" + obj_Mactividad[5] + "' readonly='true'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Mfecha-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<b>Turno: </b><br />");
                out.print("<select name='Mslc_turno' id='Mturno-id' class='input_full' >");
                out.print("<option style='display:none;' value='" + obj_Mactividad[7] + "' >Turno " + obj_Mactividad[7] + "</option>");
                out.print("<option value='1'>TURNO 1</option>");
                out.print("<option value='2'>TURNO 2</option>");
                out.print("<option value='3'>TURNO 3</option>");
                out.print("<option value='1/12'>TURNO 1/12</option>");
                out.print("<option value='2/12'>TURNO 2/12</option>");
                out.print("<option value='3/12'>TURNO 3/12</option>");
                out.print("<option value='OFICINA'>TURNO OFICINA</option>");
                out.print("</select>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Mturno-id');");
                out.print(" validation.add(Validate.Presence);");
                out.print("</script>");
                out.print("</td>");
                out.print("<td align='center'>");
                out.print("<b>Registra novedades <br /> de maquina: </b>");
                if (obj_Mactividad[11].equals(0)) {
                    out.print("<input type='radio' name='rdo_tnovM' value='1'>SI");
                    out.print("<input type='radio' name='rdo_tnovM' value='0' checked='checked'>NO");
                } else {
                    out.print("<input type='radio' name='rdo_tnovM' value='1' checked='checked'>SI");
                    out.print("<input type='radio' name='rdo_tnovM' value='0'>NO");
                }
                out.print("</td>");
                out.print("</tr>");
                Consultaform = jpa_formulario.ConsultaFormularioPorCargoActivo(CargoUsa);
                for (int i = 0; i < Consultaform.size(); i++) {
                    Object[] obj_formulario = (Object[]) Consultaform.get(i);
                    if (obj_formulario[8].equals(1)) {
                        String[] arg_nameId = obj_formulario[4].toString().split(" ");
                        String[] arg_datos = obj_formulario[6].toString().split("-");
                        if (Integer.parseInt(obj_Mactividad[22].toString()) == 30) {
                            out.print("" + ((i == (Consultaform.size() - 1)) ? ((obj_formulario[5].equals("Campo editorTexto") || obj_formulario[5].equals("Campo archivo")) ? "" : "<td colspan='2'>") : ((i == 0 || i == 2 || i == 4 || i == 6) ? "<tr><td colspan='2'>" : "<td colspan='2'>")) + "");
                        } else {
                            out.print("" + ((i == (Consultaform.size() - 1)) ? ((obj_formulario[5].equals("Campo editorTexto") || obj_formulario[5].equals("Campo archivo")) ? "" : "<td>") : ((i == 0 || i == 2 || i == 4 || i == 6) ? "<tr><td>" : "<td>")) + "");
                        }
                        if (contM == 1) {
                            // <editor-fold defaultstate="collapsed"  desc="campo1">
                            if (obj_formulario[5].equals("Campo hora")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='time' name='rd_" + arg_nameId[0] + "' id='" + arg_nameId[0] + "_id' value='" + obj_Mactividad[12] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo fecha")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input id='fecha" + (i + 1) + "' type='text' name='dtp_" + arg_nameId[0] + "' value='" + obj_Mactividad[12] + "' class='required input_field' placeholder='Seleccionar " + obj_formulario[4] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('fecha" + (i + 1) + "');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                out.print("<script type='text/javascript'>");
                                out.print("$(function() { $( '#fecha" + (i + 1) + "' ).datepicker({ altFormat: 'yy, d MM, DD' }); });");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo texto")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='text' name='Mtxt_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id' placeholder='" + obj_formulario[4] + "' value='" + obj_Mactividad[12] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo detallado")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<textarea id='M" + arg_nameId[0] + "_id' name='Mtext_" + arg_nameId[0] + "' class='input_full' rows='5'  placeholder='" + obj_formulario[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_Mactividad[12] + "</textarea><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo seleccion")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "<br />");
                                    } else {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "");
                                    }

                                }
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo lista")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<select name='Mslc_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id'>");
                                out.print("<option style='display:none;' value='" + obj_Mactividad[12] + "'>" + obj_Mactividad[12] + "</option>");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    } else {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    }
                                }
                                out.print("</select><br /><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo archivo")) {
                                archivo = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<div class='fileUpload btn btn-primary'>"
                                        + "<input type='file' class='upload' id='uploadBtn' name='Marchivo' >"
                                        + "<span>Cargar</span>"
                                        + "</div>"
                                        + "<p style='display:inline'></p>"
                                        + "<input id='uploadFile' placeholder='No ha seleccionado ningun archivo' disabled='disabled' /><br />"
                                        + "</p>";
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo editorTexto")) {
                                HTMLeditor = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<textarea id='editor1' name='Mtext_" + arg_nameId[0] + "' class='input_full' placeholder='" + obj_formulario[4] + "'> " + obj_Mactividad[16].toString().replace("<div>", "<div contenteditable='true'>") + " </textarea><br />";
                            } else if (obj_formulario[5].equals("Campo fechaRango")) {
                                out.println("<b> " + obj_formulario[4] + " </b>");
                                out.println("<div><input type='text' id='" + obj_formulario[6] + "' name='" + (arg_nameId[0] + i) + "' value='" + obj_Mactividad[12] + "' autocomplete='off' placeholder='AAAA-MM-DD' required></div>");
                                out.println("</div>");
                                out.println("<div>");
                                contM = contM + 1;
                            }
                            // </editor-fold>
                        } else if (contM == 2) {
                            // <editor-fold defaultstate="collapsed"  desc="campo2">
                            if (obj_formulario[5].equals("Campo hora")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='time' name='rd_" + arg_nameId[0] + "' id='" + arg_nameId[0] + "_id' value='" + obj_Mactividad[13] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo fecha")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input id='fecha" + (i + 1) + "' type='text' name='dtp_" + arg_nameId[0] + "' value='" + obj_Mactividad[13] + "' class='required input_field' placeholder='Seleccionar " + obj_formulario[4] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('fecha" + (i + 1) + "');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                out.print("<script type='text/javascript'>");
                                out.print("$(function() { $( '#fecha" + (i + 1) + "' ).datepicker({ altFormat: 'yy, d MM, DD' }); });");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo texto")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='text' name='Mtxt_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id' placeholder='" + obj_formulario[4] + "' value='" + obj_Mactividad[13] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo detallado")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<textarea id='M" + arg_nameId[0] + "_id' name='Mtext_" + arg_nameId[0] + "' class='input_full' rows='5'  placeholder='" + obj_formulario[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_Mactividad[13] + "</textarea><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo seleccion")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "<br />");
                                    } else {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "");
                                    }

                                }
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo lista")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<select name='Mslc_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id'>");
                                out.print("<option style='display:none;' value='" + obj_Mactividad[13] + "'>" + obj_Mactividad[13] + "</option>");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    } else {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    }
                                }
                                out.print("</select><br /><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo archivo")) {
                                archivo = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<div class='fileUpload btn btn-primary'>"
                                        + "<input type='file' class='upload' id='uploadBtn' name='Marchivo' >"
                                        + "<span>Cargar</span>"
                                        + "</div>"
                                        + "<p style='display:inline'></p>"
                                        + "<input id='uploadFile' placeholder='No ha seleccionado ningun archivo' disabled='disabled' /><br />"
                                        + "</p>";
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo editorTexto")) {
                                if (Integer.parseInt(obj_Mactividad[22].toString()) == 30) {
                                    HTMLeditor = "<b>" + obj_formulario[4] + ": </b><br />"
                                            + "<textarea id='editor1' name='Mtext_" + arg_nameId[0] + "' class='input_full' placeholder='" + obj_formulario[4] + "'> " + obj_Mactividad[13].toString().replace("<div>", "<div contenteditable='true'>") + " </textarea><br />";
                                } else {
                                    HTMLeditor = "<b>" + obj_formulario[4] + ": </b><br />"
                                            + "<textarea id='editor1' name='Mtext_" + arg_nameId[0] + "' class='input_full' placeholder='" + obj_formulario[4] + "'> " + obj_Mactividad[16].toString().replace("<div>", "<div contenteditable='true'>") + " </textarea><br />";
                                }
                            } else if (obj_formulario[5].equals("Campo fechaRango")) {
                                out.println("<b> " + obj_formulario[4] + " </b>");
                                out.println("<div><input type='text' id='" + obj_formulario[6] + "' name='" + (arg_nameId[0] + i) + "' value='" + obj_Mactividad[13] + "' autocomplete='off' placeholder='AAAA-MM-DD' required></div>");
                                out.println("</div>");
                                out.println("<div>");
                                contM = contM + 1;
                            }
                            // </editor-fold>
                        } else if (contM == 3) {
                            // <editor-fold defaultstate="collapsed"  desc="campo3">
                            if (obj_formulario[5].equals("Campo hora")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='time' name='rd_" + arg_nameId[0] + "' id='" + arg_nameId[0] + "_id' value='" + obj_Mactividad[14] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo fecha")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input id='fecha" + (i + 1) + "' type='text' name='dtp_" + arg_nameId[0] + "' value='" + obj_Mactividad[14] + "' class='required input_field' placeholder='Seleccionar " + obj_formulario[4] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('fecha" + (i + 1) + "');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                out.print("<script type='text/javascript'>");
                                out.print("$(function() { $( '#fecha" + (i + 1) + "' ).datepicker({ altFormat: 'yy, d MM, DD' }); });");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo texto")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='text' name='Mtxt_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id' placeholder='" + obj_formulario[4] + "' value='" + obj_Mactividad[14] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo detallado")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<textarea id='M" + arg_nameId[0] + "_id' name='Mtext_" + arg_nameId[0] + "' class='input_full' rows='5'  placeholder='" + obj_formulario[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_Mactividad[14] + "</textarea><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo seleccion")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "<br />");
                                    } else {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "");
                                    }

                                }
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo lista")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<select name='Mslc_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id'>");
                                out.print("<option style='display:none;' value='" + obj_Mactividad[14] + "'>" + obj_Mactividad[14] + "</option>");
                                if (Integer.parseInt(obj_formulario[1].toString()) == 8) {
                                    lst_maquina = jpa_maquina.ConsultaMaquinasPorArea(IdArea);
                                    for (int j = 0; j < lst_maquina.size(); j++) {
                                        Object[] obj_maquina = (Object[]) lst_maquina.get(j);
                                        out.print("<option>" + obj_maquina[3] + "</option>");
                                    }
                                } else {
                                    for (int j = 0; j < arg_datos.length; j++) {
                                        if (j == arg_datos.length - 1) {
                                            out.print("<option>" + arg_datos[j] + "</option>");
                                        } else {
                                            out.print("<option>" + arg_datos[j] + "</option>");
                                        }
                                    }
                                }
                                out.print("</select><br /><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo archivo")) {
                                archivo = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<div class='fileUpload btn btn-primary'>"
                                        + "<input type='file' class='upload' id='uploadBtn' name='Marchivo' >"
                                        + "<span>Cargar</span>"
                                        + "</div>"
                                        + "<p style='display:inline'></p>"
                                        + "<input id='uploadFile' placeholder='No ha seleccionado ningun archivo' disabled='disabled' /><br />"
                                        + "</p>";
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo editorTexto")) {
                                HTMLeditor = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<textarea id='editor1' name='Mtext_" + arg_nameId[0] + "' class='input_full' placeholder='" + obj_formulario[4] + "'> " + obj_Mactividad[14].toString().replace("<div>", "<div contenteditable='true'>") + " </textarea><br />";
                            } else if (obj_formulario[5].equals("Campo fechaRango")) {
                                out.println("<b> " + obj_formulario[4] + " </b>");
                                out.println("<div><input type='text' id='" + obj_formulario[6] + "' name='" + (arg_nameId[0] + i) + "' value='" + obj_Mactividad[12] + "' autocomplete='off' placeholder='AAAA-MM-DD' required></div>");
                                out.println("</div>");
                                out.println("<div>");
                                contM = contM + 1;
                            }
                            // </editor-fold>
                        } else if (contM == 4) {
                            // <editor-fold defaultstate="collapsed"  desc="campo4">
                            if (obj_formulario[5].equals("Campo hora")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='time' name='rd_" + arg_nameId[0] + "' id='" + arg_nameId[0] + "_id' value='" + obj_Mactividad[15] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo fecha")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input id='fecha" + (i + 1) + "' type='text' name='dtp_" + arg_nameId[0] + "' value='" + obj_Mactividad[15] + "' class='required input_field' placeholder='Seleccionar " + obj_formulario[4] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('fecha" + (i + 1) + "');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                out.print("<script type='text/javascript'>");
                                out.print("$(function() { $( '#fecha" + (i + 1) + "' ).datepicker({ altFormat: 'yy, d MM, DD' }); });");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo texto")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='text' name='Mtxt_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id' placeholder='" + obj_formulario[4] + "' value='" + obj_Mactividad[15] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo detallado")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<textarea id='M" + arg_nameId[0] + "_id' name='Mtext_" + arg_nameId[0] + "' class='input_full' rows='5'  placeholder='" + obj_formulario[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_Mactividad[15] + "</textarea><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo seleccion")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "<br />");
                                    } else {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "");
                                    }

                                }
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo lista")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<select name='Mslc_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id'>");
                                out.print("<option style='display:none;' value='" + obj_Mactividad[15] + "'>" + obj_Mactividad[15] + "</option>");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    } else {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    }
                                }
                                out.print("</select><br /><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo archivo")) {
                                archivo = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<div class='fileUpload btn btn-primary'>"
                                        + "<input type='file' class='upload' id='uploadBtn' name='Marchivo' >"
                                        + "<span>Cargar</span>"
                                        + "</div>"
                                        + "<p style='display:inline'></p>"
                                        + "<input id='uploadFile' placeholder='No ha seleccionado ningun archivo' disabled='disabled' /><br />"
                                        + "</p>";
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo editorTexto")) {
                                if (obj_Mactividad[3].toString().contains("ENCARGADA PRF")) {
                                    HTMLeditor = "<b>" + obj_formulario[4] + ": </b><br />"
                                            + "<textarea id='editor1' name='Mtext_" + arg_nameId[0] + "' class='input_full' placeholder='" + obj_formulario[4] + "'> " + obj_Mactividad[15].toString().replace("<div>", "<div contenteditable='true'>") + " </textarea><br />";
                                } else {
                                    HTMLeditor = "<b>" + obj_formulario[4] + ": </b><br />"
                                            + "<textarea id='editor1' name='Mtext_" + arg_nameId[0] + "' class='input_full' placeholder='" + obj_formulario[4] + "'> " + obj_Mactividad[16].toString().replace("<div>", "<div contenteditable='true'>") + " </textarea><br />";
                                }
                            } else if (obj_formulario[5].equals("Campo fechaRango")) {
                                out.println("<b> " + obj_formulario[4] + " </b>");
                                out.println("<div><input type='text' id='" + obj_formulario[6] + "' name='" + (arg_nameId[0] + i) + "' value='" + obj_Mactividad[12] + "' autocomplete='off' placeholder='AAAA-MM-DD' required></div>");
                                out.println("</div>");
                                out.println("<div>");
                                contM = contM + 1;
                            }
                            // </editor-fold>
                        } else if (contM == 5) {
                            // <editor-fold defaultstate="collapsed"  desc="campo5">
                            if (obj_formulario[5].equals("Campo hora")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='time' name='rd_" + arg_nameId[0] + "' id='" + arg_nameId[0] + "_id' value='" + obj_Mactividad[16] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo fecha")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input id='fecha" + (i + 1) + "' type='text' name='dtp_" + arg_nameId[0] + "' value='" + obj_Mactividad[16] + "' class='required input_field' placeholder='Seleccionar " + obj_formulario[4] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('fecha" + (i + 1) + "');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                out.print("<script type='text/javascript'>");
                                out.print("$(function() { $( '#fecha" + (i + 1) + "' ).datepicker({ altFormat: 'yy, d MM, DD' }); });");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo texto")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='text' name='Mtxt_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id' placeholder='" + obj_formulario[4] + "' value='" + obj_Mactividad[16] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo detallado")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<textarea id='M" + arg_nameId[0] + "_id' name='Mtext_" + arg_nameId[0] + "' class='input_full' rows='5'  placeholder='" + obj_formulario[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_Mactividad[16] + "</textarea><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo seleccion")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "<br />");
                                    } else {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "");
                                    }

                                }
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo lista")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<select name='Mslc_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id'>");
                                out.print("<option style='display:none;' value='" + obj_Mactividad[16] + "'>" + obj_Mactividad[16] + "</option>");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    } else {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    }
                                }
                                out.print("</select><br /><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo archivo")) {
                                archivo = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<div class='fileUpload btn btn-primary'>"
                                        + "<input type='file' class='upload' id='uploadBtn' name='Marchivo' >"
                                        + "<span>Cargar</span>"
                                        + "</div>"
                                        + "<p style='display:inline'></p>"
                                        + "<input id='uploadFile' placeholder='No ha seleccionado ningun archivo' disabled='disabled' /><br />"
                                        + "</p>";
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo editorTexto")) {
                                HTMLeditor = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<textarea id='editor1' name='Mtext_" + arg_nameId[0] + "' class='input_full' placeholder='" + obj_formulario[4] + "'> " + obj_Mactividad[16].toString().replace("<div>", "<div contenteditable='true'>") + " </textarea><br />";

                            } else if (obj_formulario[5].equals("Campo fechaRango")) {
                                out.println("<b> " + obj_formulario[4] + " </b>");
                                out.println("<div><input type='text' id='" + obj_formulario[6] + "' name='" + (arg_nameId[0] + i) + "' value='" + obj_Mactividad[12] + "' autocomplete='off' placeholder='AAAA-MM-DD' required></div>");
                                out.println("</div>");
                                out.println("<div>");
                                contM = contM + 1;
                            }
                            // </editor-fold>
                        } else if (contM == 6) {
                            // <editor-fold defaultstate="collapsed"  desc="campo6">
                            if (obj_formulario[5].equals("Campo hora")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='time' name='rd_" + arg_nameId[0] + "' id='" + arg_nameId[0] + "_id' value='" + obj_Mactividad[17] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo fecha")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input id='fecha" + (i + 1) + "' type='text' name='dtp_" + arg_nameId[0] + "' value='" + obj_Mactividad[17] + "' class='required input_field' placeholder='Seleccionar " + obj_formulario[4] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('fecha" + (i + 1) + "');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                out.print("<script type='text/javascript'>");
                                out.print("$(function() { $( '#fecha" + (i + 1) + "' ).datepicker({ altFormat: 'yy, d MM, DD' }); });");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo texto")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='text' name='Mtxt_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id' placeholder='" + obj_formulario[4] + "' value='" + obj_Mactividad[17] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo detallado")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<textarea id='M" + arg_nameId[0] + "_id' name='Mtext_" + arg_nameId[0] + "' class='input_full' rows='5'  placeholder='" + obj_formulario[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_Mactividad[17] + "</textarea><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo seleccion")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "<br />");
                                    } else {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "");
                                    }

                                }
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo lista")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<select name='Mslc_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id'>");
                                out.print("<option style='display:none;' value='" + obj_Mactividad[17] + "'>" + obj_Mactividad[17] + "</option>");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    } else {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    }
                                }
                                out.print("</select><br /><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo archivo")) {
                                archivo = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<div class='fileUpload btn btn-primary'>"
                                        + "<input type='file' class='upload' id='uploadBtn' name='Marchivo' >"
                                        + "<span>Cargar</span>"
                                        + "</div>"
                                        + "<p style='display:inline'></p>"
                                        + "<input id='uploadFile' placeholder='No ha seleccionado ningun archivo' disabled='disabled' /><br />"
                                        + "</p>";
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo editorTexto")) {
                                HTMLeditor = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<textarea id='editor1' name='Mtext_" + arg_nameId[0] + "' class='input_full' placeholder='" + obj_formulario[4] + "'> " + obj_Mactividad[16].toString().replace("<div>", "<div contenteditable='true'>") + " </textarea><br />";
                            } else if (obj_formulario[5].equals("Campo fechaRango")) {
                                out.println("<b> " + obj_formulario[4] + " </b>");
                                out.println("<div><input type='text' id='" + obj_formulario[6] + "' name='" + (arg_nameId[0] + i) + "' value='" + obj_Mactividad[12] + "' autocomplete='off' placeholder='AAAA-MM-DD' required></div>");
                                out.println("</div>");
                                out.println("<div>");
                                contM = contM + 1;
                            }
                            // </editor-fold>
                        } else if (contM == 7) {
                            // <editor-fold defaultstate="collapsed"  desc="campo7">
                            if (obj_formulario[5].equals("Campo hora")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='time' name='rd_" + arg_nameId[0] + "' id='" + arg_nameId[0] + "_id' value='" + obj_Mactividad[18] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo fecha")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input id='fecha" + (i + 1) + "' type='text' name='dtp_" + arg_nameId[0] + "' value='" + obj_Mactividad[18] + "' class='required input_field' placeholder='Seleccionar " + obj_formulario[4] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('fecha" + (i + 1) + "');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                out.print("<script type='text/javascript'>");
                                out.print("$(function() { $( '#fecha" + (i + 1) + "' ).datepicker({ altFormat: 'yy, d MM, DD' }); });");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo texto")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='text' name='Mtxt_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id' placeholder='" + obj_formulario[4] + "' value='" + obj_Mactividad[18] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo detallado")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<textarea id='M" + arg_nameId[0] + "_id' name='Mtext_" + arg_nameId[0] + "' class='input_full' rows='5'  placeholder='" + obj_formulario[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_Mactividad[18] + "</textarea><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo seleccion")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "<br />");
                                    } else {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "");
                                    }

                                }
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo lista")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<select name='Mslc_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id'>");
                                out.print("<option style='display:none;' value='" + obj_Mactividad[18] + "'>" + obj_Mactividad[18] + "</option>");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    } else {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    }
                                }
                                out.print("</select><br /><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo archivo")) {
                                archivo = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<div class='fileUpload btn btn-primary'>"
                                        + "<input type='file' class='upload' id='uploadBtn' name='Marchivo' >"
                                        + "<span>Cargar</span>"
                                        + "</div>"
                                        + "<p style='display:inline'></p>"
                                        + "<input id='uploadFile' placeholder='No ha seleccionado ningun archivo' disabled='disabled' /><br />"
                                        + "</p>";
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo editorTexto")) {
                                HTMLeditor = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<textarea id='editor1' name='Mtext_" + arg_nameId[0] + "' class='input_full' placeholder='" + obj_formulario[4] + "'> " + obj_Mactividad[16].toString().replace("<div>", "<div contenteditable='true'>") + " </textarea><br />";
                            } else if (obj_formulario[5].equals("Campo fechaRango")) {
                                out.println("<b> " + obj_formulario[4] + " </b>");
                                out.println("<div><input type='text' id='" + obj_formulario[6] + "' name='" + (arg_nameId[0] + i) + "' value='" + obj_Mactividad[12] + "' autocomplete='off' placeholder='AAAA-MM-DD' required></div>");
                                out.println("</div>");
                                out.println("<div>");
                                contM = contM + 1;
                            }
                            // </editor-fold>
                        } else if (contM == 8) {
                            // <editor-fold defaultstate="collapsed"  desc="campo8">
                            if (obj_formulario[5].equals("Campo hora")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='time' name='rd_" + arg_nameId[0] + "' id='" + arg_nameId[0] + "_id' value='" + obj_Mactividad[19] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo fecha")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input id='fecha" + (i + 1) + "' type='text' name='dtp_" + arg_nameId[0] + "' value='" + obj_Mactividad[19] + "' class='required input_field' placeholder='Seleccionar " + obj_formulario[4] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('fecha" + (i + 1) + "');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                out.print("<script type='text/javascript'>");
                                out.print("$(function() { $( '#fecha" + (i + 1) + "' ).datepicker({ altFormat: 'yy, d MM, DD' }); });");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo texto")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='text' name='Mtxt_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id' placeholder='" + obj_formulario[4] + "' value='" + obj_Mactividad[19] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo detallado")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<textarea id='M" + arg_nameId[0] + "_id' name='Mtext_" + arg_nameId[0] + "' class='input_full' rows='5'  placeholder='" + obj_formulario[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_Mactividad[19] + "</textarea><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo seleccion")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "<br />");
                                    } else {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "");
                                    }

                                }
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo lista")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<select name='Mslc_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id'>");
                                out.print("<option style='display:none;' value='" + obj_Mactividad[19] + "'>" + obj_Mactividad[19] + "</option>");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    } else {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    }
                                }
                                out.print("</select><br /><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo archivo")) {
                                archivo = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<div class='fileUpload btn btn-primary'>"
                                        + "<input type='file' class='upload' id='uploadBtn' name='Marchivo' >"
                                        + "<span>Cargar</span>"
                                        + "</div>"
                                        + "<p style='display:inline'></p>"
                                        + "<input id='uploadFile' placeholder='No ha seleccionado ningun archivo' disabled='disabled' /><br />"
                                        + "</p>";
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo editorTexto")) {
                                HTMLeditor = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<textarea id='editor1' name='Mtext_" + arg_nameId[0] + "' class='input_full' placeholder='" + obj_formulario[4] + "'> " + obj_Mactividad[16].toString().replace("<div>", "<div contenteditable='true'>") + " </textarea><br />";
                            } else if (obj_formulario[5].equals("Campo fechaRango")) {
                                out.println("<b> " + obj_formulario[4] + " </b>");
                                out.println("<div><input type='text' id='" + obj_formulario[6] + "' name='" + (arg_nameId[0] + i) + "' value='" + obj_Mactividad[12] + "' autocomplete='off' placeholder='AAAA-MM-DD' required></div>");
                                out.println("</div>");
                                out.println("<div>");
                                contM = contM + 1;
                            }
                            // </editor-fold>
                        } else if (contM == 9) {
                            // <editor-fold defaultstate="collapsed"  desc="campo9">
                            if (obj_formulario[5].equals("Campo hora")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='time' name='rd_" + arg_nameId[0] + "' id='" + arg_nameId[0] + "_id' value='" + obj_Mactividad[20] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo fecha")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input id='fecha" + (i + 1) + "' type='text' name='dtp_" + arg_nameId[0] + "' value='" + obj_Mactividad[20] + "' class='required input_field' placeholder='Seleccionar " + obj_formulario[4] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('fecha" + (i + 1) + "');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                out.print("<script type='text/javascript'>");
                                out.print("$(function() { $( '#fecha" + (i + 1) + "' ).datepicker({ altFormat: 'yy, d MM, DD' }); });");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo texto")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input type='text' name='Mtxt_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id' placeholder='" + obj_formulario[4] + "' value='" + obj_Mactividad[20] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo detallado")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<textarea id='M" + arg_nameId[0] + "_id' name='Mtext_" + arg_nameId[0] + "' class='input_full' rows='5'  placeholder='" + obj_formulario[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_Mactividad[20] + "</textarea><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo seleccion")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "<br />");
                                    } else {
                                        out.print("<input type='radio' name='Mrdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "");
                                    }

                                }
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo lista")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<select name='Mslc_" + arg_nameId[0] + "' id='M" + arg_nameId[0] + "_id'>");
                                out.print("<option style='display:none;' value='" + obj_Mactividad[20] + "'>" + obj_Mactividad[20] + "</option>");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    } else {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    }
                                }
                                out.print("</select><br /><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('M" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo archivo")) {
                                archivo = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<div class='fileUpload btn btn-primary'>"
                                        + "<input type='file' class='upload' id='uploadBtn' name='Marchivo' >"
                                        + "<span>Cargar</span>"
                                        + "</div>"
                                        + "<p style='display:inline'></p>"
                                        + "<input id='uploadFile' placeholder='No ha seleccionado ningun archivo' disabled='disabled' /><br />"
                                        + "</p>";
                                contM = contM + 1;
                            } else if (obj_formulario[5].equals("Campo editorTexto")) {
                                HTMLeditor = "<b>" + obj_formulario[4] + ": </b><br />"
                                        + "<textarea id='editor1' name='Mtext_" + arg_nameId[0] + "' class='input_full' placeholder='" + obj_formulario[4] + "'> " + obj_Mactividad[16].toString().replace("<div>", "<div contenteditable='true'>") + " </textarea><br />";
                            } else if (obj_formulario[5].equals("Campo fechaRango")) {
                                out.println("<b> " + obj_formulario[4] + " </b>");
                                out.println("<div><input type='text' id='" + obj_formulario[6] + "' name='" + (arg_nameId[0] + i) + "' value='" + obj_Mactividad[12] + "' autocomplete='off' placeholder='AAAA-MM-DD' required></div>");
                                out.println("</div>");
                                out.println("<div>");
                                contM = contM + 1;
                            }
                            // </editor-fold>
                        }
                        out.print("" + ((i == (Consultaform.size() - 1)) ? ((obj_formulario[5].equals("Campo editorTexto") || obj_formulario[5].equals("Campo archivo")) ? "" : "</td>") : ((i == 0 || i == 2 || i == 4 || i == 6) ? "</td>" : "</td></tr>")) + "");
                    } else {
                    }
                }
                if (!HTMLeditor.equals("")) {
                    out.print("<tr>");
                    out.print("<td colspan='2'>" + HTMLeditor + "</td>");
                    out.print("</tr>");
                }
                if (!archivo.equals("")) {
                    out.print("<tr>");
                    out.print("<td colspan='2'>" + archivo + "");
                    out.print("<script type='text/javascript'>");
                    out.print("document.getElementById('uploadBtn').onchange = function () {");
                    out.print("document.getElementById('uploadFile').value = this.value;};");
                    out.print("</script></td>");
                    out.print("</tr>");
                }
                out.print("<tr>");
                out.print("<td colspan='2' align='center'><input type='submit' value='Modificar'></td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</form>");
//                 </editor-fold>
                out.print("<div class='cleaner'></div></div>");
            } else {
                //<editor-fold defaultstate="collapsed" desc="registro actividad">
                out.print("<div style='display:none;' id='toggleR'>");
                out.print("<div id='sidebar' style='border: 1px solid #666666;width:474px;padding: 20px 0 20px 10px;'>");
                out.print("<h3>Nueva actividad </h3>");
                out.print("<form action='Adjuntos.jsp' method='post' enctype='multipart/form-data' onsubmit='checkSubmit();' name='form1' >");
                out.print("<input type='hidden' name='IdU' value='" + IdUsuario + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<input type='hidden' name='idC' value='" + CargoUsa + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<input type='hidden' name='txt_registro' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<input type='hidden' name='txt_consecutivo' value='" + obj_conse[0] + "'>");
                out.print("<input type='hidden' name='Cont' value='" + cont + "'>");
                out.print("<input type='hidden' name='Nom_campos' value='" + obj_camposNom[2] + "'>");
                out.print("<input type='hidden' name='tmhora' id='tmhora_id' readonly='true'>");
                out.print("<input type='hidden' name='txtfecha' id='fecha-id' readonly='true'>");
                out.print("<table class='table' style='width:100%; margin: 0;'>");
//                out.print("<tr>");
//                out.print("<td>");
//                out.print("<b>Hora: </b><br />");
//                out.print("<script type='text/javascript'>");
//                out.print("var validation = new LiveValidation('tmhora_id');");
//                out.print("validation.add( Validate.Presence );");
//                out.print("</script>");
//                out.print("</td>");
//                out.print("<td>");
//                out.print("<b>Fecha: </b><br />");
//                out.print("<script type='text/javascript'>");
//                out.print("var validation = new LiveValidation('fecha-id');");
//                out.print("validation.add( Validate.Presence );");
//                out.print("</script>");
//                out.print("</td>");
//                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                // CAMPOS DEL FORMULARIO
                out.print("<b>Turno: </b><br />"); // CAMPO TURNO
                out.print("<select name='slc_turno' id='turno-id' class='input_full' >");
                out.print("<option style='display:none;' value=''>Seleccione turno</option>");
                out.print("<option value='1'>TURNO 1</option>");
                out.print("<option value='2'>TURNO 2</option>");
                out.print("<option value='3'>TURNO 3</option>");
                out.print("<option value='1/12'>TURNO 1/12</option>");
                out.print("<option value='2/12'>TURNO 2/12</option>");
                out.print("<option value='3/12'>TURNO 3/12</option>");
                out.print("<option value='OFICINA'>TURNO OFICINA</option>");
                out.print("</select>");
                // SCRIPT VALIDA EL CAMPO TURNO
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('turno-id');");
                out.print("validation.add(Validate.Presence);");
                out.print("</script>");
                out.print("</td>");
                out.print("<td align='center'>");
                out.print("<b>Registra novedades <br /> de maquina: </b>"); // CAMPO NOVEDAD MAQUINA
                out.print("<input type='radio' name='rdo_tnov' value='1'>SI");
                out.print("<input type='radio' name='rdo_tnov' value='0' checked='checked'>NO");
                out.print("</td>");
                out.print("</tr>");
                Consultaform = jpa_formulario.ConsultaFormularioPorCargoActivo(CargoUsa);

                for (int i = 0; i < Consultaform.size(); i++) {
                    Object[] obj_formulario = (Object[]) Consultaform.get(i);
                    if (obj_formulario[8].equals(1)) {
                        String[] arg_nameId = obj_formulario[4].toString().split(" ");
                        String[] arg_datos = obj_formulario[6].toString().split("-");
                        if (Integer.parseInt(obj_camposNom[3].toString()) == 30) {
                            out.print("" + ((i == (Consultaform.size() - 1)) ? ((obj_formulario[5].equals("Campo editorTexto") || obj_formulario[5].equals("Campo archivo")) ? "" : "<td colspan='2'>") : ((i == 0 || i == 2 || i == 4 || i == 6) ? "<tr><td colspan='2'>" : "<td colspan='2'>")) + "");
                        } else {
                            out.print("" + ((i == (Consultaform.size() - 1)) ? ((obj_formulario[5].equals("Campo editorTexto") || obj_formulario[5].equals("Campo archivo")) ? "" : "<td>") : ((i == 0 || i == 2 || i == 4 || i == 6) ? "<tr><td>" : "<td>")) + "");
                        }
                        if (obj_formulario[5].equals("Campo hora")) {
                            //<editor-fold defaultstate="collapsed" desc="CAMPO HORA">
                            out.print("<b>" + obj_formulario[4] + ": </b><br />");
                            out.print("<input type='time' name='rd_" + arg_nameId[0] + "" + i + "' id='" + arg_nameId[0] + "" + i + "_id'><br />");
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('" + arg_nameId[0] + "" + i + "_id');");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script>");
                            //</editor-fold>
                        } else if (obj_formulario[5].equals("Campo fecha")) {
                            //<editor-fold defaultstate="collapsed" desc="CAMPO FECHA">
                            out.print("<b>" + obj_formulario[4] + ": </b><br />");
                            out.print("<input id='datepicker" + (i + 1) + "' type='text' name='dtp_" + arg_nameId[0] + "" + i + "' class='required input_field' placeholder='Seleccionar " + obj_formulario[4] + "' autocomplete='off'><br />");
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('datepicker" + (i + 1) + "');");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script>");
                            out.print("<script type='text/javascript'>");
                            out.print("$(function() { $( '#fecha" + (i + 1) + "' ).datepicker({ altFormat: 'yy, d MM, DD' }); });");
                            out.print("</script>");
                            //</editor-fold>
                        } else if (obj_formulario[5].equals("Campo texto")) {
                            //<editor-fold defaultstate="collapsed" desc="CAMPO TEXTO">
                            out.print("<b>" + obj_formulario[4] + ": </b><br />");
                            out.print("<input type='text' name='txt_" + arg_nameId[0] + "" + i + "' id='" + arg_nameId[0] + "" + i + "_id' placeholder='" + obj_formulario[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('" + arg_nameId[0] + "" + i + "_id');");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script>");
                            //</editor-fold>
                        } else if (obj_formulario[5].equals("Campo detallado")) {
                            //<editor-fold defaultstate="collapsed" desc="CAMPO DETALLADO">
                            out.print("<b>" + obj_formulario[4] + ": </b><br />");
                            out.print("<textarea id='" + arg_nameId[0] + "" + i + "_id' name='text_" + arg_nameId[0] + "" + i + "' class='input_full' rows='5'  placeholder='" + obj_formulario[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'></textarea><br />");
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('" + arg_nameId[0] + "" + i + "_id');");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script>");
                            //</editor-fold>
                        } else if (obj_formulario[5].equals("Campo seleccion")) {
                            //<editor-fold defaultstate="collapsed" desc="CAMPO SELECCION">
                            out.print("<b>" + obj_formulario[4] + ": </b><br />");
                            for (int j = 0; j < arg_datos.length; j++) {
                                if (j == arg_datos.length - 1) {
                                    out.print("<input type='radio' name='rdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "<br />");
                                } else {
                                    out.print("<input type='radio' name='rdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "");
                                }
                            }
                            //</editor-fold>
                        } else if (obj_formulario[5].equals("Campo lista")) {
                            //<editor-fold defaultstate="collapsed" desc="CAMPO LISTA">
                            if (IdArea == 3) {
                                out.print("<b>Equipo:</b><br />");
                                out.print("<select name='slc_campo3' id='campo3_id' data-live-search='true'>");
                                out.print("<option value='' style='display:none;'>Seleccionar Equipos</option>");
                                lst_maquina = jpa_maquina.ConsultaMaquinasPorArea(IdArea);
                                for (int j = 0; j < lst_maquina.size(); j++) {
                                    Object[] obj_maquina = (Object[]) lst_maquina.get(j);
                                    out.print("<option>" + obj_maquina[3] + "</option>");
                                }
                                out.print("</select><br /><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('campo3_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                            } else {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<select name='slc_" + arg_nameId[0] + "' id='" + arg_nameId[0] + "_id'>");
                                out.print("<option style='display:none;'>Seleccionar Campos</option>");
                                for (int j = 0; j < arg_datos.length; j++) {
                                    if (j == arg_datos.length - 1) {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    } else {
                                        out.print("<option>" + arg_datos[j] + "</option>");
                                    }
                                }
                                out.print("</select><br /><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('" + arg_nameId[0] + "_id');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                            }
                            //</editor-fold>
                        } else if (obj_formulario[5].equals("Campo archivo")) {
                            //<editor-fold defaultstate="collapsed" desc="CAMPO ARCHIVO">
                            archivo = "<b>" + obj_formulario[4] + ": </b><br />"
                                    + "<div class='fileUpload btn btn-primary'>"
                                    + "<input type='file' class='upload' id='uploadBtn' name='archivo' >"
                                    + "<span>Cargar</span>"
                                    + "</div>"
                                    + "<p style='display:inline'></p>"
                                    + "<input id='uploadFile' placeholder='No ha seleccionado ningun archivo' disabled='disabled' /><br />"
                                    + "</p>";
                            //</editor-fold>
                        } else if (obj_formulario[5].equals("Campo editorTexto")) {
                            //<editor-fold defaultstate="collapsed" desc="CAMPO EDITOR TEXTO">
                            HTMLeditor = "<b>" + obj_formulario[4] + ": </b><br />"
                                    + "<textarea id='editor1' name='text_" + arg_nameId[0] + "' class='input_full' placeholder='" + obj_formulario[4] + "' style='width: 440px; height: 400px'></textarea><br />";
                            //</editor-fold>
                        } else if (obj_formulario[5].equals("Campo fechaRango")) {
                            //<editor-fold defaultstate="collapsed" desc="CAMPO FECHA RANGO">
                            out.println("<b> " + obj_formulario[4] + " </b>");
                            out.println("<div><input type='text' id='" + obj_formulario[6] + "' name='" + (arg_nameId[0] + i) + "' value='' autocomplete='off' placeholder='AAAA-MM-DD' required></div>");
                            out.println("</div>");
                            out.println("<div>");
                            //</editor-fold>
                        }
                        out.print("" + ((i == (Consultaform.size() - 1)) ? ((obj_formulario[5].equals("Campo editorTexto") || obj_formulario[5].equals("Campo archivo")) ? "" : "</td>") : ((i == 0 || i == 2 || i == 4 || i == 6) ? "</td>" : "</td></tr>")) + "");

                    } else {
                    }
                }
                if (!HTMLeditor.equals("")) {
                    out.print("<tr>");
                    out.print("<td colspan='2'>" + HTMLeditor + "</td>");
                    out.print("</tr>");
                }
                if (!archivo.equals("")) {
                    out.print("<tr>");
                    out.print("<td colspan='2'>" + archivo + "");
                    out.print("<script type='text/javascript'>");
                    out.print("document.getElementById('uploadBtn').onchange = function () {");
                    out.print("document.getElementById('uploadFile').value = this.value;};");
                    out.print("</script></td>");
                    out.print("</tr>");
                }
                out.print("<tr>");
                out.print("<td colspan='2' align='center'><input type='submit' id='btsubmit' value='Registrar'><br /></td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</form>");
                //</editor-fold>
                out.print("<div class='cleaner'></div></div>");
            }
            out.print("</div>");
            if (pageContext.getRequest().getAttribute("consultaActividad") != null) {
                //<editor-fold defaultstate="collapsed" desc="CONSULTA ACTIVIDAD">
                String filtro = (String) pageContext.getRequest().getAttribute("filtro");
                List Atividad = (List) pageContext.getRequest().getAttribute("consultaActividad");
                //<editor-fold defaultstate="collapsed" desc="FILTRO POR PERSONAL">
                out.print("<i id=\"Menu_filtro\" style='float:right;' class=\"fas fa-search fa-lg\"></i>");
                out.print("<script>");
                out.print("$(Menu_filtro).click(function() {");
                out.print("$(\"#toggleF\").toggle(\"slide\");");
                out.print("});");
                out.print("</script>");
                out.print("<script language='Javascript'>"
                        + "function mostrar() {"
                        + "var panel, mostrarr ;var pagina =''; panel = document.getElementById('RangoFecha');"
                        + "if (panel.style.visibility == 'hidden') {"
                        + "panel.style.visibility = 'visible';"
                        + "mostrarr = document.getElementById('mostrar');"
                        + "document.getElementById('cambiar').src='Interfaz/Contenido/Iconos/Min.png';"
                        + "document.getElementById('cambiar').title = 'Cancelar';"
                        + "}else {"
                        + "panel.style.visibility = 'hidden';"
                        + "mostrarr = document.getElementById('mostrar');"
                        + "document.getElementById('cambiar').src = 'Interfaz/Contenido/Iconos/Plus.png';"
                        + "document.getElementById('cambiar').title = 'Consulta actividades';"
                        + "}"
                        + "}</script>");
                out.print("<div style='display: flex; align-items: baseline;' id='toggleF'>");
                out.print("<div style='float:left' >");
                out.print("<a id='mostrarr' href='javascript:mostrar();'><i id='cambiar' class=\"fas fa-plus fa-lg\"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                out.print("</div>");
                if (obj_permisos[12].equals(1)) {
                    out.print("<form action='Actividad?op=1&idC=" + 0 + "&idA=" + 0 + "' name='formActividad' method='post' >");
                    out.print("<select name='idC' onchange=\"document.formActividad.action=\'Actividad?op=1&idA=" + 0 + "&txt_bus=" + filtro + "\';document.formActividad.submit();\">");
                    out.print("<option style='display:none;'>Seleccione un cargo</option>");
                    for (int i = 0; i < lst_cargos.size(); i++) {
                        Object[] obj_cargoC = (Object[]) lst_cargos.get(i);
                        if (obj_cargoC[8].equals(1)) {
                            out.print("<option value='" + obj_cargoC[0] + "'>" + obj_cargoC[5] + " / " + obj_cargoC[11] + "</option>");
                        } else {
                        }
                    }
                    out.print("</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                } else {
                    out.print("<form action='Actividad?op=1&idC=" + CargoUsa + "&idA=" + 0 + "' name='formActividad' method='post' >");
                    out.print("<select name='idU' onchange=\"document.formActividad.action=\'Actividad?op=1&idC=" + CargoUsa + "&idA=" + 0 + "&txt_bus=" + filtro + "\';document.formActividad.submit();\">");
                    out.print("<option style='display:none;'>Seleccione un usuario</option>");
                    for (int i = 0; i < lst_Usuarios.size(); i++) {
                        Object[] obj_UsaC = (Object[]) lst_Usuarios.get(i);
                        if (obj_UsaC[10].equals(1)) {
                            out.print("<option value='" + obj_UsaC[0] + "'>" + obj_UsaC[4] + " " + obj_UsaC[5] + "</option>");
                        } else {
                        }
                    }
                    Object[] obj_UsaC = (Object[]) lst_Usuarios.get(0);
                    out.print("<option value='" + obj_UsaC[1] + "'>Todos</option>");
                    out.print("</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                }
                out.print("<input type='text' name='txt_bus' aling='right' placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("</form>");
                out.print("</div>");
                //</editor-fold>
                if (obj_permisos[12].equals(1)) {
                    out.print("<a href='Actividad?op=1&idC=" + CargoUsa + "&idA=" + 0 + "&txt_bus=' style='margin: 10px;'><i class=\"fas fa-reply fa-lg\"></i></a>");
                }
                //<editor-fold defaultstate="collapsed" desc="FILTRO POR FECHAS">
                out.print("<fieldset class='resalta_field' id='RangoFecha' style='width: 200px;visibility: hidden;position: absolute;top: 191px;left: 41%;  '>");
                out.print("<legend>Consulta Actividades</legend>");
                if (obj_permisos[12].equals(1)) {
                    out.print("<form action='Actividad?op=7' method='post' name='formAtdArea' >");
                    out.print("<b>Cargo</b><br />");
                    out.print("<select name='idC'>");
                    out.print("<option style='display:none;' value='0'>Seleccione un cargo</option>");
                    for (int i = 0; i < lst_cargos.size(); i++) {
                        Object[] obj_cargo = (Object[]) lst_cargos.get(i);
                        out.print("<option value='" + obj_cargo[0] + "'>" + obj_cargo[5] + "</option>");
                    }
                    out.print("</select><br /><br />");
                } else {
                    out.print("<form action='Actividad?op=7&idC=" + CargoUsa + "' method='post' >");
                }
                out.print("<b>Maquina:</b><br />");
                out.print("<select name='idmaquina' id='idmaquina' data-live-search='true' required>");
                out.print("<option  style='display:none;'>Seleccionar Maquina</option>");
                lst_maquina = jpa_maquina.ConsultaMaquinasPorArea(IdArea);
                out.print("<option value='0'>Todos</option>");
                for (int j = 0; j < lst_maquina.size(); j++) {
                    Object[] obj_maquinas = (Object[]) lst_maquina.get(j);
                    out.print("<option value='" + obj_maquinas[0] + "'>" + obj_maquinas[3] + "</option>");
                }
                out.print("</select><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('idmaquina');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='hidden' name='horaI'  value=''>");
                out.print("<input type='hidden' name='horaF' value=''>");
                out.print("<b>Fecha inicio</b><input id='start' type='text' name='fch_inicio' class='required input_field'  placeholder='Seleccionar fecha' autocomplete='off'>");
                out.print("<span class=' LV_validation_message LV_valid'></span>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('start');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<script type='text/javascript'>var val1 = new LiveValidation('dtp_inicio');val1.add(Validate.Presence);</script>");
                out.print("<b>Fecha fin</b><input id='end' type='text' name='fch_fin' class='required input_field' placeholder='Seleccionar fecha' autocomplete='off' >");
                out.print("<span class=' LV_validation_message LV_valid'></span>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('end');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<script type='text/javascript'>var val1 = new LiveValidation('dtp_fin');val1.add(Validate.Presence);</script>");
                out.print("<tr><td colspan='2'>Despues de escribir una palabra se debe agregar el (<b class='rojo'>+</b>).<br>Para quitar la palabra se da click encima encima de la palabra.<br>"
                        + "<br><input type='text' name='Txt_filtro_avanzado' id='Txt_filtro_avanzado' autocomplete='off' onkeypress='FiltroAvanzado(event);' placeholder='Buscar'/>"
                        + "<br /><b>Valores a filtrar</b><div  style='overflow-y:scroll' id='Buscar_valores'></div>"
                        + "<input type='hidden' name='fto'  id='Txt_valores_filtro' oninput=\"javascript:this.value+=document.getElementById('Buscar_valores').innerHTML\"/></td></tr>");
                out.print("<hr><b style='color:red;'>PDT</b><input name='cbx_actividad' type='radio' value='2' required> | "
                        + "<b style='color:green;'>RVS</b><input name='cbx_actividad' type='radio' value='1' required> | "
                        + "<b style='color:black;'>N/A</b><input name='cbx_actividad' type='radio' value='0' required>");
                out.print("<br><br><input type='submit' value='Consultar' name='Consultar'><br />");
                out.print("</form>");
                out.print("</fieldset>");
                out.print("<script type='text/javascript'>");
                out.print("$(function() { $( '#datepicker' ).datepicker({ altField: '#alternate', altFormat: 'DD, d MM, yy' }); });");
                out.print("var validation = new LiveValidation('alternate');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                //</editor-fold>
                out.print("<h3 style='margin-top: 10px;'>Actividades registradas</h3>");
                if (Atividad == null || Atividad.isEmpty()) {
                    out.print("<h3 style='margin-top: 10px;'>No se encuentran actividades registradas<h3>");
                } else {
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA ACTIVIDADES">
                    out.print("<form action='Actividad?op=5' method='post' name='f1' id='f1'>");
                    out.print("<input type='hidden' name='ContActividad' value='" + Atividad.size() + "' />");
                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "' />");
                    if (obj_permisos[12].equals(1)) {
                        out.print("<div style='float: right;'><a href='javascript:seleccionar_todo()'>Marcar todos</a> | "
                                + "<a href='javascript:deseleccionar_todo()'>ninguno</a> "
                                + "<a href='#' onclick='document.f1.submit()'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='25' height='25.5' title='Revisar' /></a></div>");
                    }
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table id='resultadosT1' style=\"width:100%;\">");
                    out.print("<tr>");
                    out.print("<td colspan='10'></td>");
                    out.print("</tr>");
                    for (int e = 0; e < lst_Actividades.size(); e++) {
                        Object[] obj_actividades = (Object[]) lst_Actividades.get(e);
                        if (obj_permisos[12].equals(1)) {
                            out.print("<tr>");
                            out.print("<td><b>" + obj_actividades[5] + "</b></td>");
                            out.print("</tr>");
                        }
                        for (int p = 0; p < Atividad.size(); p++) {
                            Object[] obj_actividad = (Object[]) Atividad.get(p);
                            NomCampo = jpa_formulario.ConsultaNombreCamposPorIdArea((Integer) obj_actividades[0], (Integer) obj_actividad[0]);
                            Object[] obj_verCampos = (Object[]) NomCampo.get(0);
                            if (obj_verCampos[0] != null) {
                                String[] arg_campos = obj_verCampos[2].toString().split("-");
                                if (obj_actividad[22] == obj_actividades[0]) {
                                    out.print("<tr>");
                                    out.print("<td>");
                                    // <editor-fold defaultstate="collapsed"  desc="Consulta campos">
                                    if (obj_actividad[21].equals(2)) {
                                        // <editor-fold defaultstate="collapsed"  desc="Consulta 2 campos">
                                        out.print("<table class='table' id='resultados' style='width: 100%; margin: 0;'>");
                                        out.print("<tr>");
                                        out.print("<td colspan='8'></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<th rowspan='2' align='center' style='width:10%;'>" + obj_actividad[5] + "<br />" + obj_actividad[6] + "</th>");
                                        out.print("<td><b>Turno:</b>" + obj_actividad[7] + "</td>");
                                        out.print("<td><b>Responsable: </b>" + obj_actividad[3] + "</td>");
                                        out.print("<td><b>Consecutivo: </b>" + obj_actividad[2] + "</td>");
                                        if (obj_actividad[11].equals(1)) {
                                            if (obj_actividad[8].equals(1)) {
                                                out.print("<td rowspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                            } else if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td rowspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                            } else {
                                                out.print("<td rowspan='2' align='center'><a href='#'><i class=\"fas fa-ban fa-2x\"></i></a></td>");
                                            }
                                        } else if (obj_actividad[8].equals(1)) {
                                            out.print("<td rowspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                        } else {
                                            out.print("<td rowspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                        }
                                        if (obj_actividad[8].equals(0)) {
                                            if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=1&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&txt_bus=" + filtro + "'><i class=\"fas fa-pencil-alt fa-2x\"></i></a></td>");
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=4&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&cier=" + 1 + "&txt_bus=" + filtro + "'><i class=\"fas fa-lock-open fa-2x\"></i></a></td>");
                                            } else {
                                                out.print("<td rowspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                                out.print("<td rowspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            }
                                            out.print("<td rowspan='3' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                        } else {
//                                            out.print("<td rowspan='2' align='center'> <i class=\"fas fa-pencil-alt fa-2x\" style=\"color: #454545;\"></i> </td>");
                                            out.print("<td rowspan='2' colspan='2' align='center'> <i class=\"fas fa-lock fa-2x\"></i> </td>");
                                            if (obj_permisos[12].equals(1)) {
                                                if (obj_actividad[9] == null) {
                                                    out.print("<td rowspan='3' align='center'><input type='checkbox' name='checkboxes[" + p + "]' value='" + obj_actividad[0] + "' ></td>");
//                                                    out.print("<td rowspan='3' align='center'><a href='Actividad?op=5&idC=" + CargoUsa + "&idA=" + obj_actividad[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='25' height='25.5' title='Revisar'/></a></td>");
                                                } else {
                                                    String[] FechaR = obj_actividad[9].toString().split("/");
                                                    out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                                }
                                            } else if (obj_actividad[9] == null) {
                                                out.print("<td rowspan='3' align='center'><a href='#'><i class=\"fas fa-ban fa-2x\"></i></a></td>");
                                            } else {
                                                String[] FechaR = obj_actividad[9].toString().split("/");
                                                out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                            }
                                        }

                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>" + arg_campos[0] + ": </b>" + obj_actividad[12] + "</td>");
                                        out.print("<td><b>" + arg_campos[1] + ": </b>" + obj_actividad[13] + "</td>");
                                        if (obj_actividad[10].equals("null")) {
                                            out.print("<td align='center'><b>N/A adjuntos</b></td>");
                                        } else {
                                            out.print("<td><b>Adjuntos: </b><a href='Descargas?file_name=" + obj_actividad[10] + "&nomC=" + obj_permisos[5] + "&nomA=" + obj_permisos[10] + "'>" + obj_actividad[10] + "</a></td>");
                                        }
                                        out.print("</tr>");
                                        out.print("</table>");
                                        // </editor-fold>
                                    } else if (obj_actividad[21].equals(3)) {
                                        // <editor-fold defaultstate="collapsed"  desc="Consulta 3 campos">
                                        out.print("<table class='table' id='resultados' style='width: 100%; margin: 0;'>");
                                        out.print("<tr>");
                                        out.print("<td colspan='8'></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<th rowspan='3' align='center' style='width:10%;'>" + obj_actividad[5] + "<br />" + obj_actividad[6] + "</th>");
                                        out.print("<td><b>Turno: </b>" + obj_actividad[7] + "</td>");
                                        out.print("<td><b>Responsable: </b>" + obj_actividad[3] + "</td>");
                                        out.print("<td><b>Consecutivo: </b>" + obj_actividad[2] + "</td>");
                                        if (obj_actividad[8].equals(0)) {
                                            if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=1&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&txt_bus=" + filtro + "'><i class=\"fas fa-pencil-alt fa-2x\"></i></a></td>");
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=4&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&cier=" + 1 + "&txt_bus=" + filtro + "'><i class=\"fas fa-lock-open fa-2x\"></i></a></td>");
                                            } else {
                                                out.print("<td rowspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                                out.print("<td rowspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            }
                                            out.print("<td rowspan='3' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                        } else {
//                                            out.print("<td rowspan='2' align='center'> <i class=\"fas fa-pencil-alt fa-2x\" style=\"color: #454545;\"></i> </td>");
                                            out.print("<td rowspan='2' colspan='2' align='center'> <i class=\"fas fa-lock fa-2x\"></i> </td>");
                                            if (obj_permisos[12].equals(1)) {
                                                if (obj_actividad[9] == null) {
                                                    out.print("<td rowspan='3' align='center'><input type='checkbox' name='checkboxes[" + p + "]' value='" + obj_actividad[0] + "'  ></td>");
//                                                    out.print("<td rowspan='3' align='center'><a href='Actividad?op=5&idC=" + CargoUsa + "&idA=" + obj_actividad[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='25' height='25.5' title='Revisar'/></a></td>");
                                                } else {
                                                    String[] FechaR = obj_actividad[9].toString().split("/");
                                                    out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                                }
                                            } else if (obj_actividad[9] == null) {
                                                out.print("<td rowspan='3' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            } else {
                                                String[] FechaR = obj_actividad[9].toString().split("/");
                                                out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                            }
                                        }
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>" + arg_campos[0] + ": </b>" + obj_actividad[12] + "</td>");
                                        out.print("<td colspan='2'><b>" + arg_campos[1] + ": </b>" + obj_actividad[13] + "</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td colspan='2'><b>" + arg_campos[2] + ": </b>" + obj_actividad[14] + "</td>");
                                        if (obj_actividad[10].equals("null")) {
                                            out.print("<td align='center'><b>N/A adjuntos</b></td>");
                                        } else {
                                            out.print("<td><b>Adjuntos: </b><a href='Descargas?file_name=" + obj_actividad[10] + "&nomC=" + obj_permisos[5] + "&nomA=" + obj_permisos[10] + "'>" + obj_actividad[10] + "</a></td>");
                                        }
                                        if (obj_actividad[11].equals(1)) {
                                            if (obj_actividad[8].equals(1)) {
                                                out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                            } else if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                            } else {
                                                out.print("<td colspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            }
                                        } else if (obj_actividad[8].equals(1)) {
                                            out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                        } else {
                                            out.print("<td colspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                        }
                                        out.print("</tr>");
                                        out.print("</table>");
                                        // </editor-fold>
                                    } else if (obj_actividad[21].equals(4)) {
                                        // <editor-fold defaultstate="collapsed"  desc="Consulta 4 campos">
                                        out.print("<table class='table' id='resultados' style='width: 100%; margin: 0;'>");
                                        out.print("<tr>");
                                        out.print("<td colspan='8'></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<th rowspan='3' align='center' style='width:10%;'>" + obj_actividad[5] + "<br />" + obj_actividad[6] + "</th>");
                                        out.print("<td><b>Turno: </b>" + obj_actividad[7] + "</td>");
                                        out.print("<td><b>Responsable: </b>" + obj_actividad[3] + "</td>");
                                        out.print("<td><b>Consecutivo: </b>" + obj_actividad[2] + "</td>");
                                        if (obj_actividad[8].equals(0)) {
                                            if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=1&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&txt_bus=" + filtro + "'> <i class=\"fas fa-pencil-alt fa-2x\"></i> </a></td>");
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=4&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&cier=" + 1 + "&txt_bus=" + filtro + "'> <i class=\"fas fa-lock-open fa-2x\"></i> </a></td>");
                                            } else {
                                                out.print("<td rowspan='2' align='center'><a href='#'> <i class=\"fas fa-ban fa-2x\"></i> </a></td>");
                                                out.print("<td rowspan='2' align='center'><a href='#'> <i class=\"fas fa-ban fa-2x\"></i> </a></td>");
                                            }
                                            out.print("<td rowspan='3' align='center'><a href='#'> <i class=\"fas fa-ban fa-2x\"></i> </a></td>");
                                        } else {
//                                            out.print("<td rowspan='2' align='center'><a href='#'> <i class=\"fas fa-pencil-alt fa-2x\" style=\"color: #454545;\"></i> </a></td>");
                                            out.print("<td rowspan='2' colspan='2' align='center'><a href='#'> <i class=\"fas fa-lock fa-2x\"></i> </a></td>");
                                            if (obj_permisos[12].equals(1)) {
                                                if (obj_actividad[9] == null) {
                                                    out.print("<td rowspan='3' align='center'><input type='checkbox' name='checkboxes[" + p + "]'  value='" + obj_actividad[0] + "' ></td>");
//                                                    out.print("<td rowspan='3' align='center'><a href='Actividad?op=5&idC=" + CargoUsa + "&idA=" + obj_actividad[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='25' height='25.5' title='Revisar'/></a></td>");
                                                } else {
                                                    String[] FechaR = obj_actividad[9].toString().split("/");
                                                    out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                                }
                                            } else if (obj_actividad[9] == null) {
                                                out.print("<td rowspan='3' align='center'><a href='#'> <i class=\"fas fa-ban fa-2x\"></i> </a></td>");
                                            } else {
                                                String[] FechaR = obj_actividad[9].toString().split("/");
                                                out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                            }
                                        }
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>" + arg_campos[0] + ": </b>" + obj_actividad[12] + "</td>");
                                        out.print("<td colspan='2'><b>" + arg_campos[1] + ": </b>" + obj_actividad[13] + "</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>" + arg_campos[2] + ": </b>" + obj_actividad[14] + "</td>");
                                        out.print("<td><b>" + arg_campos[3] + ": </b>" + obj_actividad[15] + "</td>");
                                        if (obj_actividad[10].equals("null")) {
                                            out.print("<td align='center'><b>N/A adjuntos</b></td>");
                                        } else {
                                            out.print("<td><b>Adjuntos: </b><a href='Descargas?file_name=" + obj_actividad[10] + "&nomC=" + obj_permisos[5] + "&nomA=" + obj_permisos[10] + "'>" + obj_actividad[10] + "</a></td>");
                                        }
                                        if (obj_actividad[11].equals(1)) {
                                            if (obj_actividad[8].equals(1)) {
                                                out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='> <i class=\"far fa-eye fa-2x\"></i> </a></td>");
                                            } else if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='> <i class=\"far fa-eye fa-2x\"></i> </a></td>");
                                            } else {
                                                out.print("<td colspan='2' align='center'><a href='#'> <i class=\"fas fa-ban fa-2x\"></i> </a></td>");
                                            }
                                        } else if (obj_actividad[8].equals(1)) {
                                            out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='> <i class=\"far fa-eye fa-2x\"></i> </a></td>");
                                        } else {
                                            out.print("<td colspan='2' align='center'><a href='#'> <i class=\"fas fa-ban fa-2x\"></i> </a></td>");
                                        }
                                        out.print("</tr>");
                                        out.print("</table>");
                                        // </editor-fold>
                                    } else if (obj_actividad[21].equals(5)) {
                                        // <editor-fold defaultstate="collapsed"  desc="Consulta 5 campos">
                                        out.print("<table class='table' id='resultados' style='width: 100%; margin: 0;'>");
                                        out.print("<tr>");
                                        out.print("<td colspan='9'></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<th rowspan='3' align='center' style='width:10%;'>" + obj_actividad[5] + "<br />" + obj_actividad[6] + "</th>");
                                        out.print("<td><b>Turno: </b>" + obj_actividad[7] + "</td>");
                                        out.print("<td colspan='2'><b>Responsable: </b>" + obj_actividad[3] + "</td>");
                                        out.print("<td><b>Consecutivo: </b>" + obj_actividad[2] + "</td>");
                                        if (obj_actividad[8].equals(0)) {
                                            if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=1&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&txt_bus=" + filtro + "'> <i class=\"fas fa-pencil-alt fa-2x\"></i> </a></td>");
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=4&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&cier=" + 1 + "&txt_bus=" + filtro + "'><i class=\"fas fa-lock-open fa-2x\"></i> </a></td>"); // CERRAR ACTIVIDAD
                                            } else {
//                                                out.print("<td rowspan='2' align='center'> <i class=\"fas fa-pencil-alt fa-2x\" style=\"color: #454545;\"></i> </td>");
                                                out.print("<td rowspan='2' colspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            }
                                            out.print("<td rowspan='3' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                        } else {
//                                            out.print("<td rowspan='2' align='center'> <i class=\"fas fa-pencil-alt fa-2x\" style=\"color: #454545;\"></i> </td>");
                                            if (obj_actividad[9] != null) { // SI LA ACTIVIDAD ESTA REVISADA NO SE PODRA ABRIR
                                                out.print("<td rowspan='2' align='center'> <i class=\"fas fa-lock fa-2x\"></i> </td>"); // ACTIVIDAD REVISADA, NO SE PODRA ABRIR
                                            } else {
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=4&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&cier=" + 0 + "&txt_bus=" + filtro + "'><i class=\"fas fa-lock fa-2x\"></i> </a></td>"); // ACTIVIDAD NO REVISADA, SE PODRA ABRIR
                                            }
                                            if (obj_permisos[12].equals(1)) {
                                                if (obj_actividad[9] == null) {
                                                    out.print("<td rowspan='3' align='center'><input type='checkbox' name='checkboxes[" + p + "]' value='" + obj_actividad[0] + "'></td>");
//                                                    out.print("<td rowspan='3' align='center'><a href='Actividad?op=5&idC=" + CargoUsa + "&idA=" + obj_actividad[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='25' height='25.5' title='Revisar'/></a></td>");
                                                } else {
                                                    String[] FechaR = obj_actividad[9].toString().split("/");
                                                    out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                                }
                                            } else if (obj_actividad[9] == null) {
                                                out.print("<td rowspan='3' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            } else {
                                                String[] FechaR = obj_actividad[9].toString().split("/");
                                                out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                            }
                                        }
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td style='white-space: nowrap; word-break: break-word;'><b>" + arg_campos[0] + ": </b>" + obj_actividad[12] + "</td>");
                                        out.print("<td style='white-space: nowrap; word-break: break-word;'><b>" + arg_campos[1] + ": </b>" + obj_actividad[13] + "</td>");
                                        out.print("<td colspan='2'><b>" + arg_campos[2] + ": </b>" + obj_actividad[14] + "</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td colspan='2'><b>" + arg_campos[3] + ": </b>" + obj_actividad[15] + "</td>");
                                        out.print("<td><b>" + arg_campos[4] + ": </b>" + obj_actividad[16] + "</td>");
                                        if (obj_actividad[10].equals("null")) {
                                            out.print("<td align='center'><b>N/A adjuntos</b></td>");
                                        } else {
                                            out.print("<td><b>Adjuntos: </b><a href='Descargas?file_name=" + obj_actividad[10] + "&nomC=" + obj_permisos[5] + "&nomA=" + obj_permisos[10] + "'>" + obj_actividad[10] + "</a></td>");
                                        }
                                        if (obj_actividad[11].equals(1)) {
                                            if (obj_actividad[8].equals(1)) {
                                                out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='> <i class=\"far fa-eye fa-2x\"></i> </a></td>");
                                            } else if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                            } else {
                                                out.print("<td colspan='2' align='center'><i class=\"fas fa-ban fa-2x\"></i></td>");
                                            }
                                        } else if (obj_actividad[8].equals(1)) {
                                            out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txtbus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                        } else {
                                            out.print("<td colspan='2' align='center'><i class=\"fas fa-ban fa-2x\"></i> </td>");
                                        }

                                        out.print("</tr>");
                                        out.print("</table>");
                                        // </editor-fold>
                                    } else if (obj_actividad[21].equals(6)) {
                                        // <editor-fold defaultstate="collapsed"  desc="Consulta 6 campos">
                                        out.print("<table class='table' id='resultados' style='width: 100%; margin: 0;'>");
                                        out.print("<tr>");
                                        out.print("<td colspan='9'></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<th rowspan='3' align='center' style='width:10%;'>" + obj_actividad[5] + "<br />" + obj_actividad[6] + "</th>");
                                        out.print("<td><b>Turno: </b>" + obj_actividad[7] + "</td>");
                                        out.print("<td colspan='2'><b>Responsable: </b>" + obj_actividad[3] + "</td>");
                                        out.print("<td><b>Consecutivo: </b>" + obj_actividad[2] + "</td>");
                                        if (obj_actividad[8].equals(0)) {
                                            if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=1&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&txt_bus=" + filtro + "'><i class=\"fas fa-pencil-alt fa-2x\"></i></a></td>");
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=4&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&cier=" + 1 + "&txt_bus=" + filtro + "'><i class=\"fas fa-lock-open fa-2x\"></i></a></td>");
                                            } else {
                                                out.print("<td rowspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                                out.print("<td rowspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            }
                                            out.print("<td rowspan='3' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                        } else {
//                                            out.print("<td rowspan='2' align='center'> <i class=\"fas fa-pencil-alt fa-2x\" style=\"color: #454545;\"></i> </td>");
                                            out.print("<td rowspan='2' colspan='2' align='center'> <i class=\"fas fa-lock fa-2x\"></i> </td>");
                                            if (obj_permisos[12].equals(1)) {
                                                if (obj_actividad[9] == null) {
                                                    out.print("<td rowspan='3' align='center'><input type='checkbox' name='checkboxes[" + p + "]' value='" + obj_actividad[0] + "' ></td>");
//                                                    out.print("<td rowspan='3' align='center'><a href='Actividad?op=5&idC=" + CargoUsa + "&idA=" + obj_actividad[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='25' height='25.5' title='Revisar'/></a></td>");
                                                } else {
                                                    String[] FechaR = obj_actividad[9].toString().split("/");
                                                    out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                                }
                                            } else if (obj_actividad[9] == null) {
                                                out.print("<td rowspan='3' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            } else {
                                                String[] FechaR = obj_actividad[9].toString().split("/");
                                                out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                            }
                                        }
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>" + arg_campos[0] + ": </b>" + obj_actividad[12] + "</td>");
                                        out.print("<td><b>" + arg_campos[1] + ": </b>" + obj_actividad[13] + "</td>");
                                        out.print("<td><b>" + arg_campos[2] + ": </b>" + obj_actividad[14] + "</td>");
                                        out.print("<td><b>" + arg_campos[3] + ": </b>" + obj_actividad[15] + "</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>" + arg_campos[4] + ": </b>" + obj_actividad[16] + "</td>");
                                        out.print("<td colspan='2'><b>" + arg_campos[5] + ": </b>" + obj_actividad[17] + "</td>");
                                        if (obj_actividad[10].equals("null")) {
                                            out.print("<td align='center'><b>N/A adjuntos</b></td>");
                                        } else {
                                            out.print("<td><b>Adjuntos: </b><a href='Descargas?file_name=" + obj_actividad[10] + "&nomC=" + obj_permisos[5] + "&nomA=" + obj_permisos[10] + "'>" + obj_actividad[10] + "</a></td>");
                                        }
                                        if (obj_actividad[11].equals(1)) {
                                            if (obj_actividad[8].equals(1)) {
                                                out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                            } else if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                            } else {
                                                out.print("<td colspan='2' align='center'><a href='#'><i class=\"fas fa-ban fa-2x\"></i></a></td>");
                                            }
                                        } else if (obj_actividad[8].equals(1)) {
                                            out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                        } else {
                                            out.print("<td colspan='2' align='center'><a href='#'><i class=\"fas fa-ban fa-2x\"></i></a></td>");
                                        }
                                        out.print("</tr>");
                                        out.print("</table>");
                                        // </editor-fold>
                                    } else if (obj_actividad[21].equals(7)) {
                                        // <editor-fold defaultstate="collapsed"  desc="Consulta 7 campos">
                                        out.print("<table class='table' id='resultados' style='width: 100%; margin: 0;'>");
                                        out.print("<tr>");
                                        out.print("<td colspan='9'></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<th rowspan='3' align='center' style='width:10%;'>" + obj_actividad[5] + "<br />" + obj_actividad[6] + "</th>");
                                        out.print("<td><b>Turno: </b>" + obj_actividad[7] + "</td>");
                                        out.print("<td colspan='2'><b>Responsable: </b>" + obj_actividad[3] + "</td>");
                                        out.print("<td><b>Consecutivo: </b>" + obj_actividad[2] + "</td>");
                                        out.print("<td><b>" + arg_campos[0] + ": </b>" + obj_actividad[12] + "</td>");
                                        if (obj_actividad[8].equals(0)) {
                                            if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=1&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&txt_bus=" + filtro + "'><i class=\"fas fa-pencil-alt fa-2x\"></i></a></td>");
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=4&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&cier=" + 1 + "&txt_bus=" + filtro + "'><i class=\"fas fa-lock-open fa-2x\"></i></a></td>");
                                            } else {
                                                out.print("<td rowspan='2' align='center'><a href='#'><i class=\"fas fa-ban fa-2x\"></i></a></td>");
                                                out.print("<td rowspan='2' align='center'><a href='#'><i class=\"fas fa-ban fa-2x\"></i></a></td>");
                                            }
                                            out.print("<td rowspan='3' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                        } else {
//                                            out.print("<td rowspan='2' align='center'> <i class=\"fas fa-pencil-alt fa-2x\" style=\"color: #454545;\"></i> </td>");
                                            out.print("<td rowspan='2' colspan='2' align='center'> <i class=\"fas fa-lock fa-2x\"></i> </td>");
                                            if (obj_permisos[12].equals(1)) {
                                                if (obj_actividad[9] == null) {
                                                    out.print("<td rowspan='3' align='center'><input type='checkbox' name='checkboxes[" + p + "]' value='" + obj_actividad[0] + "' ></td>");
//                                                    out.print("<td rowspan='3' align='center'><a href='Actividad?op=5&idC=" + CargoUsa + "&idA=" + obj_actividad[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='25' height='25.5' title='Revisar'/></a></td>");
                                                } else {
                                                    String[] FechaR = obj_actividad[9].toString().split("/");
                                                    out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                                }
                                            } else if (obj_actividad[9] == null) {
                                                out.print("<td rowspan='3' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            } else {
                                                String[] FechaR = obj_actividad[9].toString().split("/");
                                                out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                            }
                                        }
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>" + arg_campos[1] + ": </b>" + obj_actividad[13] + "</td>");
                                        out.print("<td><b>" + arg_campos[2] + ": </b>" + obj_actividad[14] + "</td>");
                                        out.print("<td><b>" + arg_campos[3] + ": </b>" + obj_actividad[15] + "</td>");
                                        out.print("<td colspan='2'><b>" + arg_campos[4] + ": </b>" + obj_actividad[16] + "</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td colspan='2'><b>" + arg_campos[5] + ": </b>" + obj_actividad[17] + "</td>");
                                        out.print("<td colspan='2'><b>" + arg_campos[6] + ": </b>" + obj_actividad[18] + "</td>");
                                        if (obj_actividad[10].equals("null")) {
                                            out.print("<td align='center'><b>N/A adjuntos</b></td>");
                                        } else {
                                            out.print("<td><b>Adjuntos: </b><a href='Descargas?file_name=" + obj_actividad[10] + "&nomC=" + obj_permisos[5] + "&nomA=" + obj_permisos[10] + "'>" + obj_actividad[10] + "</a></td>");
                                        }
                                        if (obj_actividad[11].equals(1)) {
                                            if (obj_actividad[8].equals(1)) {
                                                out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                            } else if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                            } else {
                                                out.print("<td colspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            }
                                        } else if (obj_actividad[8].equals(1)) {
                                            out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                        } else {
                                            out.print("<td colspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                        }
                                        out.print("</tr>");
                                        out.print("</table>");
                                        // </editor-fold>
                                    } else if (obj_actividad[21].equals(8)) {
                                        // <editor-fold defaultstate="collapsed"  desc="Consulta 8 campos">
                                        out.print("<table class='table' id='resultados' style='width: 100%; margin: 0;'>");
                                        out.print("<tr>");
                                        out.print("<td colspan='9'></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<th rowspan='3' align='center' style='width:10%;'>" + obj_actividad[5] + "<br />" + obj_actividad[6] + "</th>");
                                        out.print("<td><b>Turno: </b>" + obj_actividad[7] + "</td>");
                                        out.print("<td colspan='2'><b>Responsable: </b>" + obj_actividad[3] + "</td>");
                                        out.print("<td><b>Consecutivo: </b>" + obj_actividad[2] + "</td>");
                                        out.print("<td><b>" + arg_campos[0] + ": </b>" + obj_actividad[12] + "</td>");
                                        if (obj_actividad[8].equals(0)) {
                                            if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=1&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&txt_bus=" + filtro + "'><i class=\"fas fa-pencil-alt fa-2x\"></i></a></td>");
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=4&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&cier=" + 1 + "&txt_bus=" + filtro + "'><i class=\"fas fa-lock-open fa-2x\"></i></a></td>");
                                            } else {
                                                out.print("<td rowspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                                out.print("<td rowspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            }
                                            out.print("<td rowspan='3' align='center'><a href='#'><i class=\"fas fa-ban fa-2x\"></i></a></td>");
                                        } else {
//                                            out.print("<td rowspan='2' align='center'> <i class=\"fas fa-pencil-alt fa-2x\" style=\"color: #454545;\"></i> </td>");
                                            out.print("<td rowspan='2' colspan='2' align='center'> <i class=\"fas fa-lock fa-2x\"></i> </td>");
                                            if (obj_permisos[12].equals(1)) {
                                                if (obj_actividad[9] == null) {
                                                    out.print("<td rowspan='3' align='center'><input type='checkbox' name='checkboxes[" + p + "]' value='" + obj_actividad[0] + "' ></td>");
//                                                    out.print("<td rowspan='3' align='center'><a href='Actividad?op=5&idC=" + CargoUsa + "&idA=" + obj_actividad[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='25' height='25.5' title='Revisar'/></a></td>");
                                                } else {
                                                    String[] FechaR = obj_actividad[9].toString().split("/");
                                                    out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                                }
                                            } else if (obj_actividad[9] == null) {
                                                out.print("<td rowspan='3' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            } else {
                                                String[] FechaR = obj_actividad[9].toString().split("/");
                                                out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                            }
                                        }
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>" + arg_campos[1] + ": </b>" + obj_actividad[13] + "</td>");
                                        out.print("<td><b>" + arg_campos[2] + ": </b>" + obj_actividad[14] + "</td>");
                                        out.print("<td><b>" + arg_campos[3] + ": </b>" + obj_actividad[15] + "</td>");
                                        out.print("<td colspan='2'><b>" + arg_campos[4] + ": </b>" + obj_actividad[16] + "</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td colspan='2'><b>" + arg_campos[5] + ": </b>" + obj_actividad[17] + "</td>");
                                        out.print("<td><b>" + arg_campos[6] + ": </b>" + obj_actividad[18] + "</td>");
                                        out.print("<td><b>" + arg_campos[7] + ": </b>" + obj_actividad[19] + "</td>");
                                        if (obj_actividad[10].equals("null")) {
                                            out.print("<td align='center'><b>N/A adjuntos</b></td>");
                                        } else {
                                            out.print("<td><b>Adjuntos: </b><a href='Descargas?file_name=" + obj_actividad[10] + "&nomC=" + obj_permisos[5] + "&nomA=" + obj_permisos[10] + "'>" + obj_actividad[10] + "</a></td>");
                                        }
                                        if (obj_actividad[11].equals(1)) {
                                            if (obj_actividad[8].equals(1)) {
                                                out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                            } else if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                            } else {
                                                out.print("<td colspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            }
                                        } else if (obj_actividad[8].equals(1)) {
                                            out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                        } else {
                                            out.print("<td colspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                        }
                                        out.print("</tr>");
                                        out.print("</table>");
                                        // </editor-fold>
                                    } else if (obj_actividad[21].equals(9)) {
                                        // <editor-fold defaultstate="collapsed"  desc="Consulta 9 campos">
                                        out.print("<table class='table' id='resultados' style='width: 100%; '>");
                                        out.print("<tr>");
                                        out.print("<td colspan='9'></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<th rowspan='3' align='center' style='width:10%;'>" + obj_actividad[5] + "<br />" + obj_actividad[6] + "</th>");
                                        out.print("<td><b>Turno: </b>" + obj_actividad[7] + "</td>");
                                        out.print("<td colspan='2'><b>Responsable: </b>" + obj_actividad[3] + "</td>");
                                        out.print("<td><b>Consecutivo: </b>" + obj_actividad[2] + "</td>");
                                        out.print("<td><b>" + arg_campos[0] + ": </b>" + obj_actividad[12] + "</td>");
                                        if (obj_actividad[8].equals(0)) {
                                            if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=1&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&txt_bus=" + filtro + "'><i class=\"fas fa-pencil-alt fa-2x\"></i></a></td>");
                                                out.print("<td rowspan='2' align='center'><a href='Actividad?op=4&idA=" + obj_actividad[0] + "&idC=" + CargoUsa + "&cier=" + 1 + "&txt_bus=" + filtro + "'><i class=\"fas fa-lock-open fa-2x\"></i></a></td>");
                                            } else {
                                                out.print("<td rowspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                                out.print("<td rowspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            }
                                            out.print("<td rowspan='3' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                        } else {
//                                            out.print("<td rowspan='2' align='center'> <i class=\"fas fa-pencil-alt fa-2x\" style=\"color: #454545;\"></i> </td>");
                                            out.print("<td rowspan='2' colspan='2' align='center'> <i class=\"fas fa-lock fa-2x\"></i> </td>");
                                            if (obj_permisos[12].equals(1)) {
                                                if (obj_actividad[9] == null) {
                                                    out.print("<td rowspan='3' align='center'><input type='checkbox' name='checkboxes[" + p + "]' value='" + obj_actividad[0] + "' ></td>");
//                                                    out.print("<td rowspan='3' align='center'><a href='Actividad?op=5&idC=" + CargoUsa + "&idA=" + obj_actividad[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='25' height='25.5' title='Revisar'/></a></td>");
                                                } else {
                                                    String[] FechaR = obj_actividad[9].toString().split("/");
                                                    out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                                }
                                            } else if (obj_actividad[9] == null) {
                                                out.print("<td rowspan='3' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            } else {
                                                String[] FechaR = obj_actividad[9].toString().split("/");
                                                out.print("<td rowspan='3' align='center' style='width: 12%;'>" + FechaR[0] + "<br />" + FechaR[1] + "<br />" + FechaR[2] + "</td>");
                                            }
                                        }
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>" + arg_campos[1] + ": </b>" + obj_actividad[13] + "</td>");
                                        out.print("<td><b>" + arg_campos[2] + ": </b>" + obj_actividad[14] + "</td>");
                                        out.print("<td><b>" + arg_campos[3] + ": </b>" + obj_actividad[15] + "</td>");
                                        out.print("<td colspan='2'><b>" + arg_campos[4] + ": </b>" + obj_actividad[16] + "</td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        out.print("<td><b>" + arg_campos[5] + ": </b>" + obj_actividad[17] + "</td>");
                                        out.print("<td><b>" + arg_campos[6] + ": </b>" + obj_actividad[18] + "</td>");
                                        out.print("<td><b>" + arg_campos[7] + ": </b>" + obj_actividad[19] + "</td>");
                                        out.print("<td><b>" + arg_campos[8] + ": </b>" + obj_actividad[20] + "</td>");
                                        if (obj_actividad[10].equals("null")) {
                                            out.print("<td align='center'><b>N/A adjuntos</b></td>");
                                        } else {
                                            out.print("<td><b>Adjuntos: </b><a href='Descargas?file_name=" + obj_actividad[10] + "&nomC=" + obj_permisos[5] + "&nomA=" + obj_permisos[10] + "'>" + obj_actividad[10] + "</a></td>");
                                        }
                                        if (obj_actividad[11].equals(1)) {
                                            if (obj_actividad[8].equals(1)) {
                                                out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                            } else if (obj_actividad[1].equals(IdUsuario)) {
                                                out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                            } else {
                                                out.print("<td colspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                            }
                                        } else if (obj_actividad[8].equals(1)) {
                                            out.print("<td colspan='2' align='center'><a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txt_bus='><i class=\"far fa-eye fa-2x\"></i></a></td>");
                                        } else {
                                            out.print("<td colspan='2' align='center'> <i class=\"fas fa-ban fa-2x\"></i> </td>");
                                        }
                                        out.print("</tr>");
                                        out.print("</table>");
                                        // </editor-fold>
                                    }
                                    // </editor-fold>
                                }
                            }
                            out.print("</td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</form>");
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultadosT1', 10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    //</editor-fold>
                }
                if (filtro != "") {
                    if (filtro.contains("[")) {
                        String[] fto = filtro.replace("][", "///").replace("[", "").replace("]", "").split("///");
                        for (int i = 0; i < fto.length; i++) {
                            out.print("<script>");
                            out.print("window.onload=buscar('" + fto[i] + "')");
                            out.print("</script>");
                        }
                    } else {
                        out.print("<script>");
                        out.print("window.onload=buscar('" + filtro + "')");
                        out.print("</script>");
                    }
                } else {
                }
                //</editor-fold>
            } else {
                out.print("<h3 style='margin-top: 10px;'>No se encuentran actividades registradas<h3>");
            }
            out.print("<div class='cleaner'></div></div>");

        } catch (IOException ex) {
            Logger.getLogger(Tag_resultados.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();

    }
}
