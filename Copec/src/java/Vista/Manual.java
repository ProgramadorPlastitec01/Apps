package Vista;

import Controlador.ManualJpaController;
import Email.Connection_mysql_daruma;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Manual extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            HttpSession sesion = pageContext.getSession();
            String l_sgt = sesion.getAttribute("l_sgt").toString();
            ManualJpaController obj_manual = new ManualJpaController();
            Connection_mysql_daruma daruma = new Connection_mysql_daruma();
            int id_area = 0;
            int id_cargo = 0;
            int existencia = 0;
            String nombre = "";
            String documento = "";
            String siglatura = "";
            String codigo = "";
            int version = 0;
            String ant_version = "";
            List lst_f_area = null;
            List lst_l_area = null;
            List lst_l_cargo = null;
            List lst_l_cargo_a = null;
            List lst_documento = null;
            List lst_documentoD = null;
            List lst_ControlC = null;
            List lst_manual = null;
            List lst_movimiento = null;
            int id_manual = 0;
            int tipo_doc = 0;
            // <editor-fold defaultstate="collapsed"  desc="Registro Manual.">
            if (pageContext.getRequest().getAttribute("Manual").toString().equals("modulo")) {
                lst_f_area = (List) pageContext.getRequest().getAttribute("consultar_f_area");
                lst_l_area = (List) pageContext.getRequest().getAttribute("consultar_l_area");
                lst_l_cargo = (List) pageContext.getRequest().getAttribute("consultar_l_cargo");
                lst_l_cargo_a = (List) pageContext.getRequest().getAttribute("consultar_l_cargo_a");
                lst_documento = (List) pageContext.getRequest().getAttribute("consultar_documento");
                id_area = Integer.parseInt(pageContext.getRequest().getAttribute("id_area").toString());
                id_cargo = Integer.parseInt(pageContext.getRequest().getAttribute("id_cargo").toString());
                siglatura = pageContext.getRequest().getAttribute("siglatura").toString();
                tipo_doc = Integer.parseInt(pageContext.getRequest().getAttribute("tipo_doc").toString());
                existencia = Integer.parseInt(pageContext.getRequest().getAttribute("existencia").toString());
                out.print("<div id='sidebar'>");
                out.print("<form method='post' id='formTipo' name='formTipo' onsubmit='checkSubmit();reg_manual();'  action='Manual?opc=9'>");
                out.print("<h3>Registro Documento</h3>");
                out.print("<b>Tipo:</b><br/>");
                out.print("<select name='cbx_doc' id='cbx_doc' onchange='checarcombo();'>");
                out.print("<option value=''>SELECCIONE TIPO</option>");
                for (int i = 0; i < lst_documento.size(); i++) {
                    Object[] obj_documento = (Object[]) lst_documento.get(i);
                    if ((Integer) obj_documento[5] == 1 && !obj_documento[3].equals("GENERAL")) {
                        out.print("<option value='" + obj_documento[2] + "'>" + obj_documento[2] + " - " + obj_documento[3] + "</option>");
                    }
                }
                out.print("</select><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('cbx_doc');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                out.print("</script>");
                out.print("<b>Siglatura:</b><br/>");
                String sis = "SI";
                if (l_sgt.startsWith(sis)) {
                    out.print("<input type='text' id='rfc' name='rfc' placeholder='Ingresar Siglatura' value='" + l_sgt + "'>");
                } else {
                    if (l_sgt.contains("LO")) {
                        out.print("<input type='text' id='rfc'  name='rfc' placeholder='Ingresar Siglatura' value='" + l_sgt + "'>");
                    } else if (l_sgt.contains("GC")) {
                        out.print("<input type='text' id='rfc'  name='rfc' placeholder='Ingresar Siglatura' value='" + l_sgt + "'>");
                    } else {
                        out.print("<input type='text' id='rfc' readonly name='rfc' placeholder='Ingresar Siglatura' value='" + l_sgt + "'>");
                    }
                }
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('rfc');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Númerico:</b><br/>");
                out.print("<input id='validate_num' type='text' class='required input_field' name='txt_numerico' placeholder='Ingresar númerico' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_num');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add(Validate.Numericality);");
                out.print("</script>");
                out.print("<b>Documento:</b><br/>");
                out.print("<textarea rows='4' id='validate_nom' name='txt_manual' class='required input_field' placeholder='Ingresar documento' onchange='javascript:this.value=this.value.toUpperCase();'></textarea><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_nom');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Versión:</b><br/>");
                out.print("<input type='text' id='validate_ver' class='required input_field' name='txt_ver' placeholder='Ingresar versión'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validate_ver');");
                out.print("validation.add( Validate.Numericality );");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit'  name='' value='Registrar'>");
                out.print("</form>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
                out.print("<div id='content'>");
                out.print("<h3>Tipo Consulta</h3>");
                if (existencia == 1) {
                    out.print("<div class='exito mensajes'>Documento Creado Exitosamente.</div><br/>");
                }
                if (existencia == 2) {
                    out.print("<div class='error mensajes'>Datos Incompletos o Erroneos Por Favor Digilenciar Todos los Campos.</div><br/>");
                }
                if (existencia == 3) {
                    out.print("<div class='error mensajes'>El Documento Ingresado Ya Existe Por Favor Digite Un Codigo Diferente.</div><br/>");
                }
                if (existencia == 4) {
                    out.print("<div class='exito mensajes'>El Documento Ha Sido Inactivado Correctamente.</div><br/>");
                }
                if (existencia == 5) {
                    out.print("<div class='exito mensajes'>El Documento Ha Sido Inactivado Correctamente.</div>");
                    out.print("<div class='alerta mensajes'>Correo Enviado a Areas Que Aplica Matriz .</div><br/>");
                }
                if (existencia == 6) {
                    out.print("<div class='exito mensajes'>El Documento Ha Sido Activado Correctamente.</div><br/>");
                }
                if (existencia == 7) {
                    out.print("<div class='exito mensajes'>El Nombre Del Documento Ha Cambiado Correctamente.</div><br/>");
                }
                if (existencia == 8) {
                    out.print("<div class='error mensajes'>Error Al Cambiar El Nombre Del Documento Comuniquese Con El Administrador.</div><br/>");
                }
                if (existencia == 9) {
                    out.print("<div class='error mensajes'>Por Favor Ingresar Datos Al Nombre del Documento No Se Aceptan Datos Vacios.</div><br/>");
                }
                if (existencia == 10) {
                    out.print("<div class='exito mensajes'>El Codigo Del Documento Ha Cambiado Correctamente.</div><br/>");
                }
                if (existencia == 11) {
                    out.print("<div class='error mensajes'>Error Al Cambiar El Codigo Del Documento Comuniquese Con El Administrador.</div><br/>");
                }
                if (existencia == 12) {
                    out.print("<div class='error mensajes'>Por Favor Ingresar Datos Al Codigo del Documento No Se Aceptan Datos Vacios.</div><br/>");
                }
                if (existencia == 13) {
                    out.print("<div class='exito mensajes'>La Version Del Documento Ha Sido Cambiada Correctamente.</div>");
                }
                if (existencia == 14) {
                    out.print("<div class='exito mensajes'>La Version Del Documento Ha Sido Cambiada Correctamente.</div>");
                    out.print("<div class='alerta mensajes'>Correo Enviado a Areas Que Aplica Matriz .</div><br/>");
                }
                if (existencia == 15) {
                    out.print("<div class='error mensajes'>Error Al Cambiar La Version Del Documento Comuniquese Con El Administrador.</div><br/>");
                }
                if (existencia == 16) {
                    out.print("<div class='error mensajes'>Por Favor Ingresar Datos A La Justificacion del Documento No Se Aceptan Datos Vacios.</div><br/>");
                }
                if (tipo_doc == 0) {
                    out.print("<input type='radio' name='tipo_doc' id='tipo_doc_a' value='1' onclick='calc(1);'> <i>Por Área</i>");
                    out.print("<input type='radio' name='tipo_doc' id='tipo_doc_c' value='2' onclick='calc(2);'> <i>Por Cargo</i><br/><br/>");
                    out.print("<i>Seleccione Tipo de Consulta</i>");
                } else if (tipo_doc == 1) {
                    out.print("<input type='radio' checked name='tipo_doc' id='tipo_doc_a' value='1' onclick='calc(1);'> <i>Por Área</i>");
                    out.print("<input type='radio' name='tipo_doc' id='tipo_doc_c' value='2' onclick='calc(2);'> <i>Por Cargo</i>");
                } else {
                    out.print("<input type='radio' name='tipo_doc' id='tipo_doc_a' value='1' onclick='calc(1);'> <i>Por Área</i>");
                    out.print("<input type='radio' checked name='tipo_doc' id='tipo_doc_c' value='2' onclick='calc(2);'> <i>Por Cargo</i>");
                }

                out.print("<br/><br/>");
                if (lst_l_area != null) {
                    // <editor-fold defaultstate="collapsed"  desc="Tipo Consulta por área.">
                    out.print("<form method='post' action='Manual?opc=3' name='enviarTiC' id='enviarTiC'>");
                    out.print("<input name='tipo_doc' value='" + tipo_doc + "' type='hidden'>");
                    out.print("<select style='width:400px;' name='cbx_siglatura' id='cbx_siglatura' onchange='document.enviarTiC.submit()'>");
                    out.print("<option style='display:none'>SELECCIONE ÁREA</option>");
                    for (int i = 0; i < lst_l_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_l_area.get(i);
                        if ((Integer) obj_area[3] == 1) {
                            if (obj_area[2].toString().equals(siglatura)) {
                                out.print("<option value='" + obj_area[2] + "' selected>" + obj_area[2].toString().toUpperCase() + " / " + obj_area[1].toString().toUpperCase() + "</option>");
                            } else {
                                out.print("<option value='" + obj_area[2] + "'>" + obj_area[2].toString().toUpperCase() + " / " + obj_area[1].toString().toUpperCase() + "</option>");
                            }
                        }
                    }
                    out.print("</select><br/><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('cbx_area');");
                    out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                    out.print("</script>");
                    out.print("</form>");
                    // </editor-fold>
                }
                if (lst_l_cargo != null) {
                    // <editor-fold defaultstate="collapsed"  desc="Tipo Consulta por cargo.">
                    out.print("<form method='post' action='Manual?opc=3' name='enviarCrg' id='enviarCrg'>");
                    out.print("<input name='tipo_doc' value='" + tipo_doc + "' type='hidden'>");
                    out.print("<select style='width:400px;' name='cbx_cargo' id='cbx_cargo' onchange='document.enviarCrg.submit()'>");
                    out.print("<option style='display:none'>SELECCIONE CARGO</option>");
                    for (int i = 0; i < lst_l_cargo.size(); i++) {
                        Object[] obj_crg = (Object[]) lst_l_cargo.get(i);
                        if ((Integer) obj_crg[7] == 1) {
                            if (obj_crg[0].equals(id_cargo)) {
                                out.print("<option value='" + obj_crg[0] + ":" + obj_crg[4] + "' selected>" + obj_crg[4].toString().toUpperCase() + " / " + obj_crg[1].toString().toUpperCase() + "</option>");
                            } else {
                                out.print("<option value='" + obj_crg[0] + ":" + obj_crg[4] + "'>" + obj_crg[4].toString().toUpperCase() + " / " + obj_crg[1].toString().toUpperCase() + "</option>");
                            }
                        }
                    }
                    out.print("</select><br/><br/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('cbx_cargo');");
                    out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: \"\"} );");
                    out.print("</script>");
                    out.print("</form>");

                    // </editor-fold>
                }
                // <editor-fold defaultstate="collapsed"  desc="Consulta por área.">
                if (!siglatura.isEmpty() && id_area != 0) {
                    out.print("<input type='search' class='form-control' placeholder='Buscar..' id='Txt_filtro' onkeyup='Filtrar()'>");
                    out.print("<table class='table' style='width:100%' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th>TIPO</th>");
                    out.print("<th COLSPAN='2'>DOCUMENTO</th>");
                    out.print("<th>VERSIÓN</th>");
                    out.print("<th>MOVIT</th>");
                    if (l_sgt.equals(siglatura)) {
                        out.print("<th>ESTADO</th>");
                    }
                    out.print("</tr>");
                    if (siglatura.contains("LO")) {
                        lst_manual = obj_manual.mnl_c_manual_v2("LO","DC");
                    }else if (siglatura.contains("GC")) {
                        lst_manual = obj_manual.mnl_c_manual_v2("GC","DT");
                    }else {
                        lst_manual = obj_manual.mnl_c_manual(siglatura);
                    }

                    if (lst_manual != null) {
                        for (int i = 0; i < lst_manual.size(); i++) {
                            Object[] obj_mnl = (Object[]) lst_manual.get(i);
                            if (Integer.parseInt(obj_mnl[6].toString()) == 1) {
                                out.print("<tr>");
                                for (int j = 0; j < lst_documento.size(); j++) {
                                    Object[] obj_doc = (Object[]) lst_documento.get(j);
                                    if (obj_doc[2].equals(obj_mnl[4])) {
                                        out.print("<td style='text-align: center;'><b>" + obj_doc[3] + "</b></td>");
                                    }
                                }
                                if (l_sgt.equals(siglatura)) {
                                    out.print("<form method='post' action='Manual?opc=11' name='enviarCod' id='enviarCod'>");
                                    out.print("<input name='id_manual' value='" + obj_mnl[0] + "' type='hidden'>");
                                    out.print("<input name='siglatura' value='" + siglatura + "' type='hidden'>");
                                    String Datos[] = obj_mnl[1].toString().split("-");
                                    out.print("<input name='tipo_d' value='" + Datos[0].toString() + "' type='hidden'>");
                                    out.print("<input name='area_d' value='" + Datos[1].toString() + "' type='hidden'>");
                                    out.print("<td style='text-align: center;'>" + Datos[0].toString() + "-" + Datos[1].toString() + "-<input style='margin: 0; border: none; width: 40px; font-size: 11px; text-align: center;' type='text' name='numerico_d' id='numerico_d' placeholder='Código' value='" + Datos[2].toString() + "' onchange='document.enviarCod.submit()'> </td>");
                                    out.print("</form>");

                                    out.print("<form method='post' action='Manual?opc=10' name='enviarNom' id='enviarNom' onsubmit='cambiar_nombre()'>");
                                    out.print("<input name='id_manual' value='" + obj_mnl[0] + "' type='hidden'>");
                                    out.print("<input name='siglatura' value='" + siglatura + "' type='hidden'>");
                                    out.print("<td><input style='margin: 0; border: none; width: 100%; font-size: 11px;' type='text' name='txt_documento' id='txt_documento' placeholder='Documento' value='" + obj_mnl[3] + "' onchange='document.enviarNom.submit()'> </td>");
                                    out.print("</form>");
                                } else {
                                    out.print("<td>" + obj_mnl[1] + "</td>");
                                    out.print("<td>" + obj_mnl[3] + " </td>");
                                }
                                if (l_sgt.equals(siglatura)) {
                                    out.print("<form method='post' action='Manual?opc=5' name='enviarVer' id='enviarVer'>");
                                    out.print("<input name='id_manual' value='" + obj_mnl[0] + "' type='hidden'>");
                                    out.print("<input name='codigo' value='" + obj_mnl[1] + "' type='hidden'>");
                                    out.print("<input name='documento' value='" + obj_mnl[3] + "' type='hidden'>");
                                    out.print("<input name='tipo_doc' value='" + tipo_doc + "' type='hidden'>");
                                    out.print("<input name='siglatura' value='" + siglatura + "' type='hidden'>");
                                    out.print("<input name='txt_a_version' value='" + obj_mnl[2] + "' type='hidden'>");
                                    out.print("<td style='text-align: center;'><input style='margin: 0; border: none; width: 60px; font-size: 11px; text-align: center;' type='number' name='m_version' id='m_version' placeholder='0' min='" + obj_mnl[2] + "' value='" + obj_mnl[2] + "' onchange='document.enviarVer.submit()'> </td>");
                                    out.print("</form>");
                                } else {
                                    out.print("<td style='text-align: center;'>" + obj_mnl[2] + "</td>");
                                }
                                if (obj_manual.lmn_c_l_manual(Integer.parseInt(obj_mnl[0].toString())) != null) {
                                    out.print("<td style='text-align: center;'><a href='Manual?opc=4&id_manual=" + obj_mnl[0] + "&tipo_doc=" + tipo_doc + "&siglatura=" + siglatura + "'><img src='Interfaz/Template/images/Ver.png'  width='26' height='26' title='Cambios'></a></td>");
                                } else {
                                    out.print("<td style='text-align: center;'><img src='Interfaz/Template/images/Warning.png'  width='23' height='23' title='Sin Cambios'></td>");
                                }
                                if (l_sgt.equals(siglatura)) {
                                    if (Integer.parseInt(obj_mnl[6].toString()) == 1) {
                                        out.print("<td style='text-align: center;'><a href='javaScript:inactivo_documento(" + obj_mnl[0] + ",\"" + siglatura + "\"," + tipo_doc + "," + obj_mnl[2] + ")'><img src='Interfaz/Template/images/Check.png'  width='26' height='26' title='Inactivar'></a></td>");
                                    } else {
                                        out.print("<td style='text-align: center;'><a href='javaScript:activo_documento(" + obj_mnl[0] + ",\"" + siglatura + "\"," + tipo_doc + "," + obj_mnl[2] + ")'><img src='Interfaz/Template/images/Delete.png'  width='22' height='22' title='Activar'></a></td>");
                                    }
                                }
                                out.print("</tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                                for (int j = 0; j < lst_documento.size(); j++) {
                                    Object[] obj_doc = (Object[]) lst_documento.get(j);
                                    if (obj_doc[2].equals(obj_mnl[4])) {
                                        out.print("<td style='text-align: center;'>" + obj_doc[3] + "</td>");
                                    }
                                }
                                if (l_sgt.equals(siglatura)) {
                                    String Datos[] = obj_mnl[1].toString().split("-");
                                    out.print("<td style='text-align: center;'>" + Datos[0].toString() + "-" + Datos[1].toString() + "-" + Datos[2].toString() + " </td>");
                                    out.print("<td>" + obj_mnl[3] + " </td>");
                                } else {
                                    out.print("<td>" + obj_mnl[1] + "</td>");
                                    out.print("<td>" + obj_mnl[3] + " </td>");
                                }
                                if (l_sgt.equals(siglatura)) {
                                    out.print("<td style='text-align: center;'>" + obj_mnl[2] + " </td>");
                                } else {
                                    out.print("<td style='text-align: center;'>" + obj_mnl[2] + "</td>");
                                }
                                if (obj_manual.lmn_c_l_manual(Integer.parseInt(obj_mnl[0].toString())) != null) {
                                    out.print("<td style='text-align: center;'><a href='Manual?opc=4&id_manual=" + obj_mnl[0] + "&tipo_doc=" + tipo_doc + "&siglatura=" + siglatura + "'><img src='Interfaz/Template/images/Ver.png'  width='26' height='26' title='Cambios'></a></td>");
                                } else {
                                    out.print("<td style='text-align: center;'><img src='Interfaz/Template/images/Warning.png'  width='23' height='23' title='Sin Cambios'></td>");
                                }
                                if (l_sgt.equals(siglatura)) {
                                    if (Integer.parseInt(obj_mnl[6].toString()) == 1) {
                                        out.print("<td style='text-align: center;'><a href='javaScript:inactivo_documento(" + obj_mnl[0] + ",\"" + siglatura + "\"," + tipo_doc + ")'><img src='Interfaz/Template/images/Check.png'  width='26' height='26' title='Inactivar'></a></td>");
                                    } else {
                                        out.print("<td style='text-align: center;'><a href='javaScript:activar_documento(" + obj_mnl[0] + ",\"" + siglatura + "\"," + tipo_doc + ")'><img src='Interfaz/Template/images/Delete.png'  width='22' height='22' title='Activar'></a></td>");
                                    }
                                }
                                out.print("</tr>");
                            }
                        }
                        out.print("</table>");
                    }
                }
                // </editor-fold>

                // <editor-fold defaultstate="collapsed"  desc="Consulta por cargo.">
                if (id_cargo != 0) {
                    out.print("<input type='search' class='form-control' placeholder='Buscar..' id='Txt_filtro' onkeyup='Filtrar()'>");
                    out.print("<table class='table' style='width:100%'  id='resultados'>");
                    out.print("<tr>");
                    out.print("<th>TIPO</th>");
                    out.print("<th COLSPAN='3'>");
                    out.print("<form method='post' action='Manual?opc=3' name='enviarDato' id='enviarDato'>");
                    out.print("<input name='tipo_doc' value='" + tipo_doc + "' type='hidden'>");
                    out.print("DOCUMENTO(S)&nbsp;&nbsp;&nbsp;<select name='cbx_cargo' id='cbx_cargo' onchange='document.enviarDato.submit()'>");
                    out.print("<option value='0'>SELECCIONE DEPENDENCIA/ÁREA</option>");
                    for (int i = 0; i < lst_l_cargo_a.size(); i++) {
                        Object[] obj_l_cargo_a = (Object[]) lst_l_cargo_a.get(i);
                        if ((Integer) obj_l_cargo_a[3] == 1) {
                            out.print("<option value='" + id_cargo + ":" + obj_l_cargo_a[2] + "'>" + obj_l_cargo_a[2].toString().toUpperCase() + " / " + obj_l_cargo_a[1].toString().toUpperCase() + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("</form>");
                    out.print("</th>");
                    out.print("<th>VERSIÓN</th>");
                    out.print("</tr>");
                    if (siglatura.contains("LO")) {
                        lst_manual = obj_manual.mnl_c_manual_v2("LO","DC");
                    }else if (siglatura.contains("GC")) {
                        lst_manual = obj_manual.mnl_c_manual_v2("GC","DT");
                    }else {
                        lst_manual = obj_manual.mnl_c_manual(siglatura);
                    }

                    for (int i = 0; i < lst_manual.size(); i++) {
                        Object[] obj_mnl = (Object[]) lst_manual.get(i);
                        out.print("<tr>");
                        for (int j = 0; j < lst_documento.size(); j++) {
                            Object[] obj_doc = (Object[]) lst_documento.get(j);
                            if (obj_doc[2].equals(obj_mnl[4])) {
                                out.print("<td style='text-align: center;'><b>" + obj_doc[3] + "</b></td>");
                            }
                        }
                        List nn = obj_manual.crm_c_cargo_manual(id_cargo, obj_mnl[1].toString());
                        if (Integer.parseInt(obj_mnl[6].toString()) == 1) {
                            if (nn != null) {
                                Object[] obj_cargo_temp = (Object[]) nn.get(0);
//                            if (Integer.parseInt(obj_cargo_temp[8].toString()) == 0 && Integer.parseInt(obj_mnl[6].toString()) == 1) {
//                                out.print("<td style='text-align: center;background-color:orange;'><input type='checkbox' name='ckb_doc" + i + "' id='ckb_doc" + i + "'  value='" + obj_mnl[0] + "'  onclick='calc_c(" + i + "," + obj_mnl[0] + "," + tipo_doc + "," + id_cargo + ",\"" + siglatura + "\"," + obj_cargo_temp[7] + ",1);'></td>");
//                            } else if (Integer.parseInt(obj_cargo_temp[8].toString()) == 0 && Integer.parseInt(obj_mnl[6].toString()) == 0) {
//                                out.print("<td style='text-align: center;background-color:orange;'><img src='Interfaz/Template/images/check_inactivo.png' width='12' height='12'></td>");
//                            } else if (Integer.parseInt(obj_cargo_temp[8].toString()) == 1 && Integer.parseInt(obj_mnl[6].toString()) == 1) {
//                                out.print("<td style='text-align: center;'><a onclick='calc_c(" + i + "," + obj_mnl[0] + "," + tipo_doc + "," + id_cargo + ",\"" + siglatura + "\"," + obj_cargo_temp[7] + ",0);'><img src='Interfaz/Template/images/Visto.png' width='26' height='26'></a></td>");
//                            } else {
//                                out.print("<td><img src='Interfaz/Template/images/check_inactivo.png' width='12' height='12'></td>");
//                            }
                                if (Integer.parseInt(obj_cargo_temp[8].toString()) == 0) {
                                    out.print("<td style='text-align: center;background-color:orange;'><input type='checkbox' name='ckb_doc" + i + "' id='ckb_doc" + i + "'  value='" + obj_mnl[0] + "'  onclick='calc_c(" + i + "," + obj_mnl[0] + "," + tipo_doc + "," + id_cargo + ",\"" + siglatura + "\"," + obj_cargo_temp[7] + ",1);'></td>");
                                } else {
                                    out.print("<td style='text-align: center;'><a onclick='calc_c(" + i + "," + obj_mnl[0] + "," + tipo_doc + "," + id_cargo + ",\"" + siglatura + "\"," + obj_cargo_temp[7] + ",0);'><img src='Interfaz/Template/images/Visto.png' width='26' height='26'></a></td>");
                                }
                            } else {
                                out.print("<td style='text-align: center;'><input type='checkbox' name='ckb_doc" + i + "' id='ckb_doc" + i + "'  value='" + obj_mnl[0] + "'  onclick='calc_c(" + i + "," + obj_mnl[0] + "," + tipo_doc + "," + id_cargo + ",\"" + siglatura + "\",0,1);'></td>");
                            }
                        } else {
                            out.print("<td align='center'><b class='rojo'>Inactivo</b></td>");
                        }
                        out.print("<td>" + obj_mnl[1] + "</td>");
                        out.print("<td>" + obj_mnl[3] + " </td>");
                        out.print("<td style='text-align: center;'>" + obj_mnl[2] + " </td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                }
                // </editor-fold>
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed"  desc="Movimiento Documento.">
            if (pageContext.getRequest().getAttribute("Manual").toString().equals("movit")) {
                lst_movimiento = (List) pageContext.getRequest().getAttribute("consultar_movit");
                lst_documento = (List) pageContext.getRequest().getAttribute("consultar_documento");
                tipo_doc = Integer.parseInt(pageContext.getRequest().getAttribute("tipo_doc").toString());
                siglatura = pageContext.getRequest().getAttribute("siglatura").toString();
                out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<a href='Manual?opc=3&tipo_doc=" + tipo_doc + "&cbx_siglatura=" + siglatura + "'>"
                        + "<img src='Interfaz/Template/images/Volver.png' width='30' height='30' title='Volver'>"
                        + "</a>&nbsp;<i>Volver al módulo</i>");
                out.print("<table class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<td ROWSPAN='2' style='text-align: center;'><img src='Interfaz/Template/images/Cabecera.png' style='width:202.5px;height:67.5px'></td>");
                out.print("<td style='text-align: center; height: 50px;'><b>CONTROL DEL CAMBIO</b></td>");
                out.print("<td COLSPAN='2' style='text-align: center;'><b>MOVIMIENTO DEL DOCUMENTO</b></td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("<br/><br/>");
                out.print("<table class='table' style='width:100%'>");
                for (int i = 0; i < lst_movimiento.size(); i++) {
                    Object[] obj_mvt = (Object[]) lst_movimiento.get(i);
                    out.print("<tr>");
                    out.print("<td style='text-align: center; height: 20px;background-color:#e4e4e7;'><b>CAMBIO </b></td>");
                    out.print("<th>" + (i + 1) + "</th>");
                    out.print("<td style='text-align: center; background-color:#e4e4e7;'><b>FECHA AJUSTE</b></td>");
                    out.print("<td>" + obj_mvt[4] + "</td>");
                    out.print("<td style='text-align: center; background-color:#e4e4e7;'><b>RESPONSABLE</b></td>");
                    out.print("<td>" + obj_mvt[5] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='text-align: center; height: 20px; background-color:#e4e4e7;'><b>CÓDIGO</b></td>");
                    out.print("<td>" + obj_mvt[7] + "</td>");
                    out.print("<td style='text-align: center; background-color:#e4e4e7;'><b>DOCUMENTO</b></td>");
                    out.print("<td>" + obj_mvt[8] + "</td>");
                    out.print("<td style='text-align: center; background-color:#e4e4e7;'><b>TIPO</b></td>");
                    for (int j = 0; j < lst_documento.size(); j++) {
                        Object[] obj_doc = (Object[]) lst_documento.get(j);
                        if (obj_doc[2].equals(obj_mvt[9])) {
                            out.print("<td>" + obj_doc[3] + "</td>");
                        }
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='text-align: center; height: 20px; background-color:#e4e4e7;'><b>VERSIÓN ANTIGUA</b></td>");
                    out.print("<td style='text-align: center;'>" + obj_mvt[1] + "</td>");
                    out.print("<td style='text-align: center; background-color:#e4e4e7;'><b>VERSIÓN NUEVA</b></td>");
                    out.print("<td style='text-align: center;'>" + obj_mvt[2] + "</td>");
                    out.print("<td style='text-align: center; background-color:#e4e4e7;'><b>JUSTIFICACIÓN</b></td>");
                    out.print("<td width='20%'>" + obj_mvt[3] + "</td>");
                    out.print("</tr>");
                    out.print("<tr><td COLSPAN='6' style='border: none;'> </td></tr>");
                }
                out.print("</table>");
                out.print("<div class='cleaner'></div>");
            }
            // </editor-fold>

            // <editor-fold defaultstate="collapsed"  desc="Actualización Versión.">
            if (pageContext.getRequest().getAttribute("Manual").toString().equals("version")) {
                id_manual = Integer.parseInt(pageContext.getRequest().getAttribute("id_manual").toString());
                codigo = pageContext.getRequest().getAttribute("codigo").toString();
                documento = pageContext.getRequest().getAttribute("documento").toString();
                tipo_doc = Integer.parseInt(pageContext.getRequest().getAttribute("tipo_doc").toString());
                siglatura = pageContext.getRequest().getAttribute("siglatura").toString();
                version = Integer.parseInt(pageContext.getRequest().getAttribute("version").toString());
                ant_version = pageContext.getRequest().getAttribute("aversion").toString();
                nombre = pageContext.getRequest().getAttribute("l_capacitador").toString();
                lst_documentoD = daruma.ConsultaDocumento(codigo, version);
                if (!lst_documentoD.isEmpty()) {
                    out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "<a href='Manual?opc=3&tipo_doc=" + tipo_doc + "&cbx_siglatura=" + siglatura + "'>"
                            + "<img src='Interfaz/Template/images/Volver.png' width='30' height='30' title='Volver'>"
                            + "</a>&nbsp;<i>Volver al módulo</i>");
                    out.print("<div id='sidebar'>");
                    lst_ControlC = daruma.ConsultaControlCDocumento(Integer.parseInt(lst_documentoD.get(0).toString().split(" / ")[1]), version);
                    out.print("<form method='post' id='enviar_version'>");
                    out.print("<input type='hidden' name='id_manual' value='" + id_manual + "'/>");
                    out.print("<input type='hidden' name='version' value='" + version + "'/>");
                    out.print("<input type='hidden' name='tipo_doc' value='" + tipo_doc + "'/>");
                    out.print("<input type='hidden' name='siglatura' value='" + siglatura + "'/>");
                    out.print("<input type='hidden' name='ant_version' value='" + ant_version + "'/>");
                    out.print("<h3>Versión Documento</h3>");
                    out.print("<b>Responsable:</b><br/>");
                    out.print("<input id='validate_res' type='text' name='txt_res' class='required input_field' placeholder='Ingresar responsable' readonly='true' value='" + nombre + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("<b>Código:</b><br/>");
                    out.print("<input id='validate_cod' type='text' name='txt_cod' class='required input_field' placeholder='Ingresar nombre' readonly='true' value='" + codigo + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                    out.print("<b>Documento:</b><br/>");
                    out.print("<textarea rows='4' id='validate_doc' name='txt_doc' class='required input_field' placeholder='Ingresar documento' readonly='true' onchange='javascript:this.value=this.value.toUpperCase();'>" + documento + "</textarea><br/>");
                    out.print("<i>Justifique la nueva versión " + version + "</i><br/>");
                    out.print("<b>Justificación:</b><br/>");
                    if (lst_ControlC != null) {
                        out.print("<div>");
                        out.print("" + lst_ControlC.get(0).toString().split(" / ")[3] + "");
                        out.print("<b>Cambios:</b></br>");
                        out.print("" + lst_ControlC.get(0).toString().split(" / ")[4] + "");
                        out.print("</div>");
                        out.print("<input type='hidden' name='txt_justificacion' value='" + lst_ControlC.get(0).toString().split(" / ")[3] + " " + lst_ControlC.get(0).toString().split(" / ")[4] + "'/>");
//                    out.print("<textarea name='txt_justificacion' id='justificacion-id' rows='4' placeholder='Ingresar justificación' onchange='javascript:this.value=this.value.toUpperCase();'>" + lst_ControlC.get(0).toString().split(" / ")[3] + " " + lst_ControlC.get(0).toString().split(" / ")[4] + "</textarea><br/>");
                    } else {
                        out.print("<textarea name='txt_justificacion' id='justificacion-id' rows='4' placeholder='Ingresar justificación' onchange='javascript:this.value=this.value.toUpperCase();'></textarea><br/>");
                    }
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('justificacion-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<input type='button' onclick='cambiar_version()' name='' value='Modificar'>");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Copec solo permite actualizar documentos que se encuentren vigentes en daruma\","
                            + "type:\"error\","
                            + "showCancelButton: false,"
                            + "confirmButtonColor: \"#048B86\","
                            + "confirmButtonText: \"Aceptar\","
                            + "closeOnConfirm: false"
                            + "},"
                            + "function(){"
                            + "location.href='Manual?opc=3&tipo_doc=" + tipo_doc + "&cbx_siglatura=" + siglatura + "';"
                            + "});");
                    out.print("</script>");
                }
            }
            // </editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Manual.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Manual.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
