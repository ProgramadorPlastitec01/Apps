package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.MaquinaJpaController;
import Controladores.RecipienteJpaController;
import Controladores.OrdenJpaController;
import Controladores.RegistroJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Tag_Registros extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String UserName = pageContext.getSession().getAttribute("Nombres").toString();
        String Userrol = pageContext.getSession().getAttribute("NombreRol").toString();
        int idRolS = Integer.parseInt(pageContext.getSession().getAttribute("idRol").toString());
        RecipienteJpaController jparcp = new RecipienteJpaController();
        RegistroJpaController jpargt = new RegistroJpaController();
        OrdenJpaController jpaodn = new OrdenJpaController();
        OrdenJpaController OrdenJpa = new OrdenJpaController();
        List lst_registro = null;
        List lst_registro_id = null;
        List lst_recipiente = null;
        List lst_orden = null;
        int id_orden = 0, id_registro = 0, id_despeje = 0, estado = 0, idUsuario = 0, idRol = 0, Obs = 0;
        String template_primary = "", nombreUSer = "", NombreRol = "";
        try {
            id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("id_registro").toString());
        } catch (Exception e) {
            id_registro = 0;
        }
        try {
            id_orden = Integer.parseInt(pageContext.getRequest().getAttribute("id_orden").toString());
        } catch (Exception e) {
            id_orden = 0;
        }
        try {
            id_despeje = Integer.parseInt(pageContext.getRequest().getAttribute("id_despeje").toString());
        } catch (Exception e) {
            id_despeje = 1;
        }
        try {
            Obs = Integer.parseInt(pageContext.getRequest().getAttribute("Obs").toString());
        } catch (Exception e) {
            Obs = 1;
        }
        try {
            idUsuario = Integer.parseInt(pageContext.getRequest().getAttribute("idUsuario").toString());
            nombreUSer = pageContext.getRequest().getAttribute("Nombres").toString();
            NombreRol = pageContext.getRequest().getAttribute("NombreRol").toString();
            idRol = Integer.parseInt(pageContext.getRequest().getAttribute("idRol").toString());
        } catch (Exception e) {
            idUsuario = 0;
            nombreUSer = "";
            NombreRol = "";
            idRol = 0;
        }
        try {
            if (Obs > 0 && id_registro > 0 && id_despeje == 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR OBSERVACION ENCARGADA">
                lst_registro_id = jpargt.ConsultarRegistroId(id_registro);
                if (lst_registro_id != null) {
                    Object[] obj_registroM = (Object[]) lst_registro_id.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg_registros'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Modificar</h2>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(4)'><i class=\"fas fa-times\"></i></button>");
                    out.print("</div>");
                    out.print("<form action='Registro?opc=9' method='post'>");
                    out.print("<input type='hidden' id='id_orden' name='id_orden' value='" + id_orden + "'>");
                    out.print("<input type='hidden' id='id_registro' name='id_registro' value='" + id_registro + "'>");
                    out.print("<input type='hidden' name='Txt_observacion' value='" + obj_registroM[8].toString() + "'>");
                    out.print("<b>Observación</b><br>");
                    out.print("<textarea style='width:97%; border: 1px solid #f5eaea;height:57px; max-width:97%; max-height:57px; min-height:57px;' "
                            + "name='observacionEncargada' id='observacion' placeholder='Observación'></textarea>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('observacion');val1.add(Validate.Presence);</script>");
                     out.print("<div class='text-center'><b>Mejora Opcional:</b> <b class='text-primary '>Si colocas los siguientes simbolos (//) en la observaciones, podras realizar un salto de línea.</b></div>");
                    out.print("<div style='margin-left: 84%;margin-top:2%;'>");
                    out.print("<button type=\"submit\" class=\"btn btn-primary\"> Modificar </button>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            } else if (id_registro > 0 && id_despeje == 0) {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR REGISTROS">
                lst_registro_id = jpargt.ConsultarRegistroId(id_registro);
                if (lst_registro_id != null) {
                    Object[] obj_registroM = (Object[]) lst_registro_id.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg_registros'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Modificar</h2>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(2)'><i class=\"fas fa-times\"></i></button>");
                    out.print("</div>");
                    out.print("<form action='Registro?opc=2' method='post'>");
                    out.print("<div style='display:flex;justify-content: space-evenly;'>");
                    out.print("<div style='width:45%;margin-right: 3%;'>");
                    out.print("<input type='hidden' id='id_orden' name='id_orden' value='" + id_orden + "'>");
                    out.print("<input type='hidden' id='id_registro' name='id_registro' value='" + id_registro + "'>");
                    out.print("<b>Fecha</b>");
                    out.print("<input type='text' class='form-control' id='datepicker' name='Txt_fecha' placeholder='Fecha turno' value='" + obj_registroM[5] + "'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("<div style='width:45%;margin-right: 3%;'>");
                    out.print("<b>Lote</b>");
                    out.print("<div style='width:100%;display:flex;' >");
                    String[] lote = obj_registroM[6].toString().split("-");
                    out.print("<div>");
                    out.print("<input type='text' class='form-control' name='Txt_codigo' id='Txt_codigo' placeholder='codigo' value='" + lote[0] + "' disabled>");
                    out.print("<input type='hidden' class='form-control' name='Txt_codigo' id='Txt_codigo' placeholder='codigo' value='" + lote[0] + "'>");
                    out.print("</div>");
                    out.print("<div style='text-align:center; padding:6px 5px 0px 5px'>-</div>"
                            + "<div>");
                    out.print("<input type='text' class='form-control' name='Txt_lote' id='Txt_lote' placeholder='Lote' maxlength='5' value='" + lote[1] + "'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //<editor-fold defaultstate="collapsed" desc="ACA YACE LA MAQUINA F">

//                    out.print("<div style='width:33%;margin-right: 3%;'>");
//                    out.print("<b>Maquina</b>");
//                    lst_maquinas = jpamqn.ConsultarMaquinas();
//                    out.print("<select class='form-control' name='Cbx_maquina' id='Cbx_maquina' placeholder='Seleccionar Maquina'>");
//                    if (lst_maquinas != null) {
//                        out.print("<option value='" + obj_registroM[14].toString() + "'>" + obj_registroM[15].toString() + "</option>");
//                        for (int i = 0; i < lst_maquinas.size(); i++) {
//                            Object[] obj_maquina = (Object[]) lst_maquinas.get(i);
//                            out.print("<option value='" + obj_maquina[0] + "'>" + obj_maquina[1] + "</option>");
//                        }
//                        out.print("</select>"
//                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_maquina');"
//                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                        out.print("</div>");
//                    } else {
//                        out.print("<option value='0'>No existen Maquinas registradas</option>");
//                    }
//</editor-fold>
                    out.print("</div>");
                    out.print("<br>");
                    out.print("<div style='display:flex;justify-content: space-evenly;'>");
                    out.print("<div style='width:45%;margin-right: 3%;'>");
                    out.print("<b>Recipiente - Bolsa</b>");
                    lst_recipiente = jparcp.ConsultarRecipientes();
                    out.print("<select class='form-control' name='Cbx_recipiente' id='Cbx_recipiente' placeholder='Seleccionar Recipiente'>");
                    if (lst_recipiente != null) {
                        out.print("<option value='" + obj_registroM[3].toString() + "'>"
                                + obj_registroM[4].toString() + "(" + obj_registroM[10].toString() + "" + obj_registroM[14].toString() + ") - "
                                + obj_registroM[11].toString() + "(" + obj_registroM[12].toString() + "" + obj_registroM[15].toString() + ")"
                                + "</option>");
                        for (int i = 0; i < lst_recipiente.size(); i++) {
                            Object[] obj_recipiente = (Object[]) lst_recipiente.get(i);
                            out.print("<option value='" + obj_recipiente[0] + "'>" + obj_recipiente[1] + "(" + obj_recipiente[2] + "" + obj_recipiente[10] + ") - " + obj_recipiente[3] + "(" + obj_recipiente[5] + "" + obj_recipiente[12] + ")</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_recipiente');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</div>");
                    } else {
                        out.print("<option value='0'>No existe recipiente registradas</option>");
                    }
                    out.print("<div style='width:45%;margin-right: 3%;'>");
                    out.print("<b>Estiba</b>");
                    out.print("<input type='number' class='form-control' name='Txt_estiba' id='Txt_estiba' placeholder='Estiba' value='" + obj_registroM[7].toString() + "'>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_estiba');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='margin-left: auto;margin-top:3%;'>");
                    out.print("<b>Observación</b><br>");
                    out.print("<textarea style='width:97%; border: 1px solid #f5eaea;height:57px; max-width:97%; max-height:57px; min-height:57px;' "
                            + "name='Txt_observacion' id='observacion' placeholder='Observación'>" + obj_registroM[8].toString() + "</textarea>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('observacion');val1.add(Validate.Presence);</script>");
                    out.print("<div class='text-center'><b>Mejora Opcional:</b> <b class='text-primary '>Si colocas los siguientes simbolos (//) en la observaciones, podras realizar un salto de línea.</b></div>");
                    out.print("</div>");
                    out.print("<div style='margin-left: 84%;margin-top:2%;'>");
                    out.print("<button type=\"submit\" class=\"btn btn-primary\"> Modificar </button>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            }
            if (id_despeje > 0) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRO DESPEJE">
                lst_registro = jpargt.ConsultarFormatoDespeje(id_registro);
                if (lst_registro != null) {
                    Object[] obj_clearence_template = (Object[]) lst_registro.get(0);
                    estado = Integer.parseInt(obj_clearence_template[3].toString());
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_clearence'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>R-PRF-008</h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div style='display:flex; justify-content:space-between;align-items:center; margin-top: 25px;'>");
                    //<editor-fold defaultstate="collapsed" desc="USUARIO EN SESION">
                    if (idUsuario > 0) {
                        out.print("<div>");
                        out.print("<div><b>Usuario:</b> " + nombreUSer + "</div>");
                        out.print("<div><b>Rol:</b><b style='color:" + ((idRol == 1 || idRol == 3 || idRol == 4) ? "#00281b" : (idRol == 5 || idRol == 7 || idRol == 8) ? "#079de9" : (idRol == 6) ? "green" : "") + "'> " + NombreRol + "</b></div>");
                        out.print("</div>");
                    } else {
                        out.print("<div>");
                        out.print("<div><b>Usuario actual:</b> " + UserName + "</div>");
                        out.print("<div><b>Rol:</b><b style='color:" + ((idRolS == 1 || idRolS == 3 || idRolS == 4) ? "#00281b" : (idRolS == 5 || idRolS == 7 || idRolS == 8) ? "#079de9" : (idRolS == 6) ? "green" : "") + "'> " + Userrol + "</b></div>");
                        out.print("</div>");
                    }
                    //</editor-fold>
                    out.print("<div style='display:flex; margin: 5px;justify-content:end;'>");
                    //<editor-fold defaultstate="collapsed" desc="IMPRESION">
                    if (estado == 0) {
                        out.print("<div><a href='#' onclick=\"printSection('printableDespeje')\" class='btn btn-white'><i style='font-size:15px' class='fas fa-print'  ></i></a></div>");
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR DE USUARIO Y FIRMAR">
                    if ((idRol >= 3 && idRol <= 5) || (idRolS >= 3 && idRolS <= 5) || (idRol == 1 || idRolS == 1) || (idRol == 7 || idRolS == 7)) {
                        out.print("<div style='padding:0px 12px 0px 12px;'><a href='#' class='btn btn-white' onclick='ConfirmationSave()'><i style='font-size:15px' class='fas fa-user'></i></a></div>");
                        out.print("<div style='padding:0px 12px 0px 0px;'><a href='#' onclick='SubmitForm()' class='btn btn-white'><i style='font-size:15px' class='fas fa-signature'></i></a></div>");
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="LIBERAR">
                    if (estado != 0) {
                        if (idRol == 5 || idRol == 7 || idRol == 8 || idRolS == 5 || idRolS == 7 || idRolS == 8 || idRolS == 1) {
                            out.print("<div data-toggle=\"tooltip\" title='Liberar Despeje' style='padding:0px 12px 0px 0px;'><a href='#' class='btn btn-white' onclick='SignatureClearance(" + id_orden + "," + id_registro + "," + id_despeje + ");'><i style='font-size:15px' class='fas fa-file-contract'></i></a></div>");
                        }
                    }
                    //</editor-fold>
                    if (estado != 0) {
                        if (idRolS != 2) {
                            out.print("<div data-toggle=\"tooltip\" title='Guardar'><a href='#' onclick='SubmitFormSave()' class='btn btn-white'><i style='font-size:15px' class='far fa-save'  ></i></a></div>");
                            //<editor-fold defaultstate="collapsed" desc="GUARDAR PLANTILLA">
                            out.print("<form action='Registro?opc=8' id='FormSave' name='FormSave' method='post'>");
                            out.print("<input type='hidden' name='id_registro' id='id_record' value='" + id_registro + "' /> ");
                            out.print("<input type='hidden' name='id_orden' id='id_order' value='" + id_orden + "' /> ");
                            out.print("<input type='hidden' name='id_despeje' id='id_clearence' value='" + id_despeje + "' /> ");
                            out.print("<input type='hidden' name='Txt_template' id='templateThird' /> ");
                            out.print("<input type='hidden' name='idUsuario' value='" + idUsuario + "' /> ");
                            out.print("<input type='hidden' name='Nombres' value='" + nombreUSer + "' /> ");
                            out.print("<input type='hidden' name='NombreRol' value='" + NombreRol + "' /> ");
                            out.print("<input type='hidden' name='idRol' value='" + idRol + "' /> ");
                            out.print("</form>");
                            //</editor-fold>
                        }
                    }
                    out.print("</div>");
                    out.print("</div>");
                    
                    out.print("<div class='cont_form_user'>");
                    out.print("<div class='' style='max-height: 509px; overflow-y: auto;'>");
                    out.print("<form action='Registro?opc=6' id='FormSignature' name='FormSignature' method='post'>");
                    out.print("<input type='hidden' name='id_registro' id='id_registro' value='" + id_registro + "' /> ");
                    out.print("<input type='hidden' name='id_orden' id='id_orden' value='" + id_orden + "' /> ");
                    out.print("<input type='hidden' name='id_despeje' id='id_despeje' value='" + id_despeje + "' /> ");
                    out.print("<input type='hidden' name='signature' id='signature' value='" + ((idUsuario > 0) ? nombreUSer : UserName) + "' /> ");
                    out.print("<input type='hidden' name='rol_signature' id='rol_signature' value='" + ((idUsuario > 0) ? idRol : idRolS) + "' /> ");
                    out.print("<input type='hidden' name='idUsuario' value='" + idUsuario + "' /> ");
                    out.print("<input type='hidden' name='Nombres' value='" + nombreUSer + "' /> ");
                    out.print("<input type='hidden' name='NombreRol' value='" + NombreRol + "' /> ");
                    out.print("<input type='hidden' name='idRol' value='" + idRol + "' /> ");
                    out.print("<input type='hidden' name='idRol' id='idRolHidden' value='" + idRol + "' /> "); // ID para JS

                    if (obj_clearence_template[2] == null) {
                        out.print("NO SE HA INGRESADO CONTENIDO HTML");
                    } else {
                        template_primary = obj_clearence_template[2].toString();
                        if (estado == 0) {
                            template_primary = template_primary.replace("data-text=\"___\"></div>", "data-text=\"___\" style='background: #f9a6a6bf;'></div>");
                            template_primary = template_primary.replace("data-text=\"SI/NO\"></div>", "data-text=\"SI/NO\" style='background: #f9a6a6bf;'></div>");
                            out.print("<div id='templateMajor' onkeyup='ValuePass();'>" + template_primary.replace("true", "false") + "</div>");
                        } else {
                            template_primary = template_primary.replace("<u contenteditable=\"true\" >___</u>", "<u contenteditable=\"true\" style='background: #f9a6a6bf;'>___</u>");
                            template_primary = template_primary.replace("<u contenteditable=\"true\">___</u>", "<u contenteditable=\"true\" style='background: #f9a6a6bf;'>___</u>");
                            template_primary = template_primary.replace("<div contenteditable=\"true\" data-text=\"___\" ></div>", "<div contenteditable=\"true\" data-text=\"___\" style='background: #f9a6a6bf;' ></div>");
                            template_primary = template_primary.replace("<div contentEditable=\"true\" data-text=\"___\"></div>", "<div contenteditable=\"true\" data-text=\"___\" style='background: #f9a6a6bf;' ></div>");
                            template_primary = template_primary.replace("<div contenteditable=\"true\" data-text=\"SI/NO\"></div>", "<div contenteditable=\"true\" data-text=\"SI/NO\" style='background: #f9a6a6bf;' ></div>");
                            template_primary = template_primary.replace("<div contenteditable=\"true\" data-text=\"SI/NO\" ></div>", "<div contenteditable=\"true\" data-text=\"SI/NO\" style='background: #f9a6a6bf;' ></div>");
                            out.print("<div id='templateMajor' onkeyup='ValuePass();'>" + template_primary + "</div>");
                        }
                        out.print("<input type='hidden' name='Txt_template' id='templateSecondary' /> ");
                    }
                    out.print("</div>");
                }
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                
                out.print("<script>");
                out.print("document.addEventListener('DOMContentLoaded', function() {");
                out.print("    let idRol = document.getElementById('idRolHidden').value;");
                out.print("    document.querySelectorAll(\"u[contenteditable='true']\").forEach(function(element) {");
                out.print("        element.addEventListener('keyup', function() {");
                out.print("            if (idRol == 5 || idRol == 7 || idRol == 8) {");
                out.print("                this.style.color = '#079de9';");
                out.print("                this.style.backgroundColor = 'white';");
                out.print("            } else {");
                out.print("                this.style.color = '';");  // Restaurar color predeterminado
                out.print("                this.style.backgroundColor = '';"); // Restaurar fondo predeterminado
                out.print("            }");
                out.print("        });");
                out.print("    });");
                out.print("});");
                out.print("</script>");
                //<editor-fold defaultstate="collapsed" desc="SIGNATURE">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_signature'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Cambio de usuario</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(5)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                if (idUsuario > 0) {
                    out.print("<div style='margin-bottom:15px;'><b>Usuario actual:</b> " + nombreUSer + "</div>");
                } else {
                    out.print("<div style='margin-bottom:15px;'><b>Usuario actual:</b> " + UserName + "</div>");
                }
                out.print("<form action='Registro?opc=5' method='post'>");
                out.print("<input type='hidden' name='id_orden' value='" + id_orden + "' />");
                out.print("<input type='hidden' name='id_registro' value='" + id_registro + "' />");
                out.print("<input type='hidden' name='id_despeje' value='" + id_despeje + "' />");
//                out.print("<input type='hidden' name='temp_4' value='" + temp_4 + "'/>");
                out.print("<div class='form-group'>");
                out.print("<div class='input-group'>");
                out.print("<div class='input-group-prepend'>");
                out.print("<div class='input-group-text'  style='height: 100%;'>");
                out.print("<i class='fas fa-user'></i>");
                out.print("</div>");
                out.print("</div>");
                out.print("<input type='text' class='form-control' name='Txt_user' id='Txt_user' placeholder='Usuario' autocomplete='off'>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='form-group'>");
                out.print("<div class='input-group'>");
                out.print("<div class='input-group-prepend'>");
                out.print("<div class='input-group-text' style='height: 100%;'>");
                out.print("<i class='fas fa-key'></i>");
                out.print("</div>");
                out.print("</div>");
                out.print("<input type='password' class='form-control' name='Txt_password' id='txtPassword' placeholder='Contraseña' autocomplete='off'>");
                out.print("<div class='input-group-text' onclick='mostrarPass()' id='show_password' style='cursor: pointer;'><i id='icon' class='fas fa-eye'></i></div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<button class='btn' style='box-shadow: 1px 2px 5px 0px #959595;margin-left:180px;'><i class='fas fa-arrow-right'></i></button>         ");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="AGREGAR REGISTROS">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg_registros'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registro</h2>");
            out.print("<button class='btn_clsRg' onclick='mostrarConvencion(1)'><i class=\"fas fa-times\"></i></button>");
            out.print("</div>");
            out.print("<form action='Registro?opc=2' method='post'>");
            out.print("<div style='display: flex; justify-content: space-evenly;'>");
            out.print("<div style='width:45%;margin-right: 3%;'>");
            out.print("<b>Fecha</b><b class='text-danger'>*</b>");
            out.print("<input type='text' class='form-control' id='datepicker2' name='Txt_fecha' placeholder='Fecha turno' value='' autocomplete='off'>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker2');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("<div style='width:45%;margin-right: 3%;'>");
            out.print("<b>Lote</b><b class='text-danger'>*</b>");
            lst_orden = jpaodn.ConsultarOrdenId(id_orden);
            if (lst_orden.size() > 0) {
                Object[] obj_orden = (Object[]) lst_orden.get(0);
                out.print("<input type='hidden' id='id_orden' name='id_orden' value='" + obj_orden[0] + "'>");
                out.print("<div style='width:100%;display:flex;' >");
                out.print("<div>");
                out.print("<input type='text' class='form-control' name='Txt_codigo' id='Txt_codigo' placeholder='codigo' value='" + obj_orden[15] + "' disabled>");
                out.print("<input type='hidden' class='form-control' name='Txt_codigo' id='Txt_codigo' placeholder='codigo' value='" + obj_orden[15] + "'>");
                out.print("</div>");
                out.print("<div style='text-align:center; padding:6px 5px 0px 5px'>-</div>"
                        + "<div>");
                out.print("<input type='text' class='form-control' name='Txt_lote' id='Txt_lote' placeholder='Ingresar lote' onclick='lote()' maxlength='5' value=''  autocomplete='off'>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lote');val1.add(Validate.Presence);</script>");
                out.print("</div>");
                out.print("</div>");
                out.print("<input type='hidden' name='Cbx_maquina' value='" + obj_orden[16] + "'>");
            } else {
                out.print("<input type='text' class='form-control' placeholder='Lote' value='Orden no registrada' disabled>");
            }
            out.print("</div>");
            //<editor-fold defaultstate="collapsed" desc="ACA YACE LA MAQUINA F">

//            out.print("<div style='width:33%;margin-right: 3%;'>");
//            out.print("<b>Maquina</b>");
//            lst_maquinas = jpamqn.ConsultarMaquinas();
//            out.print("<select class='form-control' name='Cbx_maquina' id='Cbx_maquina' placeholder='Seleccionar Maquina'>");
//            if (lst_maquinas != null) {
//                out.print("<option value='0'>Selecccione Maquina</option>");
//                for (int i = 0; i < lst_maquinas.size(); i++) {
//                    Object[] obj_maquina = (Object[]) lst_maquinas.get(i);
//                    out.print("<option value='" + obj_maquina[0] + "'>" + obj_maquina[1] + "</option>");
//                }
//                out.print("</select>"
//                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_maquina');"
//                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//                out.print("</div>");
//            } else {
//                out.print("<option value='0'>No existen Maquinas registradas</option>");
//            }
//</editor-fold>
            out.print("</div>");
            out.print("<br>");
            out.print("<div style='display:flex; justify-content: space-evenly'>");
            out.print("<div style='width:45%;margin-right: 3%;'>");
            //<editor-fold defaultstate="collapsed" desc="ACA YACE EL RECIPIENTE F">
            out.print("<b>Recipiente - Bolsa</b>");
            lst_recipiente = jparcp.ConsultarRecipientes();
            out.print("<select class='form-control' name='Cbx_recipiente' id='Cbx_recipiente' placeholder='Seleccionar Recipiente' readonly='true'>");
            if (lst_recipiente != null) {
                Object[] obj_recipiente = (Object[]) lst_recipiente.get(0);
                out.print("<option value='" + obj_recipiente[0] + "'>" + obj_recipiente[1] + "(" + obj_recipiente[2] + "" + obj_recipiente[10] + ") - " + obj_recipiente[3] + "(" + obj_recipiente[5] + "" + obj_recipiente[12] + ")</option>");
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_recipiente');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
            } else {
                out.print("<option value='0'>No existe recipiente registradas</option>");
            }
            //</editor-fold>
            out.print("</div>");
            out.print("<div style='width:45%;margin-right: 3%;'>");
            out.print("<b>Estiba</b><b class='text-danger'>*</b>");
            out.print("<input type='number' class='form-control' name='Txt_estiba' id='Txt_estiba' placeholder='Estiba' value=''>"
                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_estiba');val1.add(Validate.Presence);</script>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div style='margin-left: auto;margin-top:3%;'>");
            out.print("<b>Observación</b><br>");
            out.print("<textarea style='width:97%; border: 1px solid #f5eaea;height:57px; max-width:97%; max-height:57px; min-height:57px;' "
                    + "name='Txt_observacion' id='observacion' placeholder='Observación'></textarea>");
            out.print("<div class='text-center'><b>Mejora Opcional:</b> <b class='text-primary '>Si colocas los siguientes simbolos (//) en la observaciones, podras realizar un salto de línea.</b></div>");
            out.print("</div>");
            out.print("<div style='margin-left: 84%;margin-top:2%;'>");
            out.print("<button type=\"submit\" class=\"btn btn-primary\"> Registrar </button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="VISUALIZAR DATOS ORDEN">
            if (id_orden > 0) {
                lst_orden = OrdenJpa.ConsultarOrdenId(id_orden);
                if (lst_orden != null) {
                    //<editor-fold defaultstate="collapsed" desc="ORDEN">
                    Object[] obj_orden = (Object[]) lst_orden.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana12' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_orden' style='height: auto;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>" + obj_orden[3] + "</h4>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(12)'><i class=\"fas fa-times\"></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_orden'>");

                    out.print("<div style='justify-content:space-evenly;display: flex;'>");
                    out.print("<div style='width:22%'>");
                    out.print("<b># OP</b>");
                    out.print("<br><p>" + obj_orden[1] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Codigo</b>");
                    out.print("<br><p>" + obj_orden[2].toString() + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Fecha Inicio</b>");
                    out.print("<br><p>" + obj_orden[10] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Plan</b>");
                    out.print("<br><p>" + obj_orden[4] + "</p>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='justify-content:space-evenly;display: flex;'>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Centro Costo</b>");
                    out.print("<br><p>" + obj_orden[9] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Maquina</b>");
                    out.print("<br><p>" + obj_orden[17] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Molde</b>");
                    out.print("<br><p>" + obj_orden[18] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Peso x unidades.</b>");
                    out.print("<br><p>" + obj_orden[19].toString().replace("///", " x ") + "g</p>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='justify-content:space-evenly;display: flex;'>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Cantidad Programada</b>");
                    out.print("<br><p>" + obj_orden[6] + "-" + obj_orden[8] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Peso Programado</b>");
                    out.print("<br><p>" + obj_orden[20] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Unidades de Empaque</b>");
                    out.print("<br><p>---</b></p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Peso x und. de Empaque</b>");
                    out.print("<br><p>" + obj_orden[22] + " <b>g.</b></p>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='justify-content:space-evenly;display: flex;'>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Cant. Revisada</b>");
                    out.print("<br><p> " + ((obj_orden[7] == null) ? "0" : obj_orden[7].toString()) + " -" + obj_orden[8] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Fecha Inicio Revision</b>");
                    out.print("<br><p>" + obj_orden[14] + "</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Fecha Final Revision</b>");
                    out.print("<br><p>--</p>");
                    out.print("</div>");
                    out.print("<div style='width:22%'>");
                    out.print("<b>Ficha Técnica</b>");
                    out.print("<br><p>" + (obj_orden[24].toString().contains("/") ? obj_orden[24].toString().split("/")[1] : "Sin asociación") + "</p>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='margin-left:70%; height: 30px;'><button style='float: right;' type='button' class='btn btn-primary' onclick='mostrarConvencion(12)'> Cerrar <i class='fas fa-times'></i></button></div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana12' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='cont_reg' style='width: 32%; height: 26%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Informacion de registro</h2>");
                    out.print("<button class='btn_clsRg' onclick='mostrarConvencion(8)'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_rdt'>");
                    out.print("<div>");
                    out.print("<p> No se ha podido cargar datos del registro<p>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<button style='float: right;' type='submit' class='btn btn-primary' onclick='mostrarConvencion(8)'> Cerrar <i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CABECERA DE TABLA - BUSCADOR - AGREGAR">
            out.print("<div class='page-wrapper'>");
            out.print("<div class='page-breadcrumb bg-white'>");
            out.print("<div class='row align-items-center'>");
            out.print("<div class='col-lg-3 col-md-4 col-sm-4 col-xs-12'>");
            out.print("<h4 class='page-title'> Registro</h4>");
            out.print("</div>");
            out.print("<div class='col-lg-9 col-sm-8 col-md-8 col-xs-12'>");
            out.print("<div class='d-md-flex' style='height: 33px;'>");
            out.print("<ol class='breadcrumb ms-auto'>");
            out.print("<li>");
            out.print("<div class='input-group'>");
            out.print("<div class='form-outline' style='margin-top: -7px;'>");
            out.print("<input style='height: 33px;' id='search-focus' onkeyup='Filtrar()' onchange='javascript:this.value=this.value.toUpperCase();"
                    + " type='search' id='form1' class='form-control' placeholder='Buscar..' />");
            out.print("</div>");
            out.print("<button type='button' class='btn btn-primary' style='background: #41b3f9; margin-top: -7px; height: 33px; border-color: #41b3f9;'>");
            out.print("<i class='fas fa-search'></i>");
            out.print("</button>");
            out.print("</div>");
            out.print("</li>");
            out.print("</ol>");
            if (Userrol.equals("Administrador") || Userrol.equals("Coordinadora")) {
                out.print("<a onclick='mostrarConvencion(1)'"
                        + "class='btn btn-danger  d-none d-md-block pull-right ms-3 hidden-xs hidden-sm waves-effect waves-light text-white' title='Agregar un Registro'>Agregar <i class='fas fa-plus'></i></a>");
            }
            out.print("<button class='btn btn-dark' onclick='mostrarConvencion(12)' style='color: #fff; margin-right: 5px; margin-left: 5px; height:33px;' title='Ver datos de Orden '>Orden <i class='fas fa-book'></i></button>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CONTENIDO DE TABLA">
            out.print("<div class='container-fluid'>");
            out.print("<div class='row'>");
            out.print("<div class='col-sm-12'>");
            out.print("<div class='white-box'>");
            out.print("<div style='display: flex;justify-content: space-between;align-items: baseline;'>");
            out.print("<div style='display: flex;'>");
            out.print("<h3 class='box-title'><a style='background: white; border: 1px solid white;margin-right:15px;' href='Orden?opc=1' class='btn btn-secondary' title='Volver a Ordenes de Produccion'><img src='Interfaz/Contenido/Imagenes/reply.png' width='15'></a> Tabla Registros</h3>");
            out.print("</div>");
            out.print("<div style='display: flex;'>");
            out.print("<div style='margin-right: 30px; display: flex;'>");
            lst_registro = jpargt.Consultar_pesoTotal_registro(id_orden);
            double uniddades = 0, unidEmp = 0, tara = 0;
            if (lst_registro != null || lst_registro.size() == 0) {
                Object[] obj_total = (Object[]) lst_registro.get(0);
                if (obj_total[1] != null) {
                    out.print("<div style='margin-right: 30px; text-align: center;'>");
                    out.print("<p>Peso Acumulado OP:</p> <h3 style='margin-top: -4px; margin-left: 10px; color: #ff963f;'><b>" + Math.round(Double.parseDouble(obj_total[1].toString())) + " g</b></h3>");
                    out.print("</div>");
//                    out.print("<div style='text-align: center;'>");
//                    out.print("<p>Unidades Acumuladas OP:</p> <h3 style='margin-top: -4px; margin-left: 10px; color: #ff963f;'><b>" + obj_total[2].toString() + " uds.</b></h3>");
//                    out.print("</div>");
                    uniddades = Double.parseDouble(obj_total[2].toString());
                    String[] VrData = obj_total[4].toString().split("///");
                    tara = Double.parseDouble(VrData[0].toString());
                    unidEmp = Double.parseDouble(obj_total[5].toString());
                } else {
                    out.print("<h4>Aun no hay peso registrado</h4>");
                }
            } else {
                out.print("<h4>Aun no hay peso registrado</h4>");
            }
            out.print("</div>");
            out.print("<div align='right' id='NavPosicion0' style='margin-bottom: 5px;'></div>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table text-nowrap table-hover' id='resultados'>");
            out.print("<thead>");
            out.print("<tr style='text-align: center;'>");
            out.print("<th class='border-top-0'>Ver</th>");
            out.print("<th class='border-top-0'>Fecha</th>");
            out.print("<th class='border-top-0'>Lote</th>");
            out.print("<th class='border-top-0'>Maquina</th>");
            out.print("<th class='border-top-0'>Turno</th>");
            out.print("<th class='border-top-0'>Estiba</th>");
            out.print("<th class='border-top-0'>Peso registrado</th>");
            out.print("<th class='border-top-0'>Observaciones</th>");
            out.print("<th class='border-top-0'>Resposanble</th>");
            out.print("<th colspan='1' class='border-top-0'>Opciones</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_registro = jpargt.ConsultarRegistro(id_orden);
//            lst_registro = null;
            if (lst_registro != null && lst_registro.size() != 0) {
                for (int i = 0; i < lst_registro.size(); i++) {
                    Object[] obj_registro = (Object[]) lst_registro.get(i);
                    int est = Integer.parseInt(obj_registro[9].toString());
                    out.print("<tr>");
                    if (est == 3) {
                        out.print("<td colspan='9' style='text-align: center;'> ¿Aplica Despeje?<br> <button class='btn btn-info btn-sm' onclick='window.location.href=\"Registro?opc=4&id_registro=" + obj_registro[0] + "&id_orden=" + id_orden + "\"'>SI</button> <button class='btn btn-info btn-sm' onclick='window.location.href=\"Registro?opc=3&id_registro=" + obj_registro[0] + "&id_orden=" + id_orden + "&estado=" + est + "\"'>No</button> </td>");
                    } else {
                        try {
                            id_despeje = Integer.parseInt(obj_registro[17].toString());
                        } catch (Exception e) {
                            id_despeje = 0;
                        }
                        if (obj_registro[18] != null) {
                            int estDespeje = Integer.parseInt(obj_registro[18].toString());
                            if (estDespeje == 0) {
                                out.print("<td><a href='Registro_detalle?opc=1&id_registro=" + obj_registro[0] + "&id_orden=" + obj_registro[1] + "' class='btn btn-primary' title='Ingresar a Registros'><i style='color:#fff;' class=\"fas fa-eye\"></i></i></a></td>");
                            } else {
                                out.print("<td><a href='#' class='btn btn-secondary' title='No se ha liberado el despeje'><i style='color:#fff;' class=\"fas fa-eye\"></i></i></a></td>");
                            }
                        } else {
                            out.print("<td><a href='Registro_detalle?opc=1&id_registro=" + obj_registro[0] + "&id_orden=" + obj_registro[1] + "' class='btn btn-primary' title='Ingresar a Registros'><i style='color:#fff;' class=\"fas fa-eye\"></i></i></a></td>");
                        }
                        out.print("<td align='center'>" + obj_registro[5] + "</td>");
                        out.print("<td align='center'>" + obj_registro[6] + "</td>");
                        out.print("<td align='center'><p style='  display: inline-block;border-bottom: 1px dotted black;' class='tooltip6'><span>" + obj_registro[13] + "</span>"
                                + "<span style='overflow:auto;width:345px;' class='tooltiptext'>Molde: " + obj_registro[19] + "<br>Tara:" + obj_registro[20] + "</span></p></td>");
//                        out.print("<td align='center'>" + obj_registro[4] + "</td>");
                        out.print("<td align='center'>" + ((obj_registro[21] == null) ? "Sin turno generado" : obj_registro[21]) + "</td>");
                        out.print("<td align='center'>" + obj_registro[7] + "</td>");
                        if (obj_registro[15] != null) {
                            Double calc = (Math.round(Double.parseDouble(obj_registro[15].toString())) * tara) / 1000;
                            out.print("<td align='center'><p class='tooltip5'><span>" + Math.round(Double.parseDouble(obj_registro[15].toString())) + " g</span><span class='tooltiptext'><b>Unidades: </b>" + Math.round(calc) + " un</span></p></td>");
                        } else {
                            out.print("<td align='center'>-</td>");
                        }
                        if (Integer.parseInt(obj_registro[16].toString()) >= 50) {
                            out.print("<td align='center'><p style='  display: inline-block;border-bottom: 1px dotted black;' class='tooltip6'><span>Detalle</span>"
                                    + "<span style='overflow:auto;width:345px;' class='tooltiptext'>" + obj_registro[8] + "</span></p></td>");
                        } else {
                            out.print("<td align='center'>" + obj_registro[8].toString().replace("//", "<br/>") + "</td>");
                        }
                        out.print("<td style='text-align: center;'>");
                        out.print(obj_registro[22]);
                        out.print("</td>");
                        out.print("<td style='text-align: center;'>");
                        if (Userrol.equals("Administrador") || Userrol.equals("Coordinadora")) {
                            out.print("<a " + ((est == 1) ? "href='Registro?opc=1&id_registro=" + obj_registro[0] + "&id_orden=" + obj_registro[1] + "' style='margin-right: 12px;'" : "style='pointer-events: none;margin-right: 12px;'") + "   class='btn btn-warning'><i " + ((est == 1) ? "class='fas fa-edit'" : "class='fas fa-ban'") + " ></i></a>");
                        } else if (Userrol.equals("Encargada")) {
                            out.print("<a " + ((est == 1) ? "href='Registro?opc=1&id_registro=" + obj_registro[0] + "&id_orden=" + obj_registro[1] + "&Obs=1' style='margin-right: 12px;'" : "style='pointer-events: none;margin-right: 12px;'") + "   class='btn btn-warning'><i " + ((est == 1) ? "class='fas fa-edit'" : "class='fas fa-ban'") + " ></i></a>");
                        } else {
                            out.print("<button " + ((est == 1) ? "href='#' style='margin-right: 12px;'" : "style='pointer-events: none;margin-right: 12px;'") + "   class='btn btn-warning' disabled><i " + ((est == 1) ? "class='fas fa-edit'" : "class='fas fa-ban'") + " ></i></button>");
                        }
                        if (Userrol.equals("Administrador") || Userrol.equals("Coordinadora")) {
                            out.print("" + ((est == 1)
                                    ? "<a href='Registro?opc=3&id_registro=" + obj_registro[0] + "&id_orden=" + id_orden + "&estado=" + est + "' class='btn btn-info' style='margin-right: 12px;'><i style='color:#fff;' class=\"fas fa-lock-open\" title='Orden Abierta'></i></i></a>"
                                    : "<a href='Registro?opc=3&id_registro=" + obj_registro[0] + "&id_orden=" + id_orden + "&estado=" + est + "' class='btn btn-info' style='margin-right: 12px;'><i style='color:#fff;' class=\"fas fa-lock\" title='Orden Cerrada'></i></a>") + "");
                        } else {
                            out.print("" + ((est == 1)
                                    ? "<button  class='btn btn-info' disabled style='margin-right: 12px;'><i style='color:#fff;' class=\"fas fa-lock-open\" title='Orden Abierta'></i></i></button>"
                                    : "<button  class='btn btn-info' disabled style='margin-right: 12px;'><i style='color:#fff;' class=\"fas fa-lock\" title='Orden Cerrada'></i></button>") + "");
                        }
                        if (id_despeje != 0) {
                            out.print("<button class='btn btn-dark ' onclick='window.location.href=\"Registro?opc=1&id_orden=" + id_orden + "&id_registro=" + obj_registro[0] + "&id_despeje=" + id_despeje + "\"'><i class='fas fa-file-alt'></i></button>");
                        }
                        out.print("</td>");
                    }
                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td colspan='9' style='text-align: center;'>No se han encontrado datos <i class='fas fa-exclamation-circle'></i></td>");
                out.print("</tr>");
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("<script type='text/javascript'>");
            out.print("var pager0 = new Pager0('resultados',10);");
            out.print("pager0.init();");
            out.print("pager0.showPageNav('pager0','NavPosicion0');");
            out.print("pager0.showPage(1);");
            out.print("</script>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_Registros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
